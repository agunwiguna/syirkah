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
import com.ciamiscode.syirkah.adapter.InvestasiAllAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestasiModel;
import com.ciamiscode.syirkah.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectFragment extends Fragment {

    private InvestasiAllAdapter viewAdapter;
    private List<InvestasiModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,null);

        recyclerView = view.findViewById(R.id.recyclerAllInvestasi);
        progress = view.findViewById(R.id.progress_bar);

        viewAdapter = new InvestasiAllAdapter(getContext(),mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        loadDataAllInvestasi();

        return view;
    }

    private void loadDataAllInvestasi() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getAllInvestasi();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_all_investasi();
                    viewAdapter = new InvestasiAllAdapter(getContext(), mItems);
                    recyclerView.setAdapter(viewAdapter);
                }else if(statusCode.equals("404")){
                    Toast.makeText(getContext(), "Oops, Data masih kosong!! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
