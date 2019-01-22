package zhou.com.jiaotou.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by zhou
 * on 2018/12/25.
 */

public class WpsUtil {


    //检查是否已安装WPS
    public static boolean checkWps(Context context){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("cn.wps.moffice_eng");//WPS个人版的包名
        if (intent == null) {
            return false;
        } else {
            return true;
        }
    }

    //打开文档
    public static void openFile(Context context, String fileUrl){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage( "cn.wps.moffice_eng");
        Bundle bundle = new Bundle();
        intent.setData(Uri.parse(fileUrl));//这里采用传入文档的在线地址进行打开，免除下载的步骤，也不需要判断安卓版本号
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

}
