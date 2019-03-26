package com.gadaffi.mystore.Models;

import com.google.gson.annotations.SerializedName;

public class Building {
    @SerializedName("id")
         String id;
    @SerializedName("name")
        String bname;
    @SerializedName("plotno")
        String plotno;
    @SerializedName("nofrooms")
        String nofrooms;
    @SerializedName("location")
        String location;
    @SerializedName("buildingimage")
        String bimage;

        public Building(String id, String bname, String plotno, String nofrooms, String location, String bimage)
        {
            this.id = id;
            this.bname =bname;
            this.plotno = plotno;
            this.nofrooms =nofrooms;
            this.location =location;
            this.bimage = bimage;
        }

        public String getId() {
            return id;
        }

        public String getBname() {
            return bname;
        }

        public String getPlotno() {
            return plotno;
        }

        public String getNofrooms() {
            return nofrooms;
        }

        public String getLocation() {
            return location;
        }

        public String getBimage() {
            return bimage;
        }
}
