package com.gadaffi.mystore;

import com.gadaffi.mystore.Models.Building;
import com.gadaffi.mystore.Models.Room;
import com.gadaffi.mystore.Models.Student;

public class ServerResponse {

        String result;
        String message;
        Student student;
        Building building;
        Room room;

        public String getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public Student getStudent() {
            return student;
        }

        public Building getBuilding(){return building;}

        public Room getRoom(){return room;}
}
