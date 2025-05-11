# Billing System with Offers and Discounts Engine

A Java implementation of a checkout and billing system with support for various discount types, commonly used in retail and e-commerce applications.

## 📝 Description

This system manages a catalog of products, allows adding/removing items from a shopping cart, applies various discount rules, supports undo functionality, and generates detailed receipts. It demonstrates core data structure concepts like HashMaps, Stacks, and Sorting algorithms.

## 🎯 Features

### Product Catalog Management
- Store products with ID, name, price, and discount rules
- Fast lookup using HashMap data structure

### Shopping Cart Operations
- Add products to cart with specified quantities
- Remove products from cart
- Undo last cart operation using Stack data structure
- Clear entire cart

### Discount Engine
- **Item-Level Discounts**:
  - Buy-One-Get-One (BOGO)
  - Percentage discounts (e.g., 20% off)
- **Cart-Level Discounts**:
  - Automatic percentage off when cart total exceeds threshold

### Receipt Generation
- Detailed line items with product details and applied discounts
- Sort items by name, quantity, or price
- Cart subtotal, discounts, and final total calculation

## 🧠 Data Structures & Algorithms

- **HashMap**: For product catalog and cart item storage
- **Stack**: For implementing undo operations
- **Sorting**: Custom comparators for receipt item sorting
- **Searching**: Product lookup by ID

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Git (optional, for version control)

### Installation

1. Clone the repository (or download ZIP):
   ```
   git clone https://github.com/yourusername/billing-discounts-java.git
   ```

2. Navigate to the project directory:
   ```
   cd billing-discounts-java
   ```

### Running the Application

#### From Command Line:

1. Compile the Java files:
   ```
   javac *.java
   ```

2. Run the application:
   ```
   java Main
   ```

#### Using IntelliJ IDEA:

1. Open the project in IntelliJ IDEA
2. Right-click on `Main.java`
3. Select "Run 'Main.main()'"

## 📋 Usage

The application provides a simple command-line interface:

1. **Adding Products**: Select option 1, then enter product ID and quantity
2. **Removing Products**: Select option 2, then enter product ID and quantity
3. **Viewing Cart**: Select option 3 to see current cart contents
4. **Undo Last Action**: Select option 4 to revert the last cart operation
5. **Generate Receipt**: Select option 5 to create a formatted receipt (with sorting options)
6. **Clear Cart**: Select option 6 to remove all items from cart

### Sample Product Catalog

| ID   | Name       | Price | Discount     |
|------|------------|-------|-------------|
| P001 | Shampoo    | $5.00 | None        |
| P002 | Toothpaste | $3.00 | BOGO        |
| P003 | Face Cream | $10.00| 20% off     |
| P004 | Soap       | $2.50 | None        |
| P005 | Deodorant  | $4.50 | None        |

## 📂 Project Structure

- `Product.java`: Product entity with discount type information
- `CartItem.java`: Shopping cart item with quantity and discount calculations
- `Action.java`: Records cart operations for undo functionality
- `Cart.java`: Main shopping cart implementation with discount logic
- `BillingSystem.java`: Manages product catalog and cart operations
- `Main.java`: Command-line interface and application entry point

## 🔮 Future Enhancements

- Persistent storage for product catalog
- Customer loyalty program integration
- Time-limited promotional discounts
- Graphical user interface
- Export receipts to PDF or email

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgements

- Inspired by real-world retail discount systems
- Built to demonstrate Java data structures and algorithms