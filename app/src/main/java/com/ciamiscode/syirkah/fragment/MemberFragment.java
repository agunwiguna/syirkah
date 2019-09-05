package com.ciamiscode.syirkah.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ciamiscode.syirkah.R;
import com.ciamiscode.syirkah.adapter.MemberAdapter;
import com.ciamiscode.syirkah.api.ApiEndPoint;
import com.ciamiscode.syirkah.api.ApiService;
import com.ciamiscode.syirkah.model.ResponseModel;
import com.ciamiscode.syirkah.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberFragment extends Fragment {

    private MemberAdapter viewAdapter;
    private List<UserModel> mItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progress;
    EditText edtCariMember;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member,null);

        recyclerView = view.findViewById(R.id.recyclerMember);
        progress = view.findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadDataMember();

        edtCariMember = view.findViewById(R.id.cari_member);
        edtCariMember.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        String isiSearch = edtCariMember.getText().toString();

                        if(isiSearch.matches("")){

                            Toast.makeText(getContext(), "Apa yang kamu cari?", Toast.LENGTH_SHORT).show();
                        }else{
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                            progress.setVisibility(View.VISIBLE);

                            loadSearchMember(isiSearch);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

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
                    viewAdapter.notifyDataSetChanged();
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

    public void loadSearchMember(String data){
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.getSearchMember(data);
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
                    viewAdapter.notifyDataSetChanged();
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
