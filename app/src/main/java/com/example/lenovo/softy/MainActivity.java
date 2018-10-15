package com.example.lenovo.softy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},78);
        } else {
            SetTxt();
        }
    }

    private void SetTxt() throws SecurityException{
        TextView txt = findViewById(R.id.textView);
        TelephonyManager manager;
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        txt.setText(String.format("Version:\n%s\nIMEI:\n%s", BuildConfig.VERSION_NAME, manager.getDeviceId()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 78: {
                try {
                    SetTxt();
                }
                catch (SecurityException ignored){
                    TextView txt = findViewById(R.id.textView);
                    txt.setText("No permission");
                }
            }
        }
    }
}
