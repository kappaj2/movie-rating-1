package za.co.ajk.moviecatalogservice.model;

public class CatalogItem {

    private String name;
    private String descriptipon;
    private int rating;

    public CatalogItem() {
    }

    public CatalogItem(String name, String descriptipon, int rating) {
        this.name = name;
        this.descriptipon = descriptipon;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptipon() {
        return descriptipon;
    }

    public void setDescriptipon(String descriptipon) {
        this.descriptipon = descriptipon;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
