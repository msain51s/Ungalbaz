package net;

public class SuggestionPojo {
    private String description;
    private String id;
    private String shor_description;
    private String title;
    private String video;
    private String video1;
    private String image1;

    public String getVideo1() {
        return video1;
    }

    public void setVideo1(String video1) {
        this.video1 = video1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    private String image2;

    public SuggestionPojo(String id, String title, String shor_description, String description, String video) {
        this.id = id;
        this.title = title;
        this.shor_description = shor_description;
        this.description = description;
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
}
