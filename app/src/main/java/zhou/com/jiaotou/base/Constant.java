package zhou.com.jiaotou.base;

/**
 * Created by zhou
 * on 2018/8/1.
 */

public class Constant {
    //http://19.104.9.73 东莞市委统战部智慧办公系统
    public static final String vpnAccount = "VPNACCOUNT";//vpn的账号密码
    public static final String Account = "Account";//账号密码 19.104.9.73   zgdgswtzb

   // public static final String BASE_URL = "http://221.4.134.50:8081/DMS_Phone/";
   public static final String BASE_URL = "http://wx.wanve.com/DMSPhoneAppService/";


    public static final String ACCOUNT_USER = "ACCOUNT_USER";
    public static final String ACCOUNT_PSD = "ACCOUNT_PSD";
    public static final String AppInfo = "AppInfo";
    public static final String REM = "REM";//记住密码
    public static final String VPN_AUTO = "VPN_AUTO";//自动登录
    public static final String ACCOUNT_SysID = "&SysID=K7q/DW5GAsVjobMbiMkbI8hKOQn3kb7S1GTM2KaKiCY=";
    public static final String VPN_USER = "VPN_USER";
    public static final String VPN_PSD = "VPN_PSD";
    public static final String URL_LOGIN = "Login/LoginHandler.ashx";
    public static final String MAIN_URL = BASE_URL + "Login/QuickLogin.aspx";
    public static final String JGTS_URL = "jgts_url";//保存极光推送过来的url

    //下载地址
    public static final String DOWNLOAD = BASE_URL;

    //上传地址
    public static final String UPLOAD_URL = DOWNLOAD+"WebServices/WebServiceForUpload.asmx";  //对应的url

}
