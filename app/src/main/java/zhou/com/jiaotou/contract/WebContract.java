package zhou.com.jiaotou.contract;


import zhou.com.jiaotou.base.BaseContract;
import zhou.com.jiaotou.bean.NumBean;

/**
 * Created by zhou
 * on 2018/12/4.
 */

public interface WebContract {
    interface View extends BaseContract.BaseView {

        void GetPhoneAppNumSuccess(NumBean numBean);

        String setUserId();
        String setSignature();
        String setTimestamp();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void GetPhoneAppNum();

        String getUserId();
        String getSignature();
        String getTimestamp();
    }
}
