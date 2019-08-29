package com.ciamiscode.syirkah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.adapter.MemberAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberFragment extends Fragment {

    CircleImageView foto_ustadz;

    private MemberAdapter viewAdapter;
    private List<UserModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member,null);

//        foto_ustadz = view.findViewById(R.id.foto_ustadz);
//        foto_ustadz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogMemberFragment d = new DialogMemberFragment();
//                d.show(getFragmentManager(),"example");
//            }
//        });

        recyclerView = view.findViewById(R.id.recyclerMember);
        progress = view.findViewById(R.id.progress_bar);

        viewAdapter = new MemberAdapter(getContext(),mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        loadDataMember();

        return view;
    }

    private void loadDataMember() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getAllMember();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                String message = response.body().getMessage();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_member();
                    viewAdapter = new MemberAdapter(getContext(), mItems);
                    recyclerView.setAdapter(viewAdapter);
                }else if(statusCode.equals("404")){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
