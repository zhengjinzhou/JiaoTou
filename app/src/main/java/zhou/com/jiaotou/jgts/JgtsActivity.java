package zhou.com.jiaotou.jgts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import zhou.com.jiaotou.R;
import zhou.com.jiaotou.bean.JgUrlBean;

public class JgtsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jgts);

        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            String url = null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.d("11111", "onCreate: "+bundle.getString(JPushInterface.EXTRA_EXTRA));
                url = bundle.getString(JPushInterface.EXTRA_EXTRA);
            }
            Gson gson = new Gson();
            JgUrlBean jgUrlBean = gson.fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), JgUrlBean.class);
            Log.d("打印", "onCreate: "+jgUrlBean.getUrl());
            Log.d("获取到的信息", "onCreate: "+"Title : " + title + "  " + "Content : " + content+"123"+ url);
        }
        //addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }


}
