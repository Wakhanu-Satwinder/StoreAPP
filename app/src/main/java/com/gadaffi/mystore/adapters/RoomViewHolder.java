package com.gadaffi.mystore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadaffi.mystore.R;

    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bname,roomno,spaces,price;
        public ImageView bimage;
        public RoomViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            bname = (TextView) itemView.findViewById(R.id.bname);
            roomno = (TextView) itemView.findViewById(R.id.roomno);
           // spaces = (TextView) itemView.findViewById(R.id.spaces);
            price = (TextView) itemView.findViewById(R.id.price);
            bimage=(ImageView)itemView.findViewById(R.id.thumbnail);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }
    }

