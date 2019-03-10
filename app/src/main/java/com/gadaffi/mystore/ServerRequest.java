package com.gadaffi.mystore;

import com.gadaffi.mystore.Models.BookRoom;
import com.gadaffi.mystore.Models.Building;
import com.gadaffi.mystore.Models.Room;
import com.gadaffi.mystore.Models.Student;

public class ServerRequest {

        String operation;
        Student student;
        Building building;
        Room room;
        BookRoom bookRoom;

        public void setOperation(String operation) {
            this.operation = operation;
        }
        public void setBookRoom(BookRoom bookRoom){
            this.bookRoom = bookRoom;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public void setBuilding(Building building) {this.building=building;
        }
        public void setRoom(Room room) {this.room=room;}
}
