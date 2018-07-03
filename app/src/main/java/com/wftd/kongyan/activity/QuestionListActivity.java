package com.wftd.kongyan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.app.Constant;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity2;
import com.wftd.kongyan.callback.QuestionCallback;
import com.wftd.kongyan.db.DBHelper;
import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.entity.Question;
import com.wftd.kongyan.entity.Result;
import com.wftd.kongyan.util.CommonUtils;
import com.wftd.kongyan.util.DialogUtils;
import com.wftd.kongyan.util.DisplayUtils;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.LogUtils;
import com.wftd.kongyan.util.StringUtils;
import com.wftd.kongyan.view.AddressSelector;
import com.wftd.kongyan.view.address.City;
import com.wftd.kongyan.view.address.CityInterface;
import com.wftd.kongyan.view.address.OnItemClickListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

/**
 * 调查问卷页
 */
public class QuestionListActivity extends BaseActivity2
    implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
    QuestionCallback {
    private EditText mBaseAddress;//省份
    private EditText mBaseName;//姓名
    private EditText mBasePhone;//电话
    private EditText mBaseAge;//年龄
    private EditText mBaseSex;//性别
    private Spinner mBaseSexSpinner;//性别
    private String address;
    private String name;
    private String phone;
    private String age;
    private String sex;
    private String height;
    private String weight;

    private EditText Edoctor, Eaddress;

    private People mUser = UserHelper.getUserInfo();

    private static final String[] sexs = {
        "男", "女"
    };

    private EditText mBaseHeight;//身高
    private EditText mBaseWeight;//体重

    private EditText mSBP;//
    private EditText mDBP;//
    private String sbp;
    private String dbp;
    private int index;
    private RadioButton mKfy1;
    private RadioButton mKfy2;
    private RadioButton mKfy3;
    private RadioButton mKfy4;

    private CheckBox checkA, checkB, checkC, checkD, checkE, checkF, checkG;

    private RadioGroup mHighBloodGroup;

    private LinearLayout mJyyTypeGroup;
    private ScrollView mScrollerView;

    /**
     * 日常饮食
     */
    private RadioGroup mRadioGroup11Text;
    private RadioGroup mRadioGroup11Score;
    private TextView m11Hint;
    private String m11Content;
    private int m11Score;

    private RadioGroup mRadioGroup12Text;
    private RadioGroup mRadioGroup12Score;
    private TextView m12Hint;
    private String m12Content;
    private int m12Score;

    private RadioGroup mRadioGroup13Text;
    private RadioGroup mRadioGroup13Score;
    private TextView m13Hint;
    private String m13Content;
    private int m13Score;

    private RadioGroup mRadioGroup14Text;
    private RadioGroup mRadioGroup14Score;
    private TextView m14Hint;
    private String m14Content;
    private int m14Score;

    private RadioGroup mRadioGroup15Text;
    private RadioGroup mRadioGroup15Score;
    private TextView m15Hint;
    private String m15Content;
    private int m15Score;

    private RadioGroup mRadioGroup16Text;
    private RadioGroup mRadioGroup16Score;
    private TextView m16Hint;
    private String m16Content;
    private int m16Score;

    /**
     * 常用食物
     */
    private RadioGroup mRadioGroup21Text;
    private RadioGroup mRadioGroup21Score;
    private TextView m21Hint;
    private String m21Content;
    private int m21Score;

    private RadioGroup mRadioGroup22Text;
    private RadioGroup mRadioGroup22Score;
    private TextView m22Hint;
    private String m22Content;
    private int m22Score;

    private RadioGroup mRadioGroup23Text;
    private RadioGroup mRadioGroup23Score;
    private TextView m23Hint;
    private String m23Content;
    private int m23Score;

    private RadioGroup mRadioGroup24Text;
    private RadioGroup mRadioGroup24Score;
    private TextView m24Hint;
    private String m24Content;
    private int m24Score;

    private RadioGroup mRadioGroup25Text;
    private RadioGroup mRadioGroup25Score;
    private TextView m25Hint;
    private String m25Content;
    private int m25Score;

    private RadioGroup mRadioGroup26Text;
    private RadioGroup mRadioGroup26Score;
    private TextView m26Hint;
    private String m26Content;
    private int m26Score;

    private RadioGroup mRadioGroup27Text;
    private RadioGroup mRadioGroup27Score;
    private TextView m27Hint;
    private String m27Content;
    private int m27Score;

    private Button mCommit;//提交
    private ImageView mBack;//返回

    private ArrayList<City> mProvinceList = new ArrayList<>();//省列表
    private String provinces;//省
    private String cities;//市
    private String areas;//县
    private ArrayList<City> mCityList = new ArrayList<>();//市列表
    private ArrayList<City> mAreaList = new ArrayList<>();//县列表
    private DBHelper mHelper;
    private PopupWindow mPopupWindow;
    private RadioGroup rg_is_high_blood;
    private RadioGroup rg_is_jyy;

    private boolean isHighBlood;
    private boolean isUsedJyy;
    private boolean highBloodSelected;
    private boolean jyySelected;
    private Result result1 = null;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        doctor = (Doctor) getIntent().getSerializableExtra("doctor");
        init();
        initData();
    }

    private void showAddress() {
        View view = LayoutInflater.from(this).inflate(R.layout.city_picker_pup, null);
        AddressSelector addressSelector = (AddressSelector) view.findViewById(R.id.address);
        addressSelector.setTextSelectedColor(R.color.white);
        addressSelector.setListTextNormalColor(R.color.white);
        addressSelector.setTextEmptyColor(R.color.white);
        addressSelector.setLineColor(R.color.white);
        addressSelector.setGrayLineColor(R.color.white);
        addressSelector.setTabAmount(3);
        addressSelector.setCities(mProvinceList);
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition) {
                switch (tabPosition) {
                    case 0:
                        mCityList = mHelper.queryCity(city.getCityCode());
                        addressSelector.setCities(mCityList);
                        provinces = city.getCityName();
                        cities = "";
                        areas = "";
                        mBaseAddress.setText(provinces + cities + areas);
                        break;
                    case 1:
                        mAreaList = mHelper.queryArea(city.getCityCode());
                        addressSelector.setCities(mAreaList);
                        cities = city.getCityName();
                        areas = "";
                        mBaseAddress.setText(provinces + cities + areas);
                        break;
                    case 2:
                        areas = city.getCityName();
                        mBaseAddress.setText(provinces + cities + areas);
                        if (null != mPopupWindow) {
                            mPopupWindow.dismiss();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()) {
                    case 0:
                        addressSelector.setCities(mProvinceList);
                        cities = "";
                        areas = "";
                        mBaseAddress.setText(provinces + cities + areas);
                        break;
                    case 1:
                        addressSelector.setCities(mCityList);
                        areas = "";
                        mBaseAddress.setText(provinces + cities + areas);
                        break;
                    case 2:
                        addressSelector.setCities(mAreaList);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });

        mPopupWindow =
            new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void initData() {
        //mProvinceList = mHelper.queryProvince();
    }

    private void init() {
        Intent value = getIntent();
        sbp = value.getStringExtra("sbp");
        dbp = value.getStringExtra("dbp");
        index = value.getIntExtra("index", 0);
        //基本信息
        initBaseInfo();
        //血压情况
        initHighBlood();
        //口服盐咀嚼片
        initKFY();
        //日常饮食
        initDailyDiet();
        //常用食物
        initCommonFood();

        mCommit = (Button) findViewById(R.id.question_commit);
        mBack = (ImageView) findViewById(R.id.question_back);
        mScrollerView = (ScrollView) findViewById(R.id.question_scrollview);
        //性别适配
        initSexSpinner();

        mHelper = new DBHelper(this);
        mCommit.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }

    private void initSexSpinner() {
        mBaseSexSpinner = (Spinner) findViewById(R.id.base_sex_spinner);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexs);
        //设置自定义下拉菜单样式
        adapter.setDropDownViewResource(R.layout.item_sex_menu);
        //将适配器绑定到下拉菜单
        mBaseSexSpinner.setAdapter(adapter);
        mBaseSexSpinner.setOnItemSelectedListener(this);
    }

    //日常饮食
    private void initDailyDiet() {
        mRadioGroup11Text = (RadioGroup) findViewById(R.id.subject_11_text_group);
        mRadioGroup11Score = (RadioGroup) findViewById(R.id.subject_11_score_group);
        m11Hint = (TextView) findViewById(R.id.subject_11_hint);

        mRadioGroup12Text = (RadioGroup) findViewById(R.id.subject_12_text_group);
        mRadioGroup12Score = (RadioGroup) findViewById(R.id.subject_12_score_group);
        m12Hint = (TextView) findViewById(R.id.subject_12_hint);

        mRadioGroup13Text = (RadioGroup) findViewById(R.id.subject_13_text_group);
        mRadioGroup13Score = (RadioGroup) findViewById(R.id.subject_13_score_group);
        m13Hint = (TextView) findViewById(R.id.subject_13_hint);

        mRadioGroup14Text = (RadioGroup) findViewById(R.id.subject_14_text_group);
        mRadioGroup14Score = (RadioGroup) findViewById(R.id.subject_14_score_group);
        m14Hint = (TextView) findViewById(R.id.subject_14_hint);

        mRadioGroup15Text = (RadioGroup) findViewById(R.id.subject_15_text_group);
        mRadioGroup15Score = (RadioGroup) findViewById(R.id.subject_15_score_group);
        m15Hint = (TextView) findViewById(R.id.subject_15_hint);

        mRadioGroup16Text = (RadioGroup) findViewById(R.id.subject_16_text_group);
        mRadioGroup16Score = (RadioGroup) findViewById(R.id.subject_16_score_group);
        m16Hint = (TextView) findViewById(R.id.subject_16_hint);

        mRadioGroup11Text.setOnCheckedChangeListener(this);
        mRadioGroup11Score.setOnCheckedChangeListener(this);
        mRadioGroup12Text.setOnCheckedChangeListener(this);
        mRadioGroup12Score.setOnCheckedChangeListener(this);
        mRadioGroup13Text.setOnCheckedChangeListener(this);
        mRadioGroup13Score.setOnCheckedChangeListener(this);
        mRadioGroup14Text.setOnCheckedChangeListener(this);
        mRadioGroup14Score.setOnCheckedChangeListener(this);
        mRadioGroup15Text.setOnCheckedChangeListener(this);
        mRadioGroup15Score.setOnCheckedChangeListener(this);
        mRadioGroup16Text.setOnCheckedChangeListener(this);
        mRadioGroup16Score.setOnCheckedChangeListener(this);
    }

    //常用食物
    private void initCommonFood() {
        mRadioGroup21Text = (RadioGroup) findViewById(R.id.subject_21_text_group);
        mRadioGroup21Score = (RadioGroup) findViewById(R.id.subject_21_score_group);
        m21Hint = (TextView) findViewById(R.id.subject_21_hint);

        mRadioGroup22Text = (RadioGroup) findViewById(R.id.subject_22_text_group);
        mRadioGroup22Score = (RadioGroup) findViewById(R.id.subject_22_score_group);
        m22Hint = (TextView) findViewById(R.id.subject_22_hint);

        mRadioGroup23Text = (RadioGroup) findViewById(R.id.subject_23_text_group);
        mRadioGroup23Score = (RadioGroup) findViewById(R.id.subject_23_score_group);
        m23Hint = (TextView) findViewById(R.id.subject_23_hint);

        mRadioGroup24Text = (RadioGroup) findViewById(R.id.subject_24_text_group);
        mRadioGroup24Score = (RadioGroup) findViewById(R.id.subject_24_score_group);
        m24Hint = (TextView) findViewById(R.id.subject_24_hint);

        mRadioGroup25Text = (RadioGroup) findViewById(R.id.subject_25_text_group);
        mRadioGroup25Score = (RadioGroup) findViewById(R.id.subject_25_score_group);
        m25Hint = (TextView) findViewById(R.id.subject_25_hint);

        mRadioGroup26Text = (RadioGroup) findViewById(R.id.subject_26_text_group);
        mRadioGroup26Score = (RadioGroup) findViewById(R.id.subject_26_score_group);
        m26Hint = (TextView) findViewById(R.id.subject_26_hint);

        mRadioGroup27Text = (RadioGroup) findViewById(R.id.subject_27_text_group);
        mRadioGroup27Score = (RadioGroup) findViewById(R.id.subject_27_score_group);
        m27Hint = (TextView) findViewById(R.id.subject_27_hint);

        mRadioGroup21Text.setOnCheckedChangeListener(this);
        mRadioGroup21Score.setOnCheckedChangeListener(this);
        mRadioGroup22Text.setOnCheckedChangeListener(this);
        mRadioGroup22Score.setOnCheckedChangeListener(this);
        mRadioGroup23Text.setOnCheckedChangeListener(this);
        mRadioGroup23Score.setOnCheckedChangeListener(this);
        mRadioGroup24Text.setOnCheckedChangeListener(this);
        mRadioGroup24Score.setOnCheckedChangeListener(this);
        mRadioGroup25Text.setOnCheckedChangeListener(this);
        mRadioGroup25Score.setOnCheckedChangeListener(this);
        mRadioGroup26Text.setOnCheckedChangeListener(this);
        mRadioGroup26Score.setOnCheckedChangeListener(this);
        mRadioGroup27Text.setOnCheckedChangeListener(this);
        mRadioGroup27Score.setOnCheckedChangeListener(this);
    }

    //口服盐咀嚼片
    private void initKFY() {
        mKfy1 = (RadioButton) findViewById(R.id.kfy_jjp1);
        mKfy2 = (RadioButton) findViewById(R.id.kfy_jjp2);
        mKfy3 = (RadioButton) findViewById(R.id.kfy_jjp3);
        mKfy4 = (RadioButton) findViewById(R.id.kfy_jjp4);
        if (index == 1) {
            mKfy1.setChecked(true);
        } else if (index == 2) {
            mKfy2.setChecked(true);
        } else if (index == 3) {
            mKfy3.setChecked(true);
        } else if (index == 4) {
            mKfy4.setChecked(true);
        }
    }

    //高血压相关
    private void initHighBlood() {
        mSBP = (EditText) findViewById(R.id.input_sbp);
        mDBP = (EditText) findViewById(R.id.input_dbp);
        mSBP.setText(sbp);
        mDBP.setText(dbp);
        //是否高血压患者
        rg_is_high_blood = (RadioGroup) findViewById(R.id.rg_is_high_blood);
        mHighBloodGroup = (RadioGroup) findViewById(R.id.high_blood_group);
        setHighBlood(false);
        //是否服用降压药
        rg_is_jyy = (RadioGroup) findViewById(R.id.rg_is_jyy);

        mJyyTypeGroup = (LinearLayout) findViewById(R.id.jjy_type_group);
        setJyyType(false);
        checkA = (CheckBox) findViewById(R.id.checkA);
        checkB = (CheckBox) findViewById(R.id.checkB);
        checkC = (CheckBox) findViewById(R.id.checkC);
        checkD = (CheckBox) findViewById(R.id.checkD);
        checkE = (CheckBox) findViewById(R.id.checkE);
        checkF = (CheckBox) findViewById(R.id.checkF);
        checkG = (CheckBox) findViewById(R.id.checkG);
        rg_is_high_blood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_blood1) {
                    isHighBlood = true;
                    setHighBlood(true);
                } else {
                    isHighBlood = false;
                    setHighBlood(false);
                }
                highBloodSelected = true;
            }
        });
        rg_is_jyy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_jyy1) {
                    isUsedJyy = true;
                    setJyyType(true);
                } else {
                    isUsedJyy = false;
                    setJyyType(false);
                }
                jyySelected = true;
            }
        });
    }

    //基本信息
    private void initBaseInfo() {
        Edoctor = (EditText) findViewById(R.id.doctor);
        Eaddress = (EditText) findViewById(R.id.address);
        mBaseAddress = (EditText) findViewById(R.id.base_address);
        mBaseName = (EditText) findViewById(R.id.base_name);
        mBasePhone = (EditText) findViewById(R.id.base_phone);
        mBaseAge = (EditText) findViewById(R.id.base_age);
        mBaseSex = (EditText) findViewById(R.id.base_sex);
        mBaseHeight = (EditText) findViewById(R.id.base_height);
        mBaseWeight = (EditText) findViewById(R.id.base_weight);
        Edoctor.setText(doctor != null ? doctor.getName() : "暂无数据");
        Eaddress.setText(TextUtils.isEmpty(mUser.getOrgnizationName()) ? "暂无数据" : mUser.getOrgnizationName());
        mBaseAddress.setOnClickListener(this);
        mBaseName.setOnClickListener(this);
        mBasePhone.setOnClickListener(this);
        mBaseAge.setOnClickListener(this);
        mBaseSex.setOnClickListener(this);
        mBaseHeight.setOnClickListener(this);
        mBaseWeight.setOnClickListener(this);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //提交
            case R.id.question_commit:
                boolean result = checkQuestionSelection();
                if (!result) {
                    return;
                }
                int mNumber = getValues();//获取的总值
                //这里需要判断一下对应的药物

                boolean isNext = false;
                switch (index) {
                    case 1:
                        if (1 <= mNumber && mNumber <= 19) {
                            isNext = true;
                        }
                        if (mNumber <= 18) {
                            mNumber = 19;
                        }
                        break;
                    case 2:
                        isNext = true;
                        break;
                    case 3:
                        isNext = true;
                        break;
                    case 4:
                        if (mNumber >= 9) {
                            isNext = true;
                        }
                        break;
                    default:
                        break;
                }

                if (!isNext) {
                    DialogUtils.showAlertDialog(context, "提示", "得分与适用盐度有出入建议复核问卷数据");
                    return;
                }

                //if (mNumber < 9) {
                //    //没有超标
                //    result1 = new Result(name, sex.equals("女") == true ? "1" : "0", sbp + "/" + dbp + "mmHg", "30%",
                //        mNumber + "", "低盐（食盐摄入量合适）- 处于正常范围 - 保持清淡饮食，合理膳食。");
                //} else if (9 <= mNumber && mNumber <= 13) {
                //    result1 = new Result(name, sex.equals("女") == true ? "1" : "0", sbp + "/" + dbp + "mmHg", "30%",
                //        mNumber + "", "正常（食盐摄入量合适）- 处于正常范围 - 请保持清淡饮食，建议咨询门诊医生是否需要调整降压治疗。");
                //} else if (14 <= mNumber && mNumber <= 19) {
                //    result1 = new Result(name, sex.equals("女") == true ? "1" : "0", sbp + "/" + dbp + "mmHg", "30%",
                //        mNumber + "", "中盐（食盐摄入量偏高）- 超出正常范围，偏高 - 请咨询门诊医生是否需要调整您的饮食习惯，建议您定期测量血压。");
                //} else if (20 <= mNumber) {
                //    result1 = new Result(name, sex.equals("女") == true ? "1" : "0", sbp + "/" + dbp + "mmHg", "30%",
                //        mNumber + "", "高盐（食盐摄入量偏高）- 超出正常范围，偏高 - 请咨询门诊医生是否需要调整您的饮食习惯，您的血压水平是否合适，以获得更恰当的治疗。");
                //}
                //if (result1 == null) {
                //    return;
                //}
                try {
                    Question saveQuestion = new Question();
                    saveQuestion.setId(0);
                    saveQuestion.setName(name);
                    saveQuestion.setSex(sex.equals("女") == true ? 1 : 0);
                    saveQuestion.setPhoneNumber(phone);
                    saveQuestion.setHeight(height);
                    saveQuestion.setWeight(weight);
                    saveQuestion.setAge(Integer.valueOf(age));
                    saveQuestion.setDistrict(mUser.getOrgnizationName());
                    saveQuestion.setSystolicPressure(Integer.valueOf(sbp));
                    saveQuestion.setDiastolicPressure(Integer.valueOf(dbp));
                    saveQuestion.setSaltThreshold(index);

                    if (isHighBlood) {
                        if ((mHighBloodGroup.getCheckedRadioButtonId() % 2) == 1) {
                            saveQuestion.setPatientType(2);
                        } else {
                            saveQuestion.setPatientType(3);
                        }
                    } else {
                        saveQuestion.setPatientType(1);
                    }
                    saveQuestion.setTakeDrugsA(checkA.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsB(checkB.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsC(checkC.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsD(checkD.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsE(checkE.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsF(checkF.isChecked() != true ? 0 : 1);
                    saveQuestion.setTakeDrugsG(checkG.isChecked() != true ? 0 : 1);

                    saveQuestion.setQ1(m11Score);
                    saveQuestion.setQ2(m12Score);
                    saveQuestion.setQ3(m13Score);
                    saveQuestion.setQ4(m14Score);
                    saveQuestion.setQ5(m15Score);
                    saveQuestion.setQ6(m16Score);
                    saveQuestion.setQ7(m21Score);
                    saveQuestion.setQ8(m22Score);
                    saveQuestion.setQ9(m23Score);
                    saveQuestion.setQ10(m24Score);
                    saveQuestion.setQ11(m25Score);
                    saveQuestion.setQ12(m26Score);
                    saveQuestion.setQ13(m27Score);
                    saveQuestion.setScore(mNumber);
                    saveQuestion.setPeopleId(mUser.getId());
                    saveQuestion.setDoctorId(doctor != null ? doctor.getId() : "0");
                    saveQuestion.setOrganizationId(mUser.getOrganizationId());
                    result1 = Result.getRelult(saveQuestion, mNumber);
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    saveQuestion.setSubmitDate(df.format(new Date()));
                    saveQuestion.setLoginUserId(mUser.getId());
                    db.save(saveQuestion);
                    //存储后在获取出来，主要是为了获取数据库生成的id，这也是个办法
                    List<Question> allData = db.findAll(Question.class);
                    Question question = allData.get(allData.size() - 1);
                    List<Question> questions = new ArrayList<>();
                    questions.add(question);
                    HttpUtils.QuestionPost(questions, this);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            //返回
            case R.id.question_back:
                finish();
                break;
            //省份
            case R.id.base_address:
                showAddress();
                break;
            case R.id.base_name:
                questFocus(mBaseName);
                break;
            case R.id.base_phone:
                questFocus(mBasePhone);
                break;
            case R.id.base_age:
                questFocus(mBaseAge);
                break;
            case R.id.base_sex:
                questFocus(mBaseSex);
                break;
            case R.id.base_height:
                questFocus(mBaseHeight);
                break;
            case R.id.base_weight:
                questFocus(mBaseWeight);
                break;
            case R.id.input_sbp:
                questFocus(mSBP);
                break;
            case R.id.input_dbp:
                questFocus(mDBP);
                break;
            default:
                break;
        }
    }

    /**
     * 检查是否都填写完毕
     */
    private boolean checkQuestionSelection() {
        address = mBaseAddress.getText().toString();
        name = mBaseName.getText().toString();
        phone = mBasePhone.getText().toString();
        age = mBaseAge.getText().toString();
        sex = sexs[mBaseSexSpinner.getSelectedItemPosition()];
        height = mBaseHeight.getText().toString();
        weight = mBaseWeight.getText().toString();
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(age) || StringUtils.isEmpty(
            sex) || StringUtils.isEmpty(height) || StringUtils.isEmpty(weight)) {
            DialogUtils.showAlertDialog(context, "提交失败", "基本信息不完整");
            mScrollerView.scrollTo(0, 0);
            return false;
        }
        if (!CommonUtils.isMobile(phone)) {
            mScrollerView.scrollTo(0, 0);
            DialogUtils.showAlertDialog(context, "提交失败", "请输入正确的手机号");
            return false;
        }

        if (age.substring(0, 1).equals("0") || Integer.parseInt(age) < 0 || Integer.parseInt(age) > 150) {
            mScrollerView.scrollTo(0, 0);
            DialogUtils.showAlertDialog(context, "提交失败", "请输入正确的年龄");
            return false;
        }
        if (height.substring(0, 1).equals("0") || Integer.parseInt(height) < 10 || Integer.parseInt(height) > 300) {
            mScrollerView.scrollTo(0, 0);
            DialogUtils.showAlertDialog(context, "提交失败", "请输入正确的身高");
            return false;
        }
        if (weight.substring(0, 1).equals("0") || Integer.parseInt(weight) < 1 || Integer.parseInt(weight) > 500) {
            mScrollerView.scrollTo(0, 0);
            DialogUtils.showAlertDialog(context, "提交失败", "请输入正确的体重");
            return false;
        }

        if (!highBloodSelected) {
            scrollTarget(rg_is_high_blood);
            DialogUtils.showAlertDialog(context, "提交失败", "请选择是否高血压患者");
            return false;
        }
        if (isHighBlood && mHighBloodGroup.getCheckedRadioButtonId() == -1) {
            scrollTarget(rg_is_high_blood);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (!jyySelected) {
            scrollTarget(rg_is_jyy);
            DialogUtils.showAlertDialog(context, "提交失败", "请选择目前是否服用降压药");
            return false;
        }
        if (isUsedJyy) {
            boolean hasJyyChecked = false;
            for (int i = 0; i < mJyyTypeGroup.getChildCount(); i++) {
                CheckBox button = (CheckBox) mJyyTypeGroup.getChildAt(i);
                if (button.isChecked()) {
                    hasJyyChecked = true;
                    break;
                }
            }
            if (!hasJyyChecked) {
                scrollTarget(rg_is_jyy);
                DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
                return false;
            }
        }
        if (StringUtils.isEmpty(m11Content)) {
            m11Hint.setVisibility(View.VISIBLE);
            scrollTarget(m11Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m12Content)) {
            m12Hint.setVisibility(View.VISIBLE);
            scrollTarget(m12Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m13Content)) {
            m13Hint.setVisibility(View.VISIBLE);
            scrollTarget(m13Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m14Content)) {
            m14Hint.setVisibility(View.VISIBLE);
            scrollTarget(m14Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m15Content)) {
            m15Hint.setVisibility(View.VISIBLE);
            scrollTarget(m15Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m16Content)) {
            m16Hint.setVisibility(View.VISIBLE);
            scrollTarget(m16Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m21Content)) {
            m21Hint.setVisibility(View.VISIBLE);
            scrollTarget(m21Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m22Content)) {
            m22Hint.setVisibility(View.VISIBLE);
            scrollTarget(m22Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m23Content)) {
            m23Hint.setVisibility(View.VISIBLE);
            scrollTarget(m23Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m24Content)) {
            m24Hint.setVisibility(View.VISIBLE);
            scrollTarget(m24Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m25Content)) {
            m25Hint.setVisibility(View.VISIBLE);
            scrollTarget(m25Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m26Content)) {
            m26Hint.setVisibility(View.VISIBLE);
            scrollTarget(m26Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        if (StringUtils.isEmpty(m27Content)) {
            m27Hint.setVisibility(View.VISIBLE);
            scrollTarget(m27Hint);
            DialogUtils.showAlertDialog(context, "提交失败", "选项不可为空");
            return false;
        }
        return true;
    }

    //输入框请求焦点
    private void questFocus(EditText edit) {
        //edit.setFocusable(true);
        //edit.setFocusableInTouchMode(true);
        //edit.requestFocus();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (null != mPopupWindow && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private void setHighBlood(boolean state) {
        findViewById(R.id.layout_high_blood).setSelected(state);
        for (int i = 0; i < mHighBloodGroup.getChildCount(); i++) {

            RadioButton r = (RadioButton) mHighBloodGroup.getChildAt(i);
            if (!state) {
                mHighBloodGroup.clearCheck();
            }
            r.setEnabled(state);
        }
    }

    private void setJyyType(boolean state) {
        mJyyTypeGroup.setSelected(state);
        for (int i = 0; i < mJyyTypeGroup.getChildCount(); i++) {
            CheckBox c = (CheckBox) mJyyTypeGroup.getChildAt(i);
            if (!state) {
                c.setChecked(state);
            }
            c.setEnabled(state);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroupText, @IdRes int checkedId) {
        switch (radioGroupText.getId()) {
            case R.id.subject_11_text_group:
                getRadioButtonValues(11, radioGroupText, checkedId, mRadioGroup11Score);
                break;
            case R.id.subject_12_text_group:
                getRadioButtonValues(12, radioGroupText, checkedId, mRadioGroup12Score);
                break;
            case R.id.subject_13_text_group:
                getRadioButtonValues(13, radioGroupText, checkedId, mRadioGroup13Score);
                break;
            case R.id.subject_14_text_group:
                getRadioButtonValues(14, radioGroupText, checkedId, mRadioGroup14Score);
                break;
            case R.id.subject_15_text_group:
                getRadioButtonValues(15, radioGroupText, checkedId, mRadioGroup15Score);
                break;
            case R.id.subject_16_text_group:
                getRadioButtonValues(16, radioGroupText, checkedId, mRadioGroup16Score);
                break;
            case R.id.subject_21_text_group:
                getRadioButtonValues(21, radioGroupText, checkedId, mRadioGroup21Score);
                break;
            case R.id.subject_22_text_group:
                getRadioButtonValues(22, radioGroupText, checkedId, mRadioGroup22Score);
                break;
            case R.id.subject_23_text_group:
                getRadioButtonValues(23, radioGroupText, checkedId, mRadioGroup23Score);
                break;
            case R.id.subject_24_text_group:
                getRadioButtonValues(24, radioGroupText, checkedId, mRadioGroup24Score);
                break;
            case R.id.subject_25_text_group:
                getRadioButtonValues(25, radioGroupText, checkedId, mRadioGroup25Score);
                break;
            case R.id.subject_26_text_group:
                getRadioButtonValues(26, radioGroupText, checkedId, mRadioGroup26Score);
                break;
            case R.id.subject_27_text_group:
                getRadioButtonValues(27, radioGroupText, checkedId, mRadioGroup27Score);
                break;
            default:
                break;
        }
    }

    private void getRadioButtonValues(int subject, RadioGroup textGroup, @IdRes int checkedId, RadioGroup scoreGroup) {
        RadioButton textButton = (RadioButton) textGroup.findViewById(checkedId);
        int index = textGroup.indexOfChild(textButton);
        RadioButton scoreButton = (RadioButton) scoreGroup.getChildAt(index);
        setValue(subject, textButton, scoreButton);
    }

    private void setValue(int subject, RadioButton textButton, RadioButton scoreButton) {
        String score = scoreButton.getText().toString();
        switch (subject) {
            case 11:
                m11Content = textButton.getText().toString();
                m11Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m11Hint.setVisibility(View.GONE);
                break;
            case 12:
                m12Content = textButton.getText().toString();
                m12Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m12Hint.setVisibility(View.GONE);
                break;
            case 13:
                m13Content = textButton.getText().toString();
                m13Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m13Hint.setVisibility(View.GONE);
                break;
            case 14:
                m14Content = textButton.getText().toString();
                m14Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m14Hint.setVisibility(View.GONE);
                break;
            case 15:
                m15Content = textButton.getText().toString();
                m15Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m15Hint.setVisibility(View.GONE);
                break;
            case 16:
                m16Content = textButton.getText().toString();
                m16Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m16Hint.setVisibility(View.GONE);
                break;
            case 21:
                m21Content = textButton.getText().toString();
                m21Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m21Hint.setVisibility(View.GONE);
                break;
            case 22:
                m22Content = textButton.getText().toString();
                m22Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m22Hint.setVisibility(View.GONE);
                break;
            case 23:
                m23Content = textButton.getText().toString();
                m23Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m23Hint.setVisibility(View.GONE);
                break;
            case 24:
                m24Content = textButton.getText().toString();
                m24Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m24Hint.setVisibility(View.GONE);
                break;
            case 25:
                m25Content = textButton.getText().toString();
                m25Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m25Hint.setVisibility(View.GONE);
                break;
            case 26:
                m26Content = textButton.getText().toString();
                m26Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m26Hint.setVisibility(View.GONE);
                break;
            case 27:
                m27Content = textButton.getText().toString();
                m27Score = Integer.parseInt(score.substring(1, score.length() - 2));
                m27Hint.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public int getValues() {
        return m11Score
            + m12Score
            + m13Score
            + m14Score
            + m15Score
            + m16Score
            + m21Score
            + m22Score
            + m23Score
            + m24Score
            + m25Score
            + m26Score
            + m27Score;
    }

    @Override
    public void success(Question question) {
        if (question != null) {
            question.setUpdate(true);
            try {
                db.update(Question.class, WhereBuilder.b("id", "=", question.getId()), new KeyValue("isUpdate", true));
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, QuestionResultActivity.class);
                intent.putExtra("result", result1);
                startActivityForResult(intent, Constant.FINISH_ACTIVITY);
                finishActivity();
            }
        });
    }

    @Override
    public void success() {

    }

    @Override
    public void fail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtils.showAlertDialog(context, "提交失败", "请稍后到\"数据管理\"页面重新提交",
                    new DialogUtils.OnSubmitFailureListener() {
                        @Override
                        public void onClick() {
                            Intent intent = new Intent(context, QuestionResultActivity.class);
                            intent.putExtra("result", result1);
                            startActivityForResult(intent, Constant.FINISH_ACTIVITY);
                            finishActivity();
                        }
                    });
            }
        });
    }

    private void scrollTarget(final View targetView) {
        mScrollerView.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                targetView.getLocationOnScreen(location);
                int viewHeight = 0;
                // 获取scrollview实际高度
                for (int i = 0; i < mScrollerView.getChildCount(); i++) {
                    viewHeight += mScrollerView.getChildAt(i).getHeight();
                }
                LogUtils.d(TAG, "" + location[1]);
                LogUtils.d(TAG, "" + viewHeight);
                int offset = viewHeight + location[1] - DisplayUtils.getScreenHeight() - DisplayUtils.dp2px(100);
                if (offset < 0) {
                    offset = 0;
                }
                mScrollerView.smoothScrollTo(0, offset);
            }
        });
    }
}
