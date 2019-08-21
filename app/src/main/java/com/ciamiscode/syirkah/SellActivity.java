package com.ciamiscode.syirkah;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.Button;

public class SellActivity extends AppCompatActivity {

    FloatingActionButton addSell;
    private AppCompatDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        addSell = findViewById(R.id.fab_sell);
        addSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogInput();
            }
        });
    }

    public void showDialogInput(){
        dialog = new AppCompatDialog(this);
        dialog.setContentView(R.layout.dialog_add_sell);
        dialog.setTitle("Jual Emas");

        Button buttonSave = dialog.findViewById(R.id.btn_simpan);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonCancel = dialog.findViewById(R.id.btn_batal);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!dialog.isShowing()){
            dialog.show();
        }
    }
}
