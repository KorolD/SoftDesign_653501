package com.example.lenovo.softy;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_VERSION = 78;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_VERSION);
        } else {
            SetTxt();
        }
    }

    private void SetTxt() throws SecurityException {
        TextView txt = findViewById(R.id.textView);
        TelephonyManager manager;
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        txt.setText(getResources().getString(R.string.about_formatable, BuildConfig.VERSION_NAME, manager.getDeviceId()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_VERSION: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                        final MainActivity main = this;
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.request_permissions_msg_header)
                                .setMessage(R.string.request_permissions_msg)
                                .setNegativeButton(R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                ActivityCompat.requestPermissions(main, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_VERSION);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.setCancelable(false);
                        alert.show();
                    } else {
                        TextView txt = findViewById(R.id.textView);
                        txt.setText(R.string.no_permission);
                    }
                } else {
                    SetTxt();
                }
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
