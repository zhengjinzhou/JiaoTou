package zhou.com.jiaotou.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.jiaotou.R;
import zhou.com.jiaotou.api.VpnApi;
import zhou.com.jiaotou.base.Constant;
import zhou.com.jiaotou.bean.AppInfoBean;
import zhou.com.jiaotou.bean.LoginBean;
import zhou.com.jiaotou.bean.SNIDList;
import zhou.com.jiaotou.bean.SelectBean;
import zhou.com.jiaotou.contract.SelectContract;
import zhou.com.jiaotou.presenter.SelectPresenter;
import zhou.com.jiaotou.util.SpUtil;
import zhou.com.jiaotou.util.ToastUtil;

public class SplsahActivity extends AppCompatActivity implements SelectContract.View{

    private static final String TAG = "SplsahActivity";
    private SelectPresenter presenter = new SelectPresenter(this);
    private String SNID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsah);

        SpUtil.putString(this,"we","123");

        presenter.attachView(this);
        presenter.getSnidList();

        boolean aBoolean = SpUtil.getBoolean(getApplicationContext(), Constant.VPN_AUTO, false);//是否自动登录
        SelectBean selectBean = (SelectBean) SpUtil.getObject(getApplicationContext(), Constant.Account, SelectBean.class);//账号密码


        if (aBoolean) {
            //没有点击过vpn登录
            login(selectBean.getUser(), selectBean.getPsd());
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplsahActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * 没有使用vpn的自动登录
     *
     * @param user
     * @param psd
     */
    private void login(final String user, final String psd) {
        new VpnApi(new OkHttpClient()).vpnLogin("Login", "{UserID:'" + user + "',UserPsw:'" + psd + "'}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getApplicationContext(),"请检查网络");
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.isResult()){
                            Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                            intent.putExtra(Constant.ACCOUNT_USER,user);
                            intent.putExtra(Constant.ACCOUNT_PSD,psd);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.show(getApplicationContext(),loginBean.getMessage());
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                    }
                });
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void getSnidListSuccess(List<SNIDList> snidList) {
        Log.d(TAG, "getSnidListSuccess: "+snidList.toString());
        SNID = snidList.get(0).getSNID();
        presenter.getAppInfo();
    }

    @Override
    public String setKeywork() {
        return "交通投资";
    }

    @Override
    public void getAppinfoSuccess(AppInfoBean appInfoBean) {
        if (appInfoBean==null)return;
        Log.d(TAG, "getAppinfoSuccess:123 "+appInfoBean.toString());

        SpUtil.putObject(getApplicationContext(),Constant.AppInfo,appInfoBean);
    }

    @Override
    public String setSNID() {
        return SNID;
    }
}
