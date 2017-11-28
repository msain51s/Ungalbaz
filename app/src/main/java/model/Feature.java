package model;

public class Feature {
    public String Id;
    public String description;
    public int image;
    public String title;

    public Feature(String Id, String title, String description, int image) {
        this.Id = Id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
