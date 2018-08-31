package vtc.room.a101.notificationapp.models;

public class NotificationModel {
    private String name;
    private String date;
    private String time;
    private int image;
    private int isTurned;

    public NotificationModel(String name, String date, String time, int image, int isTurned) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.image = image;
        this.isTurned = isTurned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int isTurned() {
        return isTurned;
    }

    public void setTurned(int turned) {
        isTurned = turned;
    }
}
