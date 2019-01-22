package zhou.com.jiaotou.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhou
 * on 2018/7/26.
 */

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.zhou.service.destroy")){
            Intent newIntent = new Intent(context,StartService.class);
            context.startService(newIntent);
        }
    }
}
