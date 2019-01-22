package zhou.com.jiaotou.bean;

/**
 * Created by zhou
 * on 2018/12/12.
 */

public class AppInfoBean {

    /**
     * SNID : 0001
     * SortName : 万维
     * FullName : 广东万维博通信息技术有限公司
     * DMSPhoneURL : http://172.20.111.1/DMS_Phone/
     * ImgSplash :
     * ImgLogo :
     * ImgLogin :
     * ServiceURL : http://172.20.111.1/DMSPhoneAppService/
     * AppVersion : 1
     * DownloadURL : http://172.20.111.1/DMSPhoneAppService/Download/DMSPhone.apk
     * UpdateMemo : 暂无说明
     */

    private String SNID;
    private String SortName;
    private String FullName;
    private String DMSPhoneURL;
    private String ImgSplash;
    private String ImgLogo;
    private String ImgLogin;
    private String ServiceURL;
    private int AppVersion;
    private String DownloadURL;
    private String UpdateMemo;

    public String getSNID() {
        return SNID;
    }

    public void setSNID(String SNID) {
        this.SNID = SNID;
    }

    public String getSortName() {
        return SortName;
    }

    public void setSortName(String SortName) {
        this.SortName = SortName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getDMSPhoneURL() {
        return DMSPhoneURL;
    }

    public void setDMSPhoneURL(String DMSPhoneURL) {
        this.DMSPhoneURL = DMSPhoneURL;
    }

    public String getImgSplash() {
        return ImgSplash;
    }

    public void setImgSplash(String ImgSplash) {
        this.ImgSplash = ImgSplash;
    }

    public String getImgLogo() {
        return ImgLogo;
    }

    public void setImgLogo(String ImgLogo) {
        this.ImgLogo = ImgLogo;
    }

    public String getImgLogin() {
        return ImgLogin;
    }

    public void setImgLogin(String ImgLogin) {
        this.ImgLogin = ImgLogin;
    }

    public String getServiceURL() {
        return ServiceURL;
    }

    public void setServiceURL(String ServiceURL) {
        this.ServiceURL = ServiceURL;
    }

    public int getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(int AppVersion) {
        this.AppVersion = AppVersion;
    }

    public String getDownloadURL() {
        return DownloadURL;
    }

    public void setDownloadURL(String DownloadURL) {
        this.DownloadURL = DownloadURL;
    }

    public String getUpdateMemo() {
        return UpdateMemo;
    }

    public void setUpdateMemo(String UpdateMemo) {
        this.UpdateMemo = UpdateMemo;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "SNID='" + SNID + '\'' +
                ", SortName='" + SortName + '\'' +
                ", FullName='" + FullName + '\'' +
                ", DMSPhoneURL='" + DMSPhoneURL + '\'' +
                ", ImgSplash='" + ImgSplash + '\'' +
                ", ImgLogo='" + ImgLogo + '\'' +
                ", ImgLogin='" + ImgLogin + '\'' +
                ", ServiceURL='" + ServiceURL + '\'' +
                ", AppVersion=" + AppVersion +
                ", DownloadURL='" + DownloadURL + '\'' +
                ", UpdateMemo='" + UpdateMemo + '\'' +
                '}';
    }
}
