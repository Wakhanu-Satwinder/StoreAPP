package com.gadaffi.mystore.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gadaffi.mystore.Constants;
import com.gadaffi.mystore.Models.Building;
import com.gadaffi.mystore.R;
import com.gadaffi.mystore.activities.BookActivity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;


public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    Context context;
    SharedPreferences pref;
    private List<Building> buildings_list;

    public BuildingAdapter(Context context, List<Building> buildings_list) {
        this.context = context;
        this.buildings_list = buildings_list;
    }

    public void setBuilding_list(List<Building> buildings_list) {
        this.buildings_list = buildings_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buildingslist, parent, false);


        return new BuildingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(buildings_list.get(position).getBname());
        holder.location.setText(buildings_list.get(position).getLocation());
        holder.nofrooms.setText(buildings_list.get(position).getNofrooms());
        holder.plotno.setText(buildings_list.get(position).getPlotno());
      //  holder.btn.set



        //Loading image from below url into imageView


        if(buildings_list.get(position).getBimage() != null && buildings_list.get(position).getBimage().length()>0)
        {
            Glide.with(context).load("http://192.168.43.34/mistore/upload_images/"+buildings_list.get(position).getBimage()).into(holder.image);
        }else {

            Glide.with(context).load(R.drawable.apart3).into(holder.image);
        }

     holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Building thisBuilding = buildings_list.get(position);
                Toast.makeText(context, thisBuilding.getBname(), Toast.LENGTH_SHORT).show();

                String[] building = {
                        thisBuilding.getId(),thisBuilding.getBname()


                };

                openDetailActivity(building);
            }
        });



    }
    public void openDetailActivity(String[] data){

        pref = context.getSharedPreferences("buildingdata", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.BUILDINGID,data[0]);
        Log.d("shared value", "Value: " + pref.getString(Constants.BUILDINGID,""));
        editor.apply();

//        Fragment fragment = new RoomsFragment();
//        FragmentTransaction ft =((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame, fragment);
//        ft.commit();

        Intent intent = new Intent(context, BookActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (buildings_list != null) {
            return buildings_list.size();
        }
        //Toast.makeText(context, "No Buildings Registered yet", Toast.LENGTH_SHORT).show();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView location,plotno,nofrooms;
        ImageView image;
        LinearLayout parentLayout;



        ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.bname);
            location = itemView.findViewById(R.id.location);
            plotno = itemView.findViewById(R.id.plotno);
            nofrooms = itemView.findViewById(R.id.nofrooms);
            image = itemView.findViewById(R.id.thumbnail);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }


    }

}
