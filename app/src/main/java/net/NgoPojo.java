package net;

public class NgoPojo {
    private String AboutUS;
    private String Address;
    private String Email;
    private String LawerAddress;
    private String LawerName;
    private String Phone;
    private String Rating;
    private String id;
    private String thumb;

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public NgoPojo(String id, String LawerName, String Address, String Email, String Phone, String AboutUS, String Rating, String thumb) {
        this.id = id;
        this.LawerName = LawerName;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.AboutUS = AboutUS;
        this.Rating = Rating;
        this.thumb = thumb;
    }

    public String getLawerAddress() {
        return this.LawerAddress;
    }

    public void setLawerAddress(String lawerAddress) {
        this.LawerAddress = lawerAddress;
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
