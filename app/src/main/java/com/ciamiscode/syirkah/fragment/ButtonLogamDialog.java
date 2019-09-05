package com.ciamiscode.syirkah.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.ciamiscode.syirkah.R;

public class ButtonLogamDialog extends BottomSheetDialogFragment {
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contexView = View.inflate(getContext(), R.layout.dialog_buy, null);
        dialog.setContentView(contexView);
    }
}
