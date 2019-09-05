package com.ciamiscode.syirkah.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.adapter.LogamAllAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.LogamModel;
import com.ciamiscode.syirkah.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuySellFragment extends Fragment {

    private LogamAllAdapter viewAdapter;
    private List<LogamModel> mItems = new ArrayList<>();
    private List<LogamModel> mLogam;
    RecyclerView recyclerView;
    //ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buysell,null);

        recyclerView = view.findViewById(R.id.recyclerAllLogam);
        //progress = view.findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        final SwipeRefreshLayout dorefresh = view.findViewById(R.id.swipeRefresh);
        dorefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        loadDataAllLogam();

        dorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }

            void refreshItem() {
                loadDataAllLogam();
                viewAdapter.notifyDataSetChanged();
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private void loadDataAllLogam() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getAllLogam();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                //progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_logam();
                    viewAdapter = new LogamAllAdapter(getContext(), mItems);
                    recyclerView.setAdapter(viewAdapter);
                    viewAdapter.notifyDataSetChanged();
                }else if(statusCode.equals("404")){
                    Toast.makeText(getContext(), "Oops, data penjualan masih kosong!! ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Oops, Tidak ada koneksi internet!! ", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
