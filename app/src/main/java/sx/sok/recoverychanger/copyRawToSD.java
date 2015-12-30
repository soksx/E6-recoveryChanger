package sx.sok.recoverychanger;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by R9280 on 30/12/2015.
 */
public class copyRawToSD extends AsyncTask<Integer, Void, Void>{
    ProgressDialog progress;
    ExecuteAsRootBase RootUser = new ExecuteAsRootBase();
    MainActivity act;
    public copyRawToSD(ProgressDialog progress, MainActivity act) {
        this.progress = progress;
        this.act = act;
    }

    public String getRecoveryFile(int rectype){
        String str = "";
        switch (rectype){
            case 1:
                str = "philzkk.img";
                break;
            case 2:
                str = "philzll.img";
                break;
            case 3:
                str = "stockkk.img";
                break;
            case 4:
                str = "twrpkk.img";
                break;
            case 5:
                str = "twrpll.img";
                break;
            case 6:
                str = "stockll.img";
        }
        return str;
    }
    @Override
    protected void onPreExecute() {

        //set message of the dialog
        //show dialog
        progress.show();
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Integer... params) {
        try {
            Integer identifier = params[0];
            String str = "res/raw/";
            String file = str.concat(getRecoveryFile(identifier));
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
            FileOutputStream out = new FileOutputStream("/storage/emulated/0/tmprec.img");
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
            ArrayList localArrayList = new ArrayList();
            localArrayList.add("dd if=/storage/emulated/0/tmprec.img of=/dev/recovery");
            RootUser.execute(localArrayList); //Need root permissions
        }
        catch (IOException e){
            e.getMessage();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //Delete temprec.img
        File file = new File("/storage/emulated/0/tmprec.img");
        progress.dismiss();
        boolean deleted = file.delete();
        super.onPostExecute(result);
    }

}

