package com.ciamiscode.syirkah;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ciamiscode.syirkah.adapter.InvestasiAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.InvestasiModel;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvestasiActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;

    private InvestasiAdapter viewAdapter;
    private List<InvestasiModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investasi);

        sharedPrefManager = new SharedPrefManager(this);

        fabAdd = findViewById(R.id.fab_investasi);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InvestasiActivity.this,AddProjectActivity.class);
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recyclerInvestasi);
        progress = findViewById(R.id.progress_bar);

        viewAdapter = new InvestasiAdapter(this,mItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        
        loadDataInvestasi();
    }

    private void loadDataInvestasi() {

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        String id_user = sharedPrefManager.getIdUser();

        Call<ResponseModel> call = api.getInvestasi(id_user);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String statusCode = response.body().getStatusCode();
                progress.setVisibility(View.GONE);
                if (statusCode.equals("200")) {
                    mItems = response.body().getResult();
                    viewAdapter = new InvestasiAdapter(InvestasiActivity.this, mItems);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(InvestasiActivity.this, "Oops, Tidak Ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
