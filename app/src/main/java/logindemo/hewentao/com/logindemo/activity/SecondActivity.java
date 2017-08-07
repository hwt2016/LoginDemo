package logindemo.hewentao.com.logindemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import logindemo.hewentao.com.logindemo.R;
import logindemo.hewentao.com.logindemo.oss.PutObjectSamples;

@ContentView(R.layout.activity_second)
public class SecondActivity extends AppCompatActivity {


    private OSS oss;

    // 运行sample前需要配置以下字段为有效的值
    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String uploadFilePath = "mnt/sdcard/DCIM/Camera/IMG_20170807_150304.jpg";

    private static final String testBucket = "xqjr";
    private static final String uploadObject = "user/icon/";


    private final String DIR_NAME = "oss";
    private final String FILE_NAME = "caifang.m4a";

    @ViewInject(R.id.textView)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent =getIntent();
        int a=intent.getIntExtra("Age",10);
        String name=intent.getStringExtra("name");
        textView=(TextView)findViewById(R.id.textView);
        textView.setText(a+" "+name);
        Toast toast=Toast.makeText(getApplicationContext(), "自定义显示位置的Toast", Toast.LENGTH_SHORT);
        //第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        //第三个参数：同的第二个参数道理一样
        //如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        toast.setGravity(Gravity.TOP|Gravity.CENTER, -50, 100);
        //屏幕居中显示，X轴和Y轴偏移量都是0
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("STS.NDw6aXsiFkdgnY8Ytya2JmwX2", "9WJDmiGTzaN2EbQiL7wBb2KugeHRx5MJz3zyLVPfRPrV", "CAISqgJ1q6Ft5B2yfSjIr4fCfdvsnrZn3KaMbH+JvXQsbb1mgrLz0Dz2IHtOf3JuBOAav/0/m2BR7vwdlqB6T55OSAmcNZIoFFaURLDiMeT7oMWQweEukf/MQBqXaXPS2MvVfJ+nLrf0ceusbFbpjzJ6xaCAGxypQ12iN+//6/tgdc9FcQSkRydNB9pKRG5ls9RIGnbNEvyvPxX2+CmyanBloQ1hk2hyxL2iy8mHkHrkgUb91/UeqvaWQP2tZNI+O4xkAZXnnr40VNKYj3MNtEcSpfcv0PYUom2YhLzHXQkNuSfhGvHP79hiIDV+YqUHAKNepJD+76Yj4LeIydmnl0cUbb4FCnyFG5rTyc/FCf6vMc0iabH4NnLC39aCLJDptBk+ZnYWJIz6jX+d2ZgbGoABiYdTC5GqE7/ts0WdFsLnN/u7GhI6oiUXiEjPTUYvnCc5Tj1brw1yOr6tuemIpqloZXBHgdKAxOel7pFl7FunOYRADLW7I16JUPo3eKc87v1obTcZspA1CnWg9dJSY7MiJ3BFe+JdyUqWXvZ/hnhpmKm+d0x5o2WqnyLFz1tYuDo=");

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        Log.i("TAG","***********");


        try {
            Log.i("MainActivity : ", "uploadFilePath : " + uploadFilePath);
            File uploadFile = new File(uploadFilePath);
            InputStream input = new FileInputStream(uploadFile);
            long fileLength = uploadFile.length();
            Log.i("MainActivity : ", "fileLength : " + fileLength);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 上传
        Button upload = (Button) findViewById(R.id.btn_second);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new PutObjectSamples(oss, testBucket, uploadObject, uploadFilePath).asyncPutObjectFromLocalFile();
                    }
                }).start();
            }
        });

    }
}
