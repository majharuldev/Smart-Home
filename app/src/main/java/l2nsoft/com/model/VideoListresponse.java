package l2nsoft.com.model;

public class VideoListresponse {

    String Address;
    String Document;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }

    public VideoListresponse(String address, String document) {
        Address = address;
        Document = document;
    }

    public VideoListresponse() {
    }
}
