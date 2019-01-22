package zhou.com.jiaotou.util;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.jiaotou.base.Constant;
import zhou.com.jiaotou.bean.NumBean;
import zhou.com.jiaotou.bean.SelectBean;

import static java.lang.Integer.parseInt;

/**
 * Created by zhou
 * on 2018/7/26.
 */

public class StartService extends Service implements Runnable {
    private Thread thread;
    private int num = 0;
    public StartService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //创建服务时候调用，第一次创建
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyServer", "onCreate: 创建服务");
        //onCreate的时候创建初始化
        thread = new Thread( this);
        thread.start();
    }
    //每次服务启动调用，每次启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyServer", "onCreate: 启动服务");
        //如果服务并停止了，重新生成一个新的
        if(thread.isInterrupted()){
            thread = new Thread(this);
            thread.start();
        }
        return Service.START_STICKY;
    //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {
        int i=0;
        while (true){
            try {
                //每10秒钟进行一次输出
                Thread.sleep(10000);
                //Toast.makeText(getApplicationContext(),"服务启动"+i++,Toast.LENGTH_LONG).show();


                SelectBean selectBean = (SelectBean) SpUtil.getObject(this, Constant.Account, SelectBean.class);
                if (selectBean==null)
                    return ;
                String user = selectBean.getUser();
                String url = "http://221.4.134.50:8081/oasystem2018/Handlers/DMS_Handler.ashx";
                String time = DateUtil.lineHDate(new Date());

                String JPsd = time +user+"WanveDMSOA"+time;
                String Signature = Md5Util.encoder(JPsd);
                Log.d("", "run: ---------------------------"+time);
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody.Builder().add("Action", "GetPhoneAppNum")
                        .add("UserID", user)
                        .add("Signature", Signature)
                        .add("Timestamp", time)
                        .build();
                Request request = new Request.Builder().url(url).post(formBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("错误", "onFailure: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("打印", "onResponse: "+string);
                        Gson gson = new Gson();
                        NumBean numBean = gson.fromJson(string, NumBean.class);
                        BadgeNumUtil.setBadgeNum(getApplicationContext(),numBean.getHandleCount());
                        BadgeNumUtil.setBadgeCount(getApplicationContext(),numBean.getHandleCount());
                    }
                });

                //openApp("zhou.com.myapplication");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //    private void openApp(View v, String packageName) {
    private void openApp(String packageName) {
        //Context context = v.getContext();
        PackageInfo pi = null;
        //PackageManager pm = context.getPackageManager();
        PackageManager pm = getPackageManager();
        try {
            pi = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            packageName = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    //服务销毁的时候
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyServer", "onCreate: 销毁服务");
        Intent intent = new Intent("com.zhou.service.destroy");
        sendBroadcast(intent);
    }
}