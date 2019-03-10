package com.gadaffi.mystore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadaffi.mystore.R;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bname,plotno,nofrooms,location;
        public ImageView bimage;
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            bname = (TextView) itemView.findViewById(R.id.bname);
            plotno = (TextView) itemView.findViewById(R.id.plotno);
            nofrooms = (TextView) itemView.findViewById(R.id.nofrooms);
            location = (TextView) itemView.findViewById(R.id.location);
            bimage=(ImageView)itemView.findViewById(R.id.thumbnail);
        }




        @Override
        public void onClick(View view) {
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }
    }

