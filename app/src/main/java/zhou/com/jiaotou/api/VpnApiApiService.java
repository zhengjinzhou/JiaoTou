package zhou.com.jiaotou.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
import zhou.com.jiaotou.bean.LoginBean;
import zhou.com.jiaotou.bean.NumBean;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public interface VpnApiApiService {
    @GET("Login/LoginHandler.ashx")
    Observable<LoginBean> vpnLogin(@Query("Action") String action, @Query("cmd") String cmd);

    @GET
    Call<String> requestPost(@Url() String url);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @FormUrlEncoded
    @POST("oasystem2018/Handlers/DMS_Handler.ashx")
    Observable<NumBean>
    GetPhoneAppNum(@Field("Action") String action, @Field("UserID") String UserID, @Field("Signature") String Signature, @Field("Timestamp") String Timestamp);

}
