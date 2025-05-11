/**
 * CartItem class represents a product added to the shopping cart
 */
public class CartItem {
    private Product product;
    private int quantity;
    
    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and setters
    public Product getProduct() {
        return product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Calculate the subtotal for this cart item (before discounts)
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
    
    /**
     * Calculate the discount amount based on product discount rules
     */
    public double getDiscountAmount() {
        double discountAmount = 0.0;
        
        switch (product.getDiscountType()) {
            case NONE:
                // No discount applied
                discountAmount = 0.0;
                break;
                
            case BOGO:
                // Buy One Get One Free: For every 2 items, 1 is free
                discountAmount = product.getPrice() * (quantity / 2);
                break;
                
            case PERCENTAGE:
                // Percentage discount
                discountAmount = getSubtotal() * (product.getDiscountValue() / 100.0);
                break;
        }
        
        return discountAmount;
    }
    
    /**
     * Calculate the final price after applying discount
     */
    public double getFinalPrice() {
        return getSubtotal() - getDiscountAmount();
    }
    
    /**
     * Get the discount description for receipt display
     */
    public String getDiscountDescription() {
        switch (product.getDiscountType()) {
            case BOGO:
                return "BOGO";
            case PERCENTAGE:
                return product.getDiscountValue() + "%";
            default:
                return "None";
        }
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}