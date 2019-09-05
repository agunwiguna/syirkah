package com.ciamiscode.syirkah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ciamiscode.syirkah.adapter.LogamAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.LogamModel;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellActivity extends AppCompatActivity {

    FloatingActionButton addSell;

    private LogamAdapter viewAdapter;
    private List<LogamModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        sharedPrefManager = new SharedPrefManager(this);

        addSell = findViewById(R.id.fab_sell);
        addSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellActivity.this,AddLogamActivity.class));
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerLogamUser);
        progress = findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadDataLogamUser();
    }

    private void loadDataLogamUser() {

        String id_user = sharedPrefManager.getIdUser();

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getLogamUser(id_user);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult_logam_user();
                    viewAdapter = new LogamAdapter(SellActivity.this, mItems);
                    recyclerView.setAdapter(viewAdapter);
                    viewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(SellActivity.this, "Oops, Tidak Ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
