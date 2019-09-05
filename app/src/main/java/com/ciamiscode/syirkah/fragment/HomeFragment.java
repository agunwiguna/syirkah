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
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.adapter.InvestasiAllAdapter;
import com.ciamiscode.syirkah.adapter.NewsAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestasiModel;
import com.ciamiscode.syirkah.model.NewsModel;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView tv_nama,tv_ucapan;

    SharedPrefManager sharedPrefManager;

    private NewsAdapter viewAdapter;
    private List<NewsModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);

        sharedPrefManager = new SharedPrefManager(getContext());

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        tv_nama = view.findViewById(R.id.nama_user);
        tv_nama.setText(sharedPrefManager.getSPNama());

        tv_ucapan = view.findViewById(R.id.tv_ucapan);

        if(timeOfDay >= 0 && timeOfDay < 12){
            tv_ucapan.setText("Pagi..");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            tv_ucapan.setText("Siang..");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            tv_ucapan.setText("Sore..");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            tv_ucapan.setText("Malam..");
        }


        recyclerView = view.findViewById(R.id.recyclerLimitInvestasi);
        progress = view.findViewById(R.id.progress_bar);

        viewAdapter = new NewsAdapter(getContext(),mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        loadDataNews();

        return view;
    }

    private void loadDataNews() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getAllNews();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                String message = response.body().getMessage();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_news();
                    viewAdapter = new NewsAdapter(getContext(), mItems);
                    recyclerView.setAdapter(viewAdapter);
                }else if(statusCode.equals("404")){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Oops, Tidak Ada Koneksi Internet!! ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
