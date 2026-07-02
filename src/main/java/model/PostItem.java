package model;

public class PostItem {
    private String id;
    private String title;
    private String summary;
    private String category;
    private String imageUrl;
    private String detailUrl;

    public PostItem() {
    }

    public PostItem(String id, String title, String summary, String category, String imageUrl, String detailUrl) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.category = category;
        this.imageUrl = imageUrl;
        this.detailUrl = detailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
