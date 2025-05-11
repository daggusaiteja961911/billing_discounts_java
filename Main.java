import java.util.Scanner;

/**
 * Main class with a command-line interface for testing the billing system
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BillingSystem billingSystem = new BillingSystem();
    
    public static void main(String[] args) {
        System.out.println("Billing System with Discounts Engine\n");
        
        // Initialize the system with sample products
        billingSystem.initializeWithSampleProducts();
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1: // Add product to cart
                    addProductToCart();
                    break;
                    
                case 2: // Remove product from cart
                    removeProductFromCart();
                    break;
                    
                case 3: // View cart
                    viewCart();
                    break;
                    
                case 4: // Undo last action
                    undoLastAction();
                    break;
                    
                case 5: // Generate receipt
                    generateReceipt();
                    break;
                    
                case 6: // Clear cart
                    billingSystem.clearCart();
                    System.out.println("Cart cleared.");
                    break;
                    
                case 0: // Exit
                    running = false;
                    System.out.println("Thank you for using the Billing System!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); // Add a blank line for readability
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("===== Menu =====");
        System.out.println("1. Add product to cart");
        System.out.println("2. Remove product from cart");
        System.out.println("3. View cart");
        System.out.println("4. Undo last action");
        System.out.println("5. Generate receipt");
        System.out.println("6. Clear cart");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }
    
    private static void addProductToCart() {
        System.out.println("\nAvailable Products:");
        System.out.println("ID   | Name       | Price | Discount");
        System.out.println("----------------------------------");
        System.out.println("P001 | Shampoo    | $5.00 | None");
        System.out.println("P002 | Toothpaste | $3.00 | BOGO");
        System.out.println("P003 | Face Cream | $10.00| 20%");
        System.out.println("P004 | Soap       | $2.50 | None");
        System.out.println("P005 | Deodorant  | $4.50 | None");
        
        System.out.print("\nEnter product ID: ");
        String productId = scanner.nextLine();
        
        Product product = billingSystem.getProduct(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        
        System.out.print("Enter quantity: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }
            
            billingSystem.addToCart(productId, quantity);
            System.out.println(product.getName() + " added to cart.");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
        }
    }
    
    private static void removeProductFromCart() {
        if (billingSystem.getCart().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        
        viewCart();
        
        System.out.print("\nEnter product ID to remove: ");
        String productId = scanner.nextLine();
        
        System.out.print("Enter quantity to remove: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }
            
            billingSystem.removeFromCart(productId, quantity);
            System.out.println("Product removed from cart.");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
        }
    }
    
    private static void viewCart() {
        if (billingSystem.getCart().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        
        System.out.println(billingSystem.generateReceipt());
    }
    
    private static void undoLastAction() {
        boolean success = billingSystem.undoLastCartAction();
        if (success) {
            System.out.println("Last action undone successfully.");
        } else {
            System.out.println("Nothing to undo or undo failed.");
        }
    }
    
    private static void generateReceipt() {
        if (billingSystem.getCart().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        
        System.out.println("\nSort receipt by:");
        System.out.println("1. Product Name");
        System.out.println("2. Quantity");
        System.out.println("3. Price");
        System.out.print("Enter your choice (default is Name): ");
        
        String input = scanner.nextLine().trim();
        Cart.SortCriteria sortBy;
        
        if (input.isEmpty() || input.equals("1")) {
            sortBy = Cart.SortCriteria.NAME;
        } else if (input.equals("2")) {
            sortBy = Cart.SortCriteria.QUANTITY;
        } else if (input.equals("3")) {
            sortBy = Cart.SortCriteria.PRICE;
        } else {
            sortBy = Cart.SortCriteria.NAME;
            System.out.println("Invalid choice, using default sorting (Name).");
        }
        
        System.out.println(billingSystem.generateReceipt(sortBy));
    }
}