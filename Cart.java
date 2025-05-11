import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Cart class that manages the shopping cart operations
 */
public class Cart {
    private Map<String, CartItem> items;
    private Stack<Action> undoStack;
    private static final double CART_DISCOUNT_THRESHOLD = 100.0;
    private static final double CART_DISCOUNT_PERCENTAGE = 10.0;
    
    // Constructor
    public Cart() {
        this.items = new HashMap<>();
        this.undoStack = new Stack<>();
    }
    
    /**
     * Add a product to the cart
     */
    public void addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return;
        }
        
        String productId = product.getId();
        
        // Record action for undo
        undoStack.push(new Action(Action.ActionType.ADD, productId, quantity));
        
        // Check if product already exists in cart
        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(productId, new CartItem(product, quantity));
        }
    }
    
    /**
     * Remove a product from the cart
     */
    public void removeProduct(String productId, int quantity) {
        if (productId == null || quantity <= 0 || !items.containsKey(productId)) {
            return;
        }
        
        CartItem item = items.get(productId);
        
        // Record action for undo
        undoStack.push(new Action(Action.ActionType.REMOVE, productId, Math.min(quantity, item.getQuantity())));
        
        // Update quantity or remove item if quantity becomes zero
        if (item.getQuantity() <= quantity) {
            items.remove(productId);
        } else {
            item.setQuantity(item.getQuantity() - quantity);
        }
    }
    
    /**
     * Undo the last action performed on the cart
     */
    public boolean undoLastAction() {
        if (undoStack.isEmpty()) {
            return false;
        }
        
        Action lastAction = undoStack.pop();
        String productId = lastAction.getProductId();
        
        // We need to do the opposite of the last action
        if (lastAction.getActionType() == Action.ActionType.ADD) {
            // Undo an add - we need to remove
            if (items.containsKey(productId)) {
                CartItem item = items.get(productId);
                if (item.getQuantity() <= lastAction.getQuantity()) {
                    items.remove(productId);
                } else {
                    item.setQuantity(item.getQuantity() - lastAction.getQuantity());
                }
            }
        } else {
            // Undo a remove - we need to add back
            if (items.containsKey(productId)) {
                CartItem item = items.get(productId);
                item.setQuantity(item.getQuantity() + lastAction.getQuantity());
            } else {
                // If the product is no longer in the cart, we can't add it back because
                // we don't have the product information. In a real system, we would need to
                // keep track of the full product in the action or reference a product catalog.
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculate the total price of items in the cart before any discounts
     */
    public double getSubtotal() {
        double subtotal = 0.0;
        for (CartItem item : items.values()) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }
    
    /**
     * Calculate the total discount amount for all items in the cart
     */
    public double getItemDiscountsTotal() {
        double discountTotal = 0.0;
        for (CartItem item : items.values()) {
            discountTotal += item.getDiscountAmount();
        }
        return discountTotal;
    }
    
    /**
     * Calculate the cart-level discount (e.g., 10% off if total exceeds $100)
     */
    public double getCartDiscount() {
        double subtotalAfterItemDiscounts = getSubtotal() - getItemDiscountsTotal();
        
        // Apply cart-level discount if subtotal exceeds threshold
        if (subtotalAfterItemDiscounts >= CART_DISCOUNT_THRESHOLD) {
            return subtotalAfterItemDiscounts * (CART_DISCOUNT_PERCENTAGE / 100.0);
        }
        
        return 0.0;
    }
    
    /**
     * Calculate the final total after all discounts
     */
    public double getTotal() {
        return getSubtotal() - getItemDiscountsTotal() - getCartDiscount();
    }
    
    /**
     * Get all items in the cart
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }
    
    /**
     * Sort cart items based on the specified criteria
     */
    public List<CartItem> getSortedItems(SortCriteria criteria) {
        List<CartItem> sortedItems = getItems();
        
        switch (criteria) {
            case NAME:
                sortedItems.sort(Comparator.comparing(item -> item.getProduct().getName()));
                break;
                
            case QUANTITY:
                sortedItems.sort(Comparator.comparing(CartItem::getQuantity).reversed());
                break;
                
            case PRICE:
                sortedItems.sort(Comparator.comparing(CartItem::getFinalPrice).reversed());
                break;
        }
        
        return sortedItems;
    }
    
    /**
     * Generate a formatted receipt
     */
    public String generateReceipt(SortCriteria sortBy) {
        List<CartItem> sortedItems = getSortedItems(sortBy);
        StringBuilder receipt = new StringBuilder();
        
        // Header
        receipt.append("--- RECEIPT ---\n");
        receipt.append(String.format("%-12s %-5s %-12s %-10s %-12s\n", "Item", "Qty", "Unit Price", "Discount", "Subtotal"));
        receipt.append("----------------------------------------------------\n");
        
        // Line items
        for (CartItem item : sortedItems) {
            Product product = item.getProduct();
            receipt.append(String.format("%-12s %-5d $%-11.2f %-10s $%-11.2f\n",
                    truncate(product.getName(), 12),
                    item.getQuantity(),
                    product.getPrice(),
                    item.getDiscountDescription(),
                    item.getFinalPrice()));
        }
        
        // Summary
        receipt.append("\n");
        receipt.append(String.format("Subtotal: $%.2f\n", getSubtotal()));
        
        double cartDiscount = getCartDiscount();
        if (cartDiscount > 0) {
            receipt.append(String.format("Cart Discount: %.0f%% (-$%.2f)\n", CART_DISCOUNT_PERCENTAGE, cartDiscount));
        }
        
        receipt.append(String.format("TOTAL: $%.2f\n", getTotal()));
        receipt.append("----------------\n");
        
        return receipt.toString();
    }
    
    /**
     * Generate a receipt with default sorting (by name)
     */
    public String generateReceipt() {
        return generateReceipt(SortCriteria.NAME);
    }
    
    /**
     * Helper method to truncate long strings
     */
    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Enum for different sorting criteria
     */
    public enum SortCriteria {
        NAME,
        QUANTITY,
        PRICE
    }
    
    /**
     * Check if the cart is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Clear the cart
     */
    public void clear() {
        items.clear();
        undoStack.clear();
    }
}