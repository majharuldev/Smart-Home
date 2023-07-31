package l2nsoft.com;

public class RoomResponse {

    String Details;
    String endtime;
    String image;
    String starttime;
    String title;

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RoomResponse(String details, String endtime, String image, String starttime, String title) {
        Details = details;
        this.endtime = endtime;
        this.image = image;
        this.starttime = starttime;
        this.title = title;
    }

    public RoomResponse() {
    }


}