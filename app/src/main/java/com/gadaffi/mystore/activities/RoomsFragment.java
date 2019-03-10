package com.gadaffi.mystore.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.Models.Room;
import com.gadaffi.mystore.adapters.RoomAdapter;
import com.gadaffi.mystore.api.RoomInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

//import com.squareup.picasso.Picasso;


public class RoomsFragment extends Fragment {

    private RecyclerView recyclerView;

    //formelly bookAppointmentAdapter

    private RoomAdapter roomAdapter;
    private ProgressBar progress;
    SharedPreferences pref;


    //Updates is the model in my case i use Updates
    private List<Room> rooms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rooms = new ArrayList<>();
        //id of recyclerView in updates fragment
        recyclerView = view.findViewById(R.id.rooms_recycler);
        progress = view.findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter = new RoomAdapter(getContext(), rooms);
        recyclerView.setAdapter(roomAdapter);
        pref = this.getActivity().getSharedPreferences("buildingdata",MODE_PRIVATE);

        String buildingid = pref.getString(Constants.BUILDINGID,"");
        Log.d("shared value for room", "Value: " + buildingid);



        //

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoomInterface service = retrofit.create(RoomInterface.class);
//input value

        Call<List<Room>> call = service.getRoom(buildingid);
        //
        progress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                progress.setVisibility(View.INVISIBLE);
                rooms = response.body();
                roomAdapter.setRoom_list(rooms);

            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), "Connection Failed.Check Internet Connection", Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
