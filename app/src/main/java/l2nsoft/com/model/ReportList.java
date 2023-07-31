package l2nsoft.com.model;

public class ReportList {
    String date;
    String des;
    String room;
    String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ReportList(String date, String des, String room, String time) {
        this.date = date;
        this.des = des;
        this.room = room;
        this.time = time;
    }

    public ReportList() {
    }
}
