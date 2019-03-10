package com.gadaffi.mystore.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.activities.ReverseActivity;
import com.gadaffi.mystore.api.Bookings;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MyBookingsAdapter extends RecyclerView.Adapter<MyBookingsAdapter.ViewHolder>
        implements View.OnClickListener {

    Context context;
    private List<Bookings> booking_list;
    SharedPreferences pref;

    public MyBookingsAdapter(Context context, List<Bookings> booking_list) {
        this.context = context;
        this.booking_list = booking_list;
    }

    public void setBookings_list(List<Bookings> booking_list) {
        this.booking_list = booking_list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybookingslist, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.buildingname.setText(booking_list.get(position).getBuildingname());
        holder.roomno.setText(booking_list.get(position).getRoomnumber());
//        holder.price.setText(booking_list.get(position).getPrice());
//        holder.date.setText(booking_list.get(position).getDatebooked());




        if(booking_list.get(position).getImage() != null && booking_list.get(position).getImage().length()>0)
        {
            Glide.with(context).load("http://192.168.43.34/mistore/upload_images/"+booking_list.get(position).getImage()).into(holder.image);
        }else {

            Glide.with(context).load(R.drawable.apart3).into(holder.image);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bookings thisBooking = booking_list.get(position);

                String[] booking = {
                       thisBooking.getBuildingname(),
                        thisBooking.getRoomnumber(),
                        thisBooking.getPrice(),
                        thisBooking.getDatebooked(),
                        thisBooking.getImage(),
                        thisBooking.getBuildingid(),
                        thisBooking.getRoomid()



                };

                openBookingDetail(booking);



            };


        });

    }
    public void openBookingDetail(String[] data){

        pref = context.getSharedPreferences("bookingdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.ROOMNAME,data[0]);
        editor.putString(Constants.ROOMNO,data[1]);
        editor.putString(Constants.PRICE,data[2]);
        editor.putString(Constants.DATE,data[3]);
        editor.putString(Constants.IMAGE,data[4]);
        editor.putString(Constants.BUILDINGID,data[5]);
        editor.putString(Constants.ROOMID,data[6]);
        editor.apply();
        Intent intent = new Intent(context,ReverseActivity.class);
        context.startActivity(intent);

    }


    @Override
    public int getItemCount() {
        if (booking_list != null) {
            return booking_list.size();
        }
        Toast.makeText(context, "No Bookings yet", Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public void onClick(View v) {



    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView buildingname, roomno, price, date;
        LinearLayout parentLayout;

        ImageView image;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            buildingname = itemView.findViewById(R.id.tv_building_name);
            roomno = itemView.findViewById(R.id.tv_room_no);
//            price = itemView.findViewById(R.id.tv_price);
//            date = itemView.findViewById(R.id.tv_date);
            image = itemView.findViewById(R.id.image);
            parentLayout = itemView.findViewById(R.id.parent_layout);




        }


    }




}
