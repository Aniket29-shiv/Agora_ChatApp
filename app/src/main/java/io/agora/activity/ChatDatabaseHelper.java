package io.agora.activity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.Entity;

import java.io.Serializable;

@Entity
public class ChatDatabaseHelper implements Serializable {

        @PrimaryKey(autoGenerate = true)
        private int id;

        @ColumnInfo(name = "userId")
        private String userId;

        @ColumnInfo(name = "peerId")
        private String peerId;

        @ColumnInfo(name = "message")
        private String message;



        /*
         * Getters and Setters
         * */
        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getPeerId() {
                return peerId;
        }

        public void setPeerId(String peerId) {
                this.peerId = peerId;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }



//        public boolean isFinished() {
//                return finished;
//        }
//
//        public void setFinished(boolean finished) {
//                this.finished = finished;
//        }
}
