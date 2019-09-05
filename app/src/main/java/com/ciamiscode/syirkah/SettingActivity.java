package com.ciamiscode.syirkah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.TextView;

import com.ciamiscode.syirkah.utils.Preferences;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

public class SettingActivity extends AppCompatActivity {

    TextView updateProfile,logout,ubahPassword, aboutApp;
    SharedPrefManager sharedPrefManager;
    private AppCompatDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPrefManager = new SharedPrefManager(this);

        updateProfile = findViewById(R.id.tv_editProfile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this,UpdateProfileActivity.class);
                startActivity(i);
            }
        });

        ubahPassword = findViewById(R.id.tv_ubahPassword);
        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,UbahPasswordActivity.class));
            }
        });

        aboutApp = findViewById(R.id.aboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AppCompatDialog(SettingActivity.this);
                dialog.setContentView(R.layout.dialog_tentang_app);
                dialog.show();
            }
        });

        logout = findViewById(R.id.tv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finishAffinity();

            }
        });

    }
}
