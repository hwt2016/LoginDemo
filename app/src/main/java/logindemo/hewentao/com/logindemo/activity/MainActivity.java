package logindemo.hewentao.com.logindemo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import logindemo.hewentao.com.logindemo.R;
import logindemo.hewentao.com.logindemo.util.Urls;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.username_et)
    private EditText username_et;
    @ViewInject(R.id.password_et)
    private EditText password_et;
    @ViewInject(R.id.email_et)
    private EditText email_et;

    @ViewInject(R.id.result_tv)
    private TextView result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event({R.id.register_btn, R.id.login_btn, R.id.userinfo_btn})
    private void onClickListener(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                register();
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.userinfo_btn:
                getUserInfo();
                break;
            default:
                break;
        }
    }

    private void register() {
        hideInput(username_et);
        result_tv.setText("正在注册...");

        String phone = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        String email = email_et.getText().toString().trim();

        RequestParams params = new RequestParams(Urls.REGISTER_URL);
        params.addParameter("phone", phone);
        params.addParameter("password", password);
        System.out.println("1234");
        System.out.println("**5678**"+params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.contains("true")) {
                    result_tv.setText("注册成功");
                } else {
                    result_tv.setText("注册失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                result_tv.setText("注册失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void login() {
        hideInput(username_et);
        result_tv.setText("正在登录...");

        String phone = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        RequestParams params = new RequestParams(Urls.LOGIN_URL);
        params.addParameter("phone", phone);
        params.addParameter("password", password);
        System.out.println("**5678**"+params.toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result.contains("1")) {
                    result_tv.setText("登录成功");
                    Toast.makeText(getApplicationContext(), "自定义显示位置的Toast", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent();
                    intent.setClass(MainActivity.this,SecondActivity.class);
                    intent.putExtra("Age",20);
                    intent.putExtra("name","何文涛");
                    startActivity(intent);
                    MainActivity.this.finish();

                } else {
                    result_tv.setText("登录失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                result_tv.setText("登录失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getUserInfo() {
        hideInput(username_et);
        result_tv.setText("正在获取用户信息...");

        String username = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        RequestParams params = new RequestParams(Urls.USERINFO_URL);
        params.addParameter("username", username);
        params.addParameter("password", password);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject info = new JSONObject(result);

                        result_tv.setText("用户信息:用户名" + info.get("username")
                                + ",密码" + info.getString("password")
                                + ",邮箱" + info.getString("email"));
                    } catch (JSONException e) {
                        result_tv.setText("获取用户信息失败");
                    }
                } else {
                    result_tv.setText("获取用户信息失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                result_tv.setText("获取用户信息失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //强制隐藏键盘
    private void hideInput(View view){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
