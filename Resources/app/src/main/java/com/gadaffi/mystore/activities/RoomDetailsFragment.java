package com.gadaffi.mystore.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.Models.BookRoom;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.ServerRequest;
import com.gadaffi.mystore.ServerResponse;
import com.gadaffi.mystore.api.BookRoomInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomDetailsFragment extends Fragment implements View.OnClickListener {


    TextView roomno, nofspaces, price;
    AppCompatButton btn_book;

    ImageView image;
    SharedPreferences pref, preferences, pref1;
    Context context;
    ProgressBar progress;


    public RoomDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_details, container, false);


        initViews(view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getSharedPreferences("roomdata", MODE_PRIVATE);
        price.setText(pref.getString(Constants.PRICE, ""));
        nofspaces.setText(pref.getString(Constants.SPACES, ""));
        roomno.setText(pref.getString(Constants.ROOMNO, ""));


        if (pref.getString(Constants.IMAGE, "") != null) {
            Glide.with(this).load("http://192.168.43.34/mistore/upload_images/" + pref.getString(Constants.IMAGE, "")).into(image);
        } else {

            Glide.with(this).load(R.drawable.apart3).into(image);
        }


    }


    private void initViews(View view) {

        price = (TextView) view.findViewById(R.id.price);
        nofspaces = (TextView) view.findViewById(R.id.nofspaces);
        roomno = (TextView) view.findViewById(R.id.roomno);
        image = (ImageView) view.findViewById(R.id.thumbnail);
        progress = view.findViewById(R.id.progress);
        btn_book = view.findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btn_book:

                preferences = getActivity().getSharedPreferences("buildingdata", MODE_PRIVATE);

                pref1 = getActivity().getSharedPreferences("mistore", MODE_PRIVATE);
                String buildingid = preferences.getString(Constants.BUILDINGID, "");
                Log.d("buildingid", "Value: " + buildingid);
                String roomid = pref.getString(Constants.ROOMID, "");
                Log.d("roomid", "Value: " + roomid);
                String studentid = pref1.getString(Constants.UNIQUE_ID, "");
                Log.d("studentid", "Value: " + roomid);
                bookProcess(buildingid,studentid,roomid);
                break;

        }

    }

    private void bookProcess(String buildingid, String studentid,String roomid) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BookRoomInterface bookInterface = retrofit.create(BookRoomInterface.class);

        BookRoom bookRoom = new BookRoom();
      bookRoom.setBuildingid(buildingid);
      bookRoom.setRoomid(roomid);
      bookRoom.setStudentid(studentid);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.BOOK_OPERATION);
        request.setBookRoom(bookRoom);
        Call<ServerResponse> response = bookInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }


}
