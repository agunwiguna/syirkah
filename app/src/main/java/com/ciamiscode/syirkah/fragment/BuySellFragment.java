package com.ciamiscode.syirkah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciamiscode.syirkah.R;

public class BuySellFragment extends Fragment {

    CardView cv_buy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buysell,null);

        cv_buy = view.findViewById(R.id.cv_buy);
        cv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogBuyFragment db = new DialogBuyFragment();
                db.show(getFragmentManager(),"example");
            }
        });

        return view;
    }
}
