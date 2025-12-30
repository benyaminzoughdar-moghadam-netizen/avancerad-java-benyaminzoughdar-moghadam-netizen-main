//commit 6
package se.gritacademy.server;

public class Ad {
    private long id;
    private String title;
    private String seller;
    private String description;
    private double price;
    private String pinCode;

    public Ad() {}

    public Ad(long id, String title, String seller, String description, double price, String pinCode) {
        this.id = id;
        this.title = title;
        this.seller = seller;
        this.description = description;
        this.price = price;
        this.pinCode = pinCode;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getSeller() { return seller; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getPinCode() { return pinCode; }

    public void setPrice(double price) { this.price = price; }
}
