package com.wftd.kongyan.util;

import android.os.Looper;
import com.ihealth.communication.base.statistical.gson.Gson;
import com.ihealth.communication.base.statistical.gson.reflect.TypeToken;
import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.entity.LoginResult;
import com.wftd.kongyan.entity.Message;
import com.wftd.kongyan.entity.Question;
import com.wftd.kongyan.entity.User;
import com.wftd.kongyan.entity.Version;
import com.wftd.kongyan.callback.BaseCallback;
import com.wftd.kongyan.callback.DoctorCallback;
import com.wftd.kongyan.callback.LoginCallback;
import com.wftd.kongyan.callback.MessageCallback;
import com.wftd.kongyan.callback.ModifyCallback;
import com.wftd.kongyan.callback.PeopleCallback;
import com.wftd.kongyan.callback.QuestionCallback;
import com.wftd.kongyan.callback.VersionCallback;
import com.wftd.kongyan.app.Constant;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final String SERVER_ROOT = "http://47.93.150.167:8084/";
    private static final OkHttpClient client = new OkHttpClient();

    private static String createRequestUrl(String cmd, String method, Map<String, String> queryMap) {
        String param = createQueryString(queryMap);
        if (StringUtils.isEmpty(param)) {
            param = "";
        }
        String rootUrl = SERVER_ROOT;
        rootUrl += cmd + "/" + method + param;
        return rootUrl;
    }

    private static String createQueryString(Map<String, String> queryMap) {
        if (queryMap == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                String key = entry.getKey().trim();
                String value = URLEncoder.encode(entry.getValue().trim(), "utf-8");
                sb.append(String.format("%s=%s&", key, value));
            }
            return "?" + sb.substring(0, sb.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void PeopleGet(String organizationId, final PeopleCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(SERVER_ROOT + "sc/people/list?organizationId=" + organizationId)
            .method("Get", null)
            .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                switch (code) {
                    case 0:
                        String data = jsonObject.getString("data");

                        List<User> loginResultDTO = new Gson().fromJson(data, new TypeToken<List<User>>() {
                        }.getType());
                        callback.success(loginResultDTO);
                        break;
                    case 1:
                        callback.fail();
                        break;

                    default:
                        callback.fail();
                        break;
                }
            }
        });
    }

    public static void LoginGet(String username, String pwd, final LoginCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request =
            new Request.Builder().url(SERVER_ROOT + "sc/people/login?phoneNumber=" + username + "&password=" + pwd)
                .method("Get", null)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                switch (code) {
                    case 0:
                        String data = jsonObject.getString("data");
                        LoginResult loginResultDTO = new Gson().fromJson(data, LoginResult.class);
                        callback.success(loginResultDTO.getPeople());
                        break;
                    case 1:
                        callback.fail();
                        break;

                    default:
                        callback.fail();
                        break;
                }
            }
        });
    }

    public static void DoctorGet(String doctor, final DoctorCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(SERVER_ROOT + "sc/doctor/list?organizationId=" + doctor)
            .method("Get", null)
            .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                switch (code) {
                    case 0:
                        String data = jsonObject.getString("data");
                        List<Doctor> doctors = new Gson().fromJson(data, new TypeToken<List<Doctor>>() {
                        }.getType());
                        callback.success(doctors);
                        break;
                    case 1:
                        callback.fail();
                        break;

                    default:
                        callback.fail();
                        break;
                }
            }
        });
    }

    public static void modifyGet(String username, String pwd, final ModifyCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request =
            new Request.Builder().url(SERVER_ROOT + "sc/people/password?phoneNumber=" + username + "&password=" + pwd)
                .method("Get", null)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                if (code == 0) {
                    callback.success();
                } else {
                    callback.fail();
                }
            }
        });
    }

    public static void QuestionPost(final List<Question> question, final QuestionCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String json = new Gson().toJson(question);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder().url(SERVER_ROOT + "sc/collect/add").post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                if (code == 0) {
                    if (question.size() > 1) {
                        callback.success();
                    } else {
                        callback.success(question.get(0));
                    }
                } else {
                    callback.fail();
                }
            }
        });
    }

    public static void MessageGet(String id, final MessageCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request =
            new Request.Builder().url(SERVER_ROOT + "sc/message/list?peopleId=" + id).method("Get", null).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                if (code == 0) {
                    String data = jsonObject.getString("data");
                    List<Message> messages = new Gson().fromJson(data, new TypeToken<List<Message>>() {
                    }.getType());
                    callback.success(messages);
                } else {
                    callback.fail();
                }
            }
        });
    }
    public static void appUpdate(final VersionCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request =
            new Request.Builder().url(SERVER_ROOT + "sc/app/version").method("Get", null).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                JSONObject jsonObject = JSONObject.fromObject(result);
                int code = jsonObject.getInt("code");
                if (code == 0) {
                    String data = jsonObject.getString("data");
                    Version messages = new Gson().fromJson(data, new TypeToken<Version>() {
                    }.getType());
                    callback.success(messages);
                } else {
                    callback.fail();
                }
            }
        });
    }

    /**
     * 登录
     */
    //public static void login(String cmd, String method, Map<String, String> param, final LoginCallback callback) {
    //    OkHttpClient mOkHttpClient = new OkHttpClient();
    //    String url = createRequestUrl(cmd, method, param);
    //    Request.Builder requestBuilder = new Request.Builder().url(url);
    //    Request request = requestBuilder.build();
    //    Call call = mOkHttpClient.newCall(request);
    //    call.enqueue(new Callback() {
    //        @Override
    //        public void onFailure(Call call, IOException e) {
    //            Looper.prepare();
    //            callback.fail();
    //            Looper.loop();
    //        }
    //
    //        @Override
    //        public void onResponse(Call call, Response response) throws IOException {
    //            try {
    //                Looper.prepare();
    //                String result = response.body().string();
    //                JSONObject jsonObject = JSONObject.fromObject(result);
    //                if ((int) jsonObject.get("code") == 0) {
    //                    JSONObject userObj = (JSONObject) jsonObject.get("data");
    //                    int id = (int) userObj.get("id");
    //                    String userCode = (String) userObj.get("userCode");
    //                    String userRealName = (String) userObj.get("userRealName");
    //                    String userName = (String) userObj.get("userName");
    //                    String passWord = (String) userObj.get("passWord");
    //                    User user = new User(id, userCode, userRealName, userName, passWord);
    //                    callback.success(CallbackId.CALL_BACK_LOGIN, user);
    //                    Looper.loop();
    //                }
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //
    //        }
    //    });
    //}

    /**
     * 修改密码
     */
    public static void modifyPassWord(String cmd, String method, Map<String, String> param,
        final LoginCallback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = createRequestUrl(cmd, method, param);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                callback.fail();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Looper.prepare();
                    String result = response.body().string();
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    if ((int) jsonObject.get("code") == 0) {
                        JSONObject resultObj = (JSONObject) jsonObject.get("data");
                        boolean modifyResult = (boolean) resultObj.get("result");
                        //                        callback.success(CallbackId.CALL_BACK_LOGIN, modifyResult);
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传调查问卷
     */
    public static void upload(String cmd, String method, Map<String, String> param, String json,
        final BaseCallback callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = createRequestUrl(cmd, method, param);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                callback.fail();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Looper.prepare();
                    String result = response.body().string();
                    JSONObject jsonObject = JSONObject.fromObject(result);
                    if ((int) jsonObject.get("code") == 0) {
                        JSONObject resultObj = (JSONObject) jsonObject.get("data");
                        boolean modifyResult = (boolean) resultObj.get("result");
                        callback.success(Constant.CALL_BACK_MODIFY_PASSWORD, modifyResult);
                        Looper.loop();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
