package com.gadaffi.mystore.api;

import com.gadaffi.mystore.Models.Building;
import com.gadaffi.mystore.Models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {


    @GET("mistore/wx/retrieve_building_details.php")
    Call<List<Building>> getBuildings();

    @GET("mistore/wx/retrieve_room_details.php")
    Call<List<Room>> getRooms();
}
