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
import com.gadaffi.mystore.adapters.MyBookingsAdapter;
import com.gadaffi.mystore.api.Bookings;
import com.gadaffi.mystore.api.MyBookingsInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class MyBookingsFragment extends Fragment {

    private RecyclerView recyclerView;

    //formelly bookAppointmentAdapter

    private MyBookingsAdapter bookingsAdapter;
    private ProgressBar progress;
    SharedPreferences pref;

    //Updates is the model in my case i use Updates
    private List<Bookings> bookings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookings = new ArrayList<>();
        //id of recyclerView in updates fragment
        recyclerView = view.findViewById(R.id.bookings_recycler);
        progress = view.findViewById(R.id.progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingsAdapter = new MyBookingsAdapter(getContext(), bookings);
        recyclerView.setAdapter(bookingsAdapter);
        pref = getContext().getSharedPreferences("mistore",MODE_PRIVATE);
        String studentid = pref.getString(Constants.UNIQUE_ID,"");

        //

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyBookingsInterface service = retrofit.create(MyBookingsInterface.class);
//input value

        Call<List<Bookings>> call = service.getBookings(studentid);
        //
        progress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<Bookings>>() {
            @Override
            public void onResponse(Call<List<Bookings>> call, Response<List<Bookings>> response) {
                progress.setVisibility(View.INVISIBLE);
               bookings = response.body();
               bookingsAdapter.setBookings_list(bookings);

            }

            @Override
            public void onFailure(Call<List<Bookings>> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), "Connection Failed.Check Internet Connection", Snackbar.LENGTH_LONG).show();

            }
        });
    }
}

