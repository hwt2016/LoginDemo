package logindemo.hewentao.com.logindemo;

import org.xutils.x;
import android.app.Application;


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
