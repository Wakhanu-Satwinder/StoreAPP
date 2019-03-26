package com.gadaffi.mystore.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gadaffi.mystore.Models.Building;
import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.adapters.BuildingAdapter;
import com.gadaffi.mystore.api.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {


    private RecyclerView recyclerView;



    private BuildingAdapter buildingAdapter;
    private ProgressBar progress;


    private List<Building> buildingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildingList = new ArrayList<>();
        //id of recyclerView in updates fragment
        recyclerView = view.findViewById(R.id.updates_recycler);
        progress = view.findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        buildingAdapter = new BuildingAdapter(getActivity(), buildingList);
        recyclerView.setAdapter(buildingAdapter);
        //

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);


        Call<List<Building>> call = service.getBuildings();
        //
        progress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                progress.setVisibility(View.INVISIBLE);
                buildingList = response.body();
             buildingAdapter.setBuilding_list(buildingList);

            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), "Connection Failed.Check Internet Connection", Snackbar.LENGTH_LONG).show();

            }
        });
    }
//protected void onBuldingslistClick(ListView 1,View v,int position,long id)

}
