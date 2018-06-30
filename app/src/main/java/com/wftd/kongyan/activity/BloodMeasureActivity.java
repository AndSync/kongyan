package com.wftd.kongyan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ihealth.communication.control.Bp3lControl;
import com.ihealth.communication.control.BpProfile;
import com.ihealth.communication.manager.iHealthDevicesCallback;
import com.ihealth.communication.manager.iHealthDevicesManager;
import com.wftd.kongyan.R;
import com.wftd.kongyan.adapter.DoctorListAdapter;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.DoctorCallback;
import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.entity.Ser1UserInfo;
import com.wftd.kongyan.util.DialogUtils;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.LogUtils;
import com.wftd.kongyan.util.StringUtils;
import com.wftd.kongyan.util.ToastUtils;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wftd.kongyan.R.id.radioGroup;

/**
 * 主页
 */
public class BloodMeasureActivity extends BaseActivity
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, DoctorCallback {
    private ImageView mGoPersonal;//个人中心
    private ImageView mNextStep;//填写问卷
    private ImageView mBack;
    private EditText mEtSBP;//收缩压
    private EditText mEtDBP;//舒张压
    private TextView mAuto;//自动获取
    private TextView mHint;//未选择口服盐编号提示
    private RadioGroup mGroup;
    private RadioButton first;
    private RadioButton second;
    private RadioButton third;
    private RadioButton fourth;
    private Ser1UserInfo user = UserHelper.getUserInfo();

    private String mType, deviceMac;
    private Bp3lControl bp3lControl;
    private int clientCallbackId;
    private String userName = "kongyan";
    private List<Doctor> doctorList = UserHelper.getDoctorList();
    private DoctorListAdapter spinnerAdapter;
    private Spinner spinner;

    String doctorName;
    /**
     * 扫描到血压仪
     */
    private static final int MESSAGE_SCAN_SUCCESS = 100;
    private static final int MESSAGE_SCAN_FAILURE = 101;
    private static final int MESSAGE_LINK_SUCCESS = 102;
    private static final int MESSAGE_LINK_FAILURE = 103;
    private static final int MESSAGE_MEASURE_END = 104;
    private static final int MESSAGE_MEASURE_TIMEOUT = 105;
    private static final int HANDLER_MESSAGE = 106;
    private static final int HANDLER_ERROR = 107;
    private boolean isMeasure;

    private Handler mHandler = new Handler() {
        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MESSAGE_SCAN_SUCCESS:
                    mAuto.setText("连接设备");
                    break;
                case MESSAGE_LINK_SUCCESS:
                    mAuto.setText("自动获取");
                    break;
                case MESSAGE_LINK_FAILURE:
                    mAuto.setText("连接设备");
                    break;
                case MESSAGE_MEASURE_END:
                    Bundle bundle = msg.getData();
                    String low = bundle.getString("low", "");
                    String hight = bundle.getString("hight", "");
                    mEtSBP.setText(hight);
                    mEtDBP.setText(low);
                    mAuto.setText("自动获取");
                    break;
                case HANDLER_ERROR:
                    mAuto.setText("自动获取");
                    DialogUtils.showAlertDialog(context, "获取失败", "请确保正确佩戴设备");
                    break;
                case MESSAGE_MEASURE_TIMEOUT:
                    mAuto.setText("自动获取");
                    DialogUtils.showAlertDialog(context, "获取失败", "获取超时请重新获取");
                    break;
                case HANDLER_MESSAGE:
                    Log.d(TAG, (String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_index);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bp3lControl != null) {
            LogUtils.d(TAG, "断开连接");
            bp3lControl.disconnect();
        }
        iHealthDevicesManager.getInstance().unRegisterClientCallback(clientCallbackId);
    }

    private void init() {
        mGoPersonal = (ImageView) findViewById(R.id.go_personal_center);
        mNextStep = (ImageView) findViewById(R.id.next_step);
        mEtSBP = (EditText) findViewById(R.id.home_input_sbp);
        mEtDBP = (EditText) findViewById(R.id.home_input_dbp);
        mAuto = (TextView) findViewById(R.id.home_auto_get);
        mHint = (TextView) findViewById(R.id.kfy_hint);
        mGroup = (RadioGroup) findViewById(radioGroup);
        first = (RadioButton) findViewById(R.id.first);
        second = (RadioButton) findViewById(R.id.second);
        third = (RadioButton) findViewById(R.id.third);
        fourth = (RadioButton) findViewById(R.id.fourth);
        mBack = (ImageView) findViewById(R.id.home_back);
        mGoPersonal.setOnClickListener(this);
        mNextStep.setOnClickListener(this);
        mGroup.setOnCheckedChangeListener(this);
        mAuto.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAuto.setText("搜索中请开启血压仪");
        iHealthDevicesManager.getInstance().init(this, Log.VERBOSE, Log.ASSERT);

        clientCallbackId = iHealthDevicesManager.getInstance().registerClientCallback(miHealthDevicesCallback);
        /* Limited wants to receive notification specified device */
        iHealthDevicesManager.getInstance().startDiscovery(32L);

        if (user != null && user.getOrganizationId() != null) {
            HttpUtils.DoctorGet(user.getOrganizationId(), this);
        }
        // 初始化控件
        spinner = (Spinner) findViewById(R.id.sp_doctor);

        // 建立Adapter并且绑定数据源
        spinnerAdapter = new DoctorListAdapter(this, doctorList);
        //绑定 Adapter到控件
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                doctorName = doctorList.get(pos).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private int i = 0;
    private iHealthDevicesCallback miHealthDevicesCallback = new iHealthDevicesCallback() {

        @Override
        public void onScanDevice(String mac, String deviceType, int rssi, Map manufactorData) {
            deviceMac = mac;
            mType = deviceType;
            mHandler.sendEmptyMessage(MESSAGE_SCAN_SUCCESS);
            LogUtils.d(TAG, "扫描到血压仪:" + mac + "、" + deviceType);
        }

        @Override
        public void onDeviceConnectionStateChange(String mac, String deviceType, int status, int errorID) {

            if (status == iHealthDevicesManager.DEVICE_STATE_CONNECTED) {
                Message msg = new Message();
                msg.what = MESSAGE_LINK_SUCCESS;
                mHandler.sendMessage(msg);
                ToastUtils.show(context, "连接成功");
                LogUtils.d(TAG, "连接成功");
                iHealthDevicesManager.getInstance()
                    .addCallbackFilterForDeviceType(clientCallbackId, iHealthDevicesManager.TYPE_BP3L);
                /* Limited wants to receive notification specified device */
                iHealthDevicesManager.getInstance()
                    .addCallbackFilterForDeviceType(clientCallbackId, iHealthDevicesManager.TYPE_BP3L);
                /* Get bp3 controller */
                bp3lControl = iHealthDevicesManager.getInstance().getBp3lControl(deviceMac);
            } else if (status == iHealthDevicesManager.DEVICE_STATE_DISCONNECTED) {
                Message msg = new Message();
                msg.what = MESSAGE_LINK_FAILURE;
                mHandler.sendMessage(msg);
                LogUtils.d(TAG, "断开连接");
            }
            LogUtils.d(TAG, status + ":" + errorID);
        }

        @Override
        public void onDeviceNotify(String mac, String deviceType, String action, String message) {
            i++;

            LogUtils.d(TAG, "mac:" + mac + "\ndeviceType" + deviceType + "\naction:" + action + "\nmessage:" + message);

            if (BpProfile.ACTION_BATTERY_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String battery = info.getString(BpProfile.BATTERY_BP);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "battery: " + battery;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_DISENABLE_OFFLINE_BP.equals(action)) {

            } else if (BpProfile.ACTION_ENABLE_OFFLINE_BP.equals(action)) {

            } else if (BpProfile.ACTION_ERROR_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String num = info.getString(BpProfile.ERROR_NUM_BP);
                    Message msg = new Message();
                    msg.what = HANDLER_ERROR;
                    msg.obj = "error num: " + num;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_HISTORICAL_DATA_BP.equals(action)) {
                String str = "";
                try {
                    JSONObject info = new JSONObject(message);
                    if (info.has(BpProfile.HISTORICAL_DATA_BP)) {
                        JSONArray array = info.getJSONArray(BpProfile.HISTORICAL_DATA_BP);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String date = obj.getString(BpProfile.MEASUREMENT_DATE_BP);
                            String hightPressure = obj.getString(BpProfile.HIGH_BLOOD_PRESSURE_BP);
                            String lowPressure = obj.getString(BpProfile.LOW_BLOOD_PRESSURE_BP);
                            String pulseWave = obj.getString(BpProfile.PULSE_BP);
                            String ahr = obj.getString(BpProfile.MEASUREMENT_AHR_BP);
                            String hsd = obj.getString(BpProfile.MEASUREMENT_HSD_BP);
                            str = "date:"
                                + date
                                + "hightPressure:"
                                + hightPressure
                                + "\n"
                                + "lowPressure:"
                                + lowPressure
                                + "\n"
                                + "pulseWave"
                                + pulseWave
                                + "\n"
                                + "ahr:"
                                + ahr
                                + "\n"
                                + "hsd:"
                                + hsd
                                + "\n";
                        }
                    }
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = str;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_HISTORICAL_NUM_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String num = info.getString(BpProfile.HISTORICAL_NUM_BP);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "num: " + num;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_IS_ENABLE_OFFLINE.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String isEnableoffline = info.getString(BpProfile.IS_ENABLE_OFFLINE);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "isEnableoffline: " + isEnableoffline;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_ONLINE_PRESSURE_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String pressure = info.getString(BpProfile.BLOOD_PRESSURE_BP);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "pressure: " + pressure;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_ONLINE_PULSEWAVE_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String pressure = info.getString(BpProfile.BLOOD_PRESSURE_BP);
                    String wave = info.getString(BpProfile.PULSEWAVE_BP);
                    String heartbeat = info.getString(BpProfile.FLAG_HEARTBEAT_BP);
                    Message msg = new Message();
                    msg.what = HANDLER_MESSAGE;
                    msg.obj = "pressure:" + pressure + "\n" + "wave: " + wave + "\n" + " - heartbeat:" + heartbeat;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_ONLINE_RESULT_BP.equals(action)) {
                try {
                    JSONObject info = new JSONObject(message);
                    String highPressure = info.getString(BpProfile.HIGH_BLOOD_PRESSURE_BP);
                    String lowPressure = info.getString(BpProfile.LOW_BLOOD_PRESSURE_BP);
                    String ahr = info.getString(BpProfile.MEASUREMENT_AHR_BP);
                    String pulse = info.getString(BpProfile.PULSE_BP);
                    Message msg = new Message();
                    msg.what = MESSAGE_MEASURE_END;
                    msg.obj = "highPressure: "
                        + highPressure
                        + "lowPressure: "
                        + lowPressure
                        + "ahr: "
                        + ahr
                        + "pulse: "
                        + pulse;

                    Bundle bundle = new Bundle();
                    bundle.putString("low", lowPressure);
                    bundle.putString("hight", highPressure);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (BpProfile.ACTION_ZOREING_BP.equals(action)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = "zoreing";
                mHandler.sendMessage(msg);
            } else if (BpProfile.ACTION_ZOREOVER_BP.equals(action)) {
                Message msg = new Message();
                msg.what = HANDLER_MESSAGE;
                msg.obj = "zoreover";
                mHandler.sendMessage(msg);
            } else if (BpProfile.ACTION_COMMUNICATION_TIMEOUT.equals(action)) {
                Message msg = new Message();
                msg.what = MESSAGE_MEASURE_TIMEOUT;
                mHandler.sendMessage(msg);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_personal_center:
                Intent personalIntent = new Intent(this, PersonalCenterActivity.class);
                startActivity(personalIntent);
                break;
            case R.id.next_step:
                String sbp = mEtSBP.getText().toString();
                String dbp = mEtDBP.getText().toString();
                if (StringUtils.isEmpty(sbp)
                    || sbp.substring(0, 1).equals("0")
                    || StringUtils.isEmpty(dbp)
                    || dbp.substring(0, 1).equals("0")) {
                    DialogUtils.showAlertDialog(this, "提交失败", "请输入正确的血压值");
                    return;
                }
                int checkId = mGroup.getCheckedRadioButtonId();
                //未选择口服盐编号
                if (checkId == -1) {
                    mHint.setVisibility(View.VISIBLE);
                    String alertText = "所有内容均为必填项，请填写完整。当前 <font color=\"#FE0000\">口服盐咀嚼片</font>未选";
                    DialogUtils.showAlertDialog(this, "提交失败", alertText);
                    return;
                }
                Intent nextIntent = new Intent(this, QuestionListActivity.class);

                nextIntent.putExtra("orname", TextUtils.isEmpty(doctorName) ? "暂无数据" : doctorName);
                nextIntent.putExtra("sbp", sbp);
                nextIntent.putExtra("dbp", dbp);
                int index = 0;
                if (second.isChecked()) {
                    index = 2;
                } else if (third.isChecked()) {
                    index = 3;
                } else if (fourth.isChecked()) {
                    index = 4;
                } else if (first.isChecked()) {
                    index = 1;
                }
                nextIntent.putExtra("index", index);
                //startActivity(nextIntent);
                startActivityForResult(nextIntent, 0);
                break;
            case R.id.home_auto_get:
                if ("自动获取".equals(mAuto.getText().toString())) {
                    mAuto.setText("获取中，请稍等...");
                    mEtSBP.setText("");
                    mEtDBP.setText("");
                    if (bp3lControl != null) {
                        bp3lControl.startMeasure();
                    } else {
                        ToastUtils.show(context, "iHealth未连接");
                    }
                }
                if ("连接设备".equals(mAuto.getText().toString())) {
                    mAuto.setText("连接中，请稍等...");
                    boolean req = iHealthDevicesManager.getInstance()
                        .connectDevice(userName, deviceMac, iHealthDevicesManager.TYPE_BP3L);
                    if (!req) {
                        Toast.makeText(BloodMeasureActivity.this,
                            "Haven’t permission to connect this device or the mac is not valid", Toast.LENGTH_LONG)
                            .show();
                    }
                }
                break;
            case R.id.home_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        mHint.setVisibility(View.GONE);
    }

    @Override
    public boolean success(List<Doctor> doctors) {
        UserHelper.updateDoctorList(doctors);
        doctorList.clear();
        doctorList.addAll(doctors);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinnerAdapter.notifyDataSetChanged();
            }
        });
        return false;
    }

    @Override
    public boolean fail() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            clearInput();
        }
    }

    private void clearInput() {
        mEtSBP.setText("");
        mEtDBP.setText("");
        for (int i = 0; i < mGroup.getChildCount(); i++) {
            View view = mGroup.getChildAt(i);
            if (view instanceof RadioButton) {
                ((RadioButton) view).setChecked(false);
            }
        }
    }
}
