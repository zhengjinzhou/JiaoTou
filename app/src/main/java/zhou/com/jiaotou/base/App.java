package zhou.com.jiaotou.base;

import android.app.Application;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhou
 * on 2019/1/15.
 */

public class App extends Application {
    public App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        Log.d("", "onCreate: 尼玛的怎么不成功呢");
    }
}
