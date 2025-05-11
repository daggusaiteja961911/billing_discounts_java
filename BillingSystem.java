import java.util.HashMap;
import java.util.Map;

/**
 * BillingSystem class that manages the product catalog and shopping cart
 */
public class BillingSystem {
    private Map<String, Product> productCatalog;
    private Cart cart;
    
    // Constructor
    public BillingSystem() {
        this.productCatalog = new HashMap<>();
        this.cart = new Cart();
    }
    
    /**
     * Add a product to the catalog
     */
    public void addProductToCatalog(Product product) {
        if (product != null) {
            productCatalog.put(product.getId(), product);
        }
    }
    
    /**
     * Get a product from the catalog by ID
     */
    public Product getProduct(String productId) {
        return productCatalog.get(productId);
    }
    
    /**
     * Add a product to the cart
     */
    public void addToCart(String productId, int quantity) {
        Product product = getProduct(productId);
        if (product != null) {
            cart.addProduct(product, quantity);
        }
    }
    
    /**
     * Remove a product from the cart
     */
    public void removeFromCart(String productId, int quantity) {
        cart.removeProduct(productId, quantity);
    }
    
    /**
     * Undo the last cart action
     */
    public boolean undoLastCartAction() {
        return cart.undoLastAction();
    }
    
    /**
     * Generate a receipt for the current cart
     */
    public String generateReceipt(Cart.SortCriteria sortBy) {
        return cart.generateReceipt(sortBy);
    }
    
    /**
     * Generate a receipt with default sorting
     */
    public String generateReceipt() {
        return cart.generateReceipt();
    }
    
    /**
     * Get the shopping cart
     */
    public Cart getCart() {
        return cart;
    }
    
    /**
     * Clear the cart
     */
    public void clearCart() {
        cart.clear();
    }
    
    /**
     * Initialize the system with some sample products
     */
    public void initializeWithSampleProducts() {
        // Add some sample products to the catalog
        addProductToCatalog(new Product("P001", "Shampoo", 5.00));
        
        // Product with BOGO discount
        Product toothpaste = new Product("P002", "Toothpaste", 3.00);
        toothpaste.setDiscountType(Product.DiscountType.BOGO);
        addProductToCatalog(toothpaste);
        
        // Product with percentage discount
        Product faceCream = new Product("P003", "Face Cream", 10.00);
        faceCream.setDiscountType(Product.DiscountType.PERCENTAGE);
        faceCream.setDiscountValue(20.0);  // 20% off
        addProductToCatalog(faceCream);
        
        addProductToCatalog(new Product("P004", "Soap", 2.50));
        addProductToCatalog(new Product("P005", "Deodorant", 4.50));
    }
}