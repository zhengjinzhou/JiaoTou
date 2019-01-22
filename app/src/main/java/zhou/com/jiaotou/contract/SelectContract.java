package zhou.com.jiaotou.contract;

import java.util.List;

import zhou.com.jiaotou.base.BaseContract;
import zhou.com.jiaotou.bean.AppInfoBean;
import zhou.com.jiaotou.bean.SNIDList;


/**
 * Created by zhou
 * on 2018/12/12.
 */

public interface SelectContract {
    interface View extends BaseContract.BaseView {
        void getSnidListSuccess(List<SNIDList> snidList);
        String setKeywork();
        void getAppinfoSuccess(AppInfoBean appInfoBean);
        String setSNID();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getSnidList();
        String getKeywork();
        void getAppInfo();
        String getSNID();
    }
}
