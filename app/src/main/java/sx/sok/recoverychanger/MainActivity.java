package sx.sok.recoverychanger;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public ExecuteAsRootBase RootUser = new ExecuteAsRootBase();
    public final int RECTYPE_PHL_KK = 1;
    public final int RECTYPE_PHL_LL = 2;
    public final int RECTYPE_STC_KK = 3;
    public final int RECTYPE_STC_LL = 6;
    public final int RECTYPE_TWRP_KK = 4;
    public final int RECTYPE_TWRP_LL = 5;
    public void alertClose(String paramString1, String paramString2)
    {
        AlertDialog localAlertDialog = new AlertDialog.Builder(this).create();
        localAlertDialog.setTitle(paramString1);
        localAlertDialog.setMessage(paramString2);
        localAlertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                paramAnonymousDialogInterface.dismiss();
            }
        });
        localAlertDialog.setButton(-2, "Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                paramAnonymousDialogInterface.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        localAlertDialog.show();
    }
    public void showRecoveryMSG(){
        String paramString1 = "Recovery";
        String paramString2 = "Do you want reboot into recovery mode?";
        AlertDialog localAlertDialog = new AlertDialog.Builder(this).create();
        localAlertDialog.setTitle(paramString1);
        localAlertDialog.setMessage(paramString2);
        localAlertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                ArrayList localArrayList = new ArrayList();
                localArrayList.add("reboot recovery");
                localArrayList.add("busybox reboot recovery");
                localArrayList.add("toolbox reboot recovery");
                RootUser.execute(localArrayList);
                return;
            }
        });
        localAlertDialog.setButton(-2, "Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                paramAnonymousDialogInterface.dismiss();
            }
        });
        localAlertDialog.show();
    }


    public void disableButtons(){
        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setEnabled(false);
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setEnabled(false);
        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setEnabled(false);
        Button btn4 = (Button) findViewById(R.id.button4);
        btn4.setEnabled(false);
    }

    public String getRecoveryName(int paramInt)
    {
        String str = "";

        if (paramInt == 4 || paramInt == 5) {
            str = "TeamWinRecovery (TWRP)";
        }
        if (paramInt == 3 || paramInt == 6) {
            str = "Recovery Stock";
        }
        if (paramInt == 1 || paramInt == 2) {
            str = "PhilZ Touch Recovery";
        }
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView rootLabel = (TextView)findViewById(R.id.textView4);
        if (RootUser.canRunRootCommands() == true) {
            rootLabel.setTextColor(Color.parseColor("#aeea00"));
            rootLabel.append("rooted");
        }else{
            rootLabel.setTextColor(Color.parseColor("#ef5350"));
            rootLabel.append("no rooted");
            disableButtons();
            alertClose("No ROOT Acces", "Your device isn't rooted");
        }
    }
    public void buttonOnClickStock(View v)
    {
        ProgressDialog progress = new ProgressDialog(this);
        if (myVersion.equals("4.4.4") || myVersion.equals("4.4.2") || myVersion.equals("5.1.1")){
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_STC_KK));
            new copyRawToSD(progress, this).execute(RECTYPE_STC_KK);
        }
        else{
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_STC_LL));
            new copyRawToSD(progress, this).execute(RECTYPE_STC_LL);
        }
    }
    public void buttonOnClickTWRP(View v)
    {
        ProgressDialog progress = new ProgressDialog(this);
        if (myVersion.equals("4.4.4") || myVersion.equals("4.4.2") || myVersion.equals("5.1.1")){
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_TWRP_KK));
            new copyRawToSD(progress, this).execute(RECTYPE_TWRP_KK);
        }
        else{
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_TWRP_LL));
            new copyRawToSD(progress, this).execute(RECTYPE_TWRP_LL);
        }
    }
    public void buttonOnClickPhillz(View v)
    {
        ProgressDialog progress = new ProgressDialog(this);
        if (myVersion.equals("4.4.4") || myVersion.equals("4.4.2") || myVersion.equals("5.1.1")){
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_PHL_KK));
            new copyRawToSD(progress, this).execute(RECTYPE_PHL_KK);
        }
        else{
            progress.setMessage("Installing " + getRecoveryName(RECTYPE_PHL_LL));
            new copyRawToSD(progress, this).execute(RECTYPE_PHL_LL);
        }
    }

    public void buttonOnClickReboot(View v)
    {
        showRecoveryMSG();
    }

}
