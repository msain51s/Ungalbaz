package net;

public class LawersPojo {
    private String AboutUS;
    private String Email;
    private String LawerName;
    private String LawerType;
    private String Phone;
    private String Rating;
    private String id;
    private String thumb;

    public LawersPojo(String id, String LawerName, String LawerType, String Email, String Phone, String AboutUS, String Rating, String thumb) {
        this.id = id;
        this.LawerName = LawerName;
        this.LawerType = LawerType;
        this.Email = Email;
        this.Phone = Phone;
        this.AboutUS = AboutUS;
        this.Rating = Rating;
        this.thumb = thumb;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLawerName() {
        return this.LawerName;
    }

    public void setLawerName(String lawerName) {
        this.LawerName = lawerName;
    }

    public String getLawerType() {
        return this.LawerType;
    }

    public void setLawerType(String lawerType) {
        this.LawerType = lawerType;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhone() {
        return this.Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getAboutUS() {
        return this.AboutUS;
    }

    public void setAboutUS(String aboutUS) {
        this.AboutUS = aboutUS;
    }

    public String getRating() {
        return this.Rating;
    }

    public void setRating(String rating) {
        this.Rating = rating;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
