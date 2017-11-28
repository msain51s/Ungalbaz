package net;

public class UngalPojo {
    private String description;
    private String id;
    private String mainImage;
    private String mainImage2;
    private String shor_description;
    private String title;
    private String video;

    public UngalPojo(String id, String title, String shor_description, String description, String mainImage, String mainImage2, String video) {
        this.id = id;
        this.title = title;
        this.shor_description = shor_description;
        this.description = description;
        this.mainImage = mainImage;
        this.mainImage2 = mainImage2;
        this.video = video;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getShor_description() {
        return this.shor_description;
    }

    public void setShor_description(String shor_description) {
        this.shor_description = shor_description;
    }

    public String getMainImage() {
        return this.mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImage2() {
        return this.mainImage2;
    }

    public void setMainImage2(String mainImage2) {
        this.mainImage2 = mainImage2;
    }
}
