package zhou.com.jiaotou.api;

import android.content.Context;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import zhou.com.jiaotou.base.Constant;
import zhou.com.jiaotou.bean.AppInfoBean;
import zhou.com.jiaotou.bean.LoginBean;
import zhou.com.jiaotou.bean.NumBean;
import zhou.com.jiaotou.util.SpUtil;

/**
 * Created by zhou
 * on 2018/11/21.
 */

public class VpnApi {
    private VpnApiApiService service;
    private Context context;

    public VpnApi(OkHttpClient okHttpClient) {
        /**这里是换了服务器上的地址了*/
        AppInfoBean appInfoBean = (AppInfoBean) SpUtil.getObject(context, Constant.AppInfo, AppInfoBean.class);
        String baseUrl = appInfoBean.getDMSPhoneURL();

        Log.d("-=-=-=-=-=-", "VpnApi: "+baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(VpnApiApiService.class);
    }

    public Observable<LoginBean> vpnLogin(String action, String cmd){
        return service.vpnLogin(action,cmd);
    }

    public Observable<NumBean> GetPhoneAppNum(String action, String UserID, String Signature, String Timestamp){
        return service.GetPhoneAppNum(action,UserID,Signature,Timestamp);
    }

}