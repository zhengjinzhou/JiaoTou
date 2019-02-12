package zhou.com.jiaotou.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import zhou.com.jiaotou.R;
import zhou.com.jiaotou.base.Constant;
import zhou.com.jiaotou.bean.AppInfoBean;
import zhou.com.jiaotou.bean.NumBean;
import zhou.com.jiaotou.bean.SelectBean;
import zhou.com.jiaotou.bean.ShareBean;
import zhou.com.jiaotou.bean.openfileBean;
import zhou.com.jiaotou.contract.WebContract;
import zhou.com.jiaotou.presenter.WebPresenter;
import zhou.com.jiaotou.util.AppManager;
import zhou.com.jiaotou.util.DateUtil;
import zhou.com.jiaotou.util.DownloadUtil;
import zhou.com.jiaotou.util.JsonConvert;
import zhou.com.jiaotou.util.LoadDialog;
import zhou.com.jiaotou.util.Md5Util;
import zhou.com.jiaotou.util.SpUtil;
import zhou.com.jiaotou.util.StartService;
import zhou.com.jiaotou.util.ToastUtil;
import zhou.com.jiaotou.util.WpsUtil;

public class WebActivity extends AppCompatActivity {


    private static final String TAG = "WebActivity";
    private WebView mWebView;
    /**
     * 菜单弹出popup
     */
    static PopupWindow mPopupWindow;
    private String LastUrl = "";
    private String fileName;
    private String guid;
    private long downloadId;
    private int startX;
    private int scrollSize = 350;
    private LoadDialog loadDialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        loadDialog = new LoadDialog(this, true, "正在下载...");

        startService(new Intent(getApplicationContext(),StartService.class));

        mWebView = findViewById(R.id.webView);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);//加载JavaScript
        //设置可以支持缩放
        //设置支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        //不显示webview缩放按钮
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(new HuamboJsInterface(), "contact");
        mWebView.setWebViewClient(mWebViewClient);//这个一定要设置，要不然不会再本应用中加载
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.getSettings().setSupportZoom(true);


        /**
         * webView白屏问题
         */
        mWebView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        SelectBean selectBean = (SelectBean) SpUtil.getObject(this, Constant.Account, SelectBean.class);
        AppInfoBean appInfoBean = (AppInfoBean) SpUtil.getObject(this, Constant.AppInfo, AppInfoBean.class);

        /**
         * 设置别名
         */
        //JPushInterface.setAlias(getApplicationContext(),0,appInfoBean.getSNID()+"_"+selectBean.getUser());

        String baseUrl = appInfoBean.getDMSPhoneURL();

        //拼合成主页面地址
        String url = baseUrl+"Login/QuickLogin.aspx" +"?UserID=" + selectBean.getUser() + Constant.ACCOUNT_SysID+ "&From=APP";
        mWebView.loadUrl(url);
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        initUpdate();
    }

    /**
     * 更新app，
     * 只需要服务器的
     * 版本号大于本地的版本号即可更新
     * 现在服务器的是1，本地的是2
     * 本次所有都变成3
     */
    private void initUpdate() {

        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            Log.d(TAG, "initUpdate: "+versionCode);
            final AppInfoBean appInfoBean = (AppInfoBean) SpUtil.getObject(this, Constant.AppInfo, AppInfoBean.class);
            if (appInfoBean==null)return;
            if (versionCode < appInfoBean.getAppVersion()){
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("版本更新");
                builder.setMessage(appInfoBean.getUpdateMemo());
                builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(appInfoBean.getDownloadURL());
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                        browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(browserIntent);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果要实现文件下载的功能，需要设置WebView的DownloadListener，通过实现自己的DownloadListener来实现文件的下载
     */
    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Log.i("tag", "url=" + url);
            Log.i("tag", "userAgent=" + userAgent);
            Log.i("tag", "contentDisposition=" + contentDisposition);
            Log.i("tag", "mimetype=" + mimetype);
            Log.i("tag", "contentLength=" + contentLength);
            String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
            Log.d(TAG, "fileName:{123456}" + fileName);
            downloadBySystem(url, contentDisposition, mimetype);
        }
    }

    /**
     * 下载
     * @param url
     * @param contentDisposition
     * @param mimeType
     */
    @SuppressLint("NewApi")
    private void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedOverMetered(true);
        request.setVisibleInDownloadsUi(false);
        request.setAllowedOverRoaming(true);
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
        Log.d(TAG, "fileName:{}" + fileName);
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getPath().toString(), fileName);
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);
        Log.d(TAG, "downloadId:{} " + downloadId);

    }

    //ChromeClient
    WebChromeClient mWebChromeClient = new WebChromeClient() {
        //监听网页加载
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {

            result.confirm();
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.d(TAG, "onReceivedTitle: " + view);
            super.onReceivedTitle(view, title);
        }
    };

    //WebViewClient
    WebViewClient mWebViewClient = new WebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (view.getUrl().contains("cmd") && view.getTitle().length() > 16) {
                SpUtil.remove(getApplicationContext(), Constant.Account);
                ToastUtil.show(getApplicationContext(), "账号或密码有误");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
            LastUrl = url;

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * JS 交互
     */
    public final class HuamboJsInterface {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @JavascriptInterface
        public void clickAndroid(String json) {
            Log.d(TAG, "clickAndroid: " + json);
            String mType = JsonConvert.analysisJson(json, new TypeToken<String>() {
            }.getType(), "type");
            if (!TextUtils.isEmpty(mType)) {
                switch (mType) {
                    //分享
                    case "openfile":
                        Gson g = new Gson();
                        openfileBean openfileBean = g.fromJson(json, openfileBean.class);
                        if (WpsUtil.checkWps(getApplicationContext())) {
                            WpsUtil.openFile(getApplicationContext(), openfileBean.getPath());
                            return;
                        }else {
                            ToastUtil.show(getApplicationContext(),"请安装wps软件！");
                        }
                        break;
                    case "share":
                        Gson gson1 = new Gson();
                        ShareBean shareBean = gson1.fromJson(json, ShareBean.class);
                        downFile(shareBean.getPath(),shareBean.getName());
                        break;

                    case "phonebook":
                        //js不对，调不到通讯录,所以省略
                        // MailActivity.newInstance(WebActivity.this);
                        mPopupWindow.dismiss();
                        break;
                    case "logout":
                        //  SpUtil.clear();
                        SpUtil.remove(getApplicationContext(), Constant.Account);
                        SpUtil.remove(getApplicationContext(), Constant.VPN_AUTO);

                        Intent intent = new Intent(WebActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        WebActivity.this.startActivity(intent);
                        finish();
                        break;
                    case "quit":
                        finish();
                        break;

                    case "EditDocument":
                        Log.d(TAG, "打印: " + json);

                        break;
                    default:

                        break;
                }
            }
        }
    }

    private static void checkFileUriExposure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    public void downFile(String url, final String fileName) {

        DownloadUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), fileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDialog.dismiss();
                    }
                });

                Log.d("下载", "onDownloadSuccess: ");
                //下载完成进行相关逻辑操作
                checkFileUriExposure();

                String path = Environment.getExternalStorageDirectory().getPath().toString() + "/" + fileName;

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));  //传输图片或者文件 采用流的方式
                share.setType("*/*");   //分享文件
                startActivity(Intent.createChooser(share, fileName));

            }

            @Override
            public void onDownloading(int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDialog.show();
                    }
                });
                Log.d("下载", "onDownloading: "+progress);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                Log.d("下载", "onDownloadFailed: "+e.getMessage());
                //下载异常进行相关提示操作
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("-----------------------", "onResume: " + LastUrl);
        /** 注册下载完成接收广播 **/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mWebView) {
            mWebView.destroy();
        }
    }
}
