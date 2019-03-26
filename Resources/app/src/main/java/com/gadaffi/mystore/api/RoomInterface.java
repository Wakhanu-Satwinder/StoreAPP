package com.gadaffi.mystore.api;

import com.gadaffi.mystore.Models.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RoomInterface {

    @GET("mistore/wx/retrieve_room_details.php")
    Call<List<Room>> getRoom(@Query("buildingid") String buildingid);
}
