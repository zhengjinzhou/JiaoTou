package zhou.com.jiaotou.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import zhou.com.jiaotou.activity.SplsahActivity;
import zhou.com.jiaotou.api.VpnApi;
import zhou.com.jiaotou.base.Constant;
import zhou.com.jiaotou.base.RxPresenter;
import zhou.com.jiaotou.bean.AppInfoBean;
import zhou.com.jiaotou.bean.SNIDList;
import zhou.com.jiaotou.contract.SelectContract;

/**
 * Created by zhou
 * on 2018/12/13.
 */

public class SelectPresenter extends RxPresenter<SelectContract.View> implements SelectContract.Presenter<SelectContract.View> {
   // VpnApi vpnApi;
    SplsahActivity splsahActivity;

    public SelectPresenter(SplsahActivity splsahActivity){
        this.splsahActivity = splsahActivity;
        //this.vpnApi = new VpnApi(new OkHttpClient());
    }

    @Override
    public void getSnidList() {

        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个request对象
        final Request request = new Request.Builder()
                .url(Constant.BASE_URL+"AppConfigHandler.ashx?Action=search&keyword="+getKeywork()+"&maxnum=10")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("----------------", "onError: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                List<SNIDList> lists=gson.fromJson(string,new TypeToken<List<SNIDList>>(){}.getType());
                mView.getSnidListSuccess(lists);
            }
        });
    }

    @Override
    public String getKeywork() {
        return mView.setKeywork();
    }

    @Override
    public void getAppInfo() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个request对象
        final Request request = new Request.Builder()
                .url(Constant.BASE_URL+"AppConfigHandler.ashx?Action=getappinfo&SNID="+getSNID())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("----------------", "onError: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                AppInfoBean appInfoBean = gson.fromJson(string, AppInfoBean.class);
                mView.getAppinfoSuccess(appInfoBean);
               // Log.d("-----AppInfo--======---", "onResponse: "+appInfoBean.toString());
            }
        });
    }

    @Override
    public String getSNID() {
        return mView.setSNID();
    }
}
