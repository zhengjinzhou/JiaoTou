package zhou.com.jiaotou.presenter;

import android.util.Log;

import okhttp3.OkHttpClient;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.jiaotou.activity.LoginActivity;
import zhou.com.jiaotou.api.VpnApi;
import zhou.com.jiaotou.base.RxPresenter;
import zhou.com.jiaotou.bean.LoginBean;
import zhou.com.jiaotou.contract.loginContract;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public class LoginPresenter extends RxPresenter<loginContract.View> implements loginContract.Presenter<loginContract.View>{

    VpnApi vpnApi;
    LoginActivity loginActivity;

    public LoginPresenter(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        this.vpnApi = new VpnApi(new OkHttpClient());
    }

    @Override
    public void login() {
        Log.d("", "vpnLogin: ");
        Subscription rxSubscription = vpnApi.vpnLogin("Login", "{UserID:'"+getUser()+"',UserPsw:'"+getPsd()+"'}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                        Log.d("", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        Log.d("", "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        Log.d("", "onNext: "+loginBean.toString());
                        mView.loginSuccess(loginBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void vpnLogin() {
        //cmd={UserID:'shb',UserPsw:'123456'}

    }

    @Override
    public String getUser() {
        return mView.setUser();
    }

    @Override
    public String getPsd() {
        return mView.setPsd();
    }
}
