package com.ciamiscode.syirkah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ciamiscode.syirkah.utils.Preferences;

public class SettingActivity extends AppCompatActivity {

    TextView updateProfile,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        updateProfile = findViewById(R.id.tv_editProfile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this,UpdateProfileActivity.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.tv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear Set Preferences
                Preferences.setLogout(getBaseContext());

                //Pindah Halaman ke Login
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });

    }
}
