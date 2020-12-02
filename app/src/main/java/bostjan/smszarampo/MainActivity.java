package bostjan.smszarampo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import android.support.annotation.NonNull;

public class MainActivity extends Activity {
    private static final int REQUEST_SMS = 2;
    private String phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //dvigni na GCC (pošlji SMS Heleni)
        phoneNo = "+38631770943";
        //dvigni na ŠCC (pošlji SMS Boštjanu - trenutno nastavljeno v Rampa1, da se dvigne rampa pri delavnici)
        //phoneNo = "+38641314375";
        sendSMSMessage(phoneNo, "gor");
    }

    protected void sendSMSMessage(String phoneNo, String message) {

        try {

            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                Toast.makeText(getApplicationContext(), "Dvigujem rampo ...\n\n(" + phoneNo + ": " + message + ")", Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS);
            }
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "NAPAKA: \n\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_SMS){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sendSMSMessage(phoneNo,"gor");
            }
            else {
                Toast.makeText(MainActivity.this,"Permission DENIED", Toast.LENGTH_LONG).show();
            }
        }
    }
}
