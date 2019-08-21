package com.ciamiscode.syirkah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciamiscode.syirkah.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberFragment extends Fragment {

    CircleImageView foto_ustadz;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member,null);

        foto_ustadz = view.findViewById(R.id.foto_ustadz);
        foto_ustadz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMemberFragment d = new DialogMemberFragment();
                d.show(getFragmentManager(),"example");
            }
        });

        return view;
    }
}
