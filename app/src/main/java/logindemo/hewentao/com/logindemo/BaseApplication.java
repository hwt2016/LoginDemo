package logindemo.hewentao.com.logindemo;


import android.app.Application;

import org.xutils.x;


/**
 * Created by sa on 2017-06-02.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
