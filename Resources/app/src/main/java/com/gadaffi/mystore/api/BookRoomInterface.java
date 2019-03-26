package com.gadaffi.mystore.api;

import com.gadaffi.mystore.ServerRequest;
import com.gadaffi.mystore.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookRoomInterface {


        @POST("mistore/www/")
        Call<ServerResponse> operation(@Body ServerRequest request);


    }



