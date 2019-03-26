package com.gadaffi.mystore.Models;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String rname;
@SerializedName("roomno")
 String roomno;
    @SerializedName("spaces")
    String spaces;
  @SerializedName("price")
String price;
    @SerializedName("img")
    String img;

    public Room( String id, String rname,String roomno,String spaces, String price,String img)
    {
        this.id = id;
        this.rname = rname;
        this.roomno = roomno;
        this.spaces =spaces;
         this.price =price;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public String getRname() {
        return rname;
    }

    public String getRoomno() {
        return roomno;
    }

    public String getSpaces() {
        return spaces;
    }

    public String getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }
}

