/**
 * Product class representing items in the product catalog
 */
public class Product {
    private String id;
    private String name;
    private double price;
    private DiscountType discountType;
    private double discountValue;
    
    // Enum for different types of discounts
    public enum DiscountType {
        NONE,
        BOGO,       // Buy One Get One
        PERCENTAGE  // Percentage off
    }
    
    // Constructor
    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountType = DiscountType.NONE;
        this.discountValue = 0.0;
    }
    
    // Constructor with discount information
    public Product(String id, String name, double price, DiscountType discountType, double discountValue) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public DiscountType getDiscountType() {
        return discountType;
    }
    
    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
    
    public double getDiscountValue() {
        return discountValue;
    }
    
    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discountType=" + discountType +
                ", discountValue=" + discountValue +
                '}';
    }
}