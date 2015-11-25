package org.team10424102.whisky.components;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;

import org.team10424102.whisky.R;

import static org.team10424102.whisky.App.ERR_INIT_RETROFIT;
import static org.team10424102.whisky.App.ERR_INVOKING_API;
import static org.team10424102.whisky.App.ERR_NETWORK;
import static org.team10424102.whisky.App.ERR_RENEW_TOKEN;
import static org.team10424102.whisky.App.ERR_SERVER_MAINTENANCE;

/**
 * Created by yy on 11/8/15.
 */
public class ErrorManager {

    public void handleError(Context context, int err) {
        switch (err) {
            case ERR_RENEW_TOKEN:
            case ERR_NETWORK:
                alertErrorDialogAndQuit(context, R.string.err_network);
                break;
            case ERR_SERVER_MAINTENANCE:
                alertErrorDialogAndQuit(context, R.string.err_server_maintenance);
                break;
            case ERR_INIT_RETROFIT:
                alertErrorDialogAndQuit(context, R.string.err_init_retrofit);
                break;
            case ERR_INVOKING_API:
                alertErrorDialog(context, R.string.err_invoking_api);
        }
    }

    private void alertErrorDialog(final Context context, @StringRes int resId) {
        String msg = context.getResources().getString(resId);
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle).create();
        String title = context.getResources().getString(R.string.alert_dialog_title);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }

    private void alertErrorDialogAndQuit(final Context context, @StringRes int resId) {
        String msg = context.getResources().getString(resId);
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle).create();
        String title = context.getResources().getString(R.string.alert_dialog_title);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        jumpToHomeScreen(context);
                    }
                });
        alert.show();
    }

    private void jumpToHomeScreen(Context context) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
    }

}
