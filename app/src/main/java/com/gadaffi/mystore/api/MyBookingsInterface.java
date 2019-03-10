package com.gadaffi.mystore.api;
import java.util.List;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;




public interface MyBookingsInterface {
    @GET("mistore/wx/retrieve_booking_details.php")
    Call<List<Bookings>> getBookings(@Query("studentid") String studentid);
}
