package se.gritacademy.server;

public class Ad {

    private Long id;
    private String subject;
    private String sellerName;
    private String sellerContact;
    private String description;
    private double price;

    public Ad() {
    }

    public Ad(Long id, String subject, String sellerName, String sellerContact, String description, double price) {
        this.id = id;
        this.subject = subject;
        this.sellerName = sellerName;
        this.sellerContact = sellerContact;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}