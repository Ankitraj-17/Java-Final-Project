import java.util.Scanner;

public class GroceryInventoryCalculator {

    // Maximum limit and parallel arrays to store product details
    private static final int MAX_PRODUCTS = 100;
    private static final String CURRENCY = "Rs. "; // Currency in Rupees
    private static String[] names = new String[MAX_PRODUCTS];
    private static double[] prices = new double[MAX_PRODUCTS];
    private static int[] quantities = new int[MAX_PRODUCTS];
    private static int[] minStock = new int[MAX_PRODUCTS];
    private static int productCount = 0;

    private static Scanner scanner = new Scanner(System.in);

    // Main method running the menu-driven loop
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   GROCERY INVENTORY CALCULATOR - STORE SYSTEM   ");
        System.out.println("==================================================");

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice (1-6): ");
            System.out.println();

            switch (choice) {
                case 1:
                    addProducts();
                    break;
                case 2:
                    displayTotalInventoryValue();
                    break;
                case 3:
                    showLowStockProducts();
                    break;
                case 4:
                    searchProduct();
                    break;
                case 5:
                    displayReport();
                    break;
                case 6:
                    System.out.println("Exiting Grocery Inventory Calculator. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("[!] Invalid option. Please select a valid number between 1 and 6.");
            }

            if (running) {
                System.out.println("\n--------------------------------------------------");
            }
        }

        // Close scanner on program termination
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Add Products");
        System.out.println("2. Calculate Inventory Value");
        System.out.println("3. Show Low-Stock Products");
        System.out.println("4. Search Product");
        System.out.println("5. Display Inventory Report");
        System.out.println("6. Exit");
    }

    // Check whether a product with the same name already exists (case-insensitive)
    public static boolean nameExists(String name) {
        for (int i = 0; i < productCount; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Module 1: Add products with input validation and capacity check
    public static void addProducts() {
        System.out.println("--- [Module 1: Product Entry] ---");

        if (productCount >= MAX_PRODUCTS) {
            System.out.println("[!] Inventory is full! Cannot add more products (Limit: " + MAX_PRODUCTS + ").");
            return;
        }

        int availableCapacity = MAX_PRODUCTS - productCount;
        System.out.println("Available slots in inventory: " + availableCapacity);

        int countToAdd;
        while (true) {
            countToAdd = readInt("How many products would you like to add? ");
            if (countToAdd <= 0) {
                System.out.println("[!] Please enter a positive number greater than 0.");
            } else if (countToAdd > availableCapacity) {
                System.out.println("[!] Cannot add " + countToAdd + " product(s). Only " + availableCapacity + " slot(s) remaining.");
            } else {
                break;
            }
        }

        for (int i = 0; i < countToAdd; i++) {
            System.out.println("\nEntering details for Product #" + (productCount + 1) + ":");

            String name;
            while (true) {
                name = readString("  Enter Product Name: ");
                if (name.isEmpty()) {
                    System.out.println("  [!] Product name cannot be blank. Please try again.");
                } else if (nameExists(name)) {
                    System.out.println("  [!] Product '" + name + "' already exists in inventory. Please enter a unique name.");
                } else {
                    break;
                }
            }

            double price;
            while (true) {
                price = readDouble("  Enter Unit Price (" + CURRENCY + "): ");
                // Demonstrates logical OR (||) operator for price validation
                if (price < 0.0 || Double.isNaN(price) || Double.isInfinite(price)) {
                    System.out.println("  [!] Price cannot be negative or invalid. Please re-enter.");
                } else {
                    break;
                }
            }

            int qty;
            while (true) {
                qty = readInt("  Enter Quantity in Stock: ");
                if (qty < 0) {
                    System.out.println("  [!] Quantity cannot be negative. Please re-enter.");
                } else {
                    break;
                }
            }

            int min;
            while (true) {
                min = readInt("  Enter Minimum Stock Threshold: ");
                // Demonstrates logical OR (||) operator for boundary check
                if (min < 0 || min > 1000000) {
                    System.out.println("  [!] Minimum stock must be between 0 and 1,000,000. Please re-enter.");
                } else {
                    break;
                }
            }

            // Demonstrates logical AND (&&): warn if the item is added already low on stock
            if (qty < min && qty > 0) {
                System.out.println("  [!] Note: this product is already below its minimum stock.");
            }

            // Store values in parallel arrays at current productCount index
            names[productCount] = name;
            prices[productCount] = price;
            quantities[productCount] = qty;
            minStock[productCount] = min;
            productCount++;

            System.out.println("  [OK] '" + name + "' added successfully!");
        }

        System.out.println("\n[OK] All " + countToAdd + " product(s) registered. Total products in store: " + productCount);
    }

    // Module 2: Calculate value for a single item (price * quantity)
    public static double calculateValue(double price, int qty) {
        return price * qty;
    }

    // Calculate sum of values for all active products
    public static double calculateTotalValue() {
        double total = 0.0;
        for (int i = 0; i < productCount; i++) {
            total = total + calculateValue(prices[i], quantities[i]);
        }
        return total;
    }

    // Display per-product breakdown and total inventory valuation
    public static void displayTotalInventoryValue() {
        System.out.println("--- [Module 2: Inventory Value Calculation] ---");
        if (productCount == 0) {
            System.out.println("[i] Inventory is currently empty. No products to calculate.");
            return;
        }

        System.out.println("Inventory Breakdown (per product):");
        for (int i = 0; i < productCount; i++) {
            double itemVal = calculateValue(prices[i], quantities[i]);
            System.out.printf("  %-20s : %s%.2f x %d = %s%.2f%n", names[i], CURRENCY, prices[i], quantities[i], CURRENCY, itemVal);
        }
        System.out.println("--------------------------------------------------");

        double totalVal = calculateTotalValue();
        System.out.println("Total Distinct Products : " + productCount);
        System.out.printf("Total Inventory Valuation: %s%.2f%n", CURRENCY, totalVal);
    }

    // Module 3: Check if quantity is below minimum stock threshold
    public static boolean isLowStock(int qty, int min) {
        return qty < min;
    }

    // Determine stock status using explicit if-else without ternary operator
    public static String getStatus(int qty, int min) {
        if (isLowStock(qty, min)) {
            return "RESTOCK";
        } else {
            return "OK";
        }
    }

    // Display all items requiring restocking
    public static void showLowStockProducts() {
        System.out.println("--- [Module 3: Stock Validation (Low-Stock Alert)] ---");
        if (productCount == 0) {
            System.out.println("[i] Inventory is currently empty. Please add products first.");
            return;
        }

        int lowStockCount = 0;

        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-14s | %-14s%n",
                "No.", "Product Name", "Current", "Min Stock", "Deficit Units", "Stock Level");
        System.out.println("-------------------------------------------------------------------------------------");

        for (int i = 0; i < productCount; i++) {
            if (isLowStock(quantities[i], minStock[i])) {
                lowStockCount++;
                int deficit = minStock[i] - quantities[i];

                String stockLevel;
                // Direct non-redundant check since we are already inside the isLowStock branch
                if (quantities[i] == 0) {
                    stockLevel = "OUT OF STOCK";
                } else {
                    stockLevel = "LOW";
                }

                System.out.printf("%-5d | %-20s | %-10d | %-10d | %-14d | %-14s%n",
                        lowStockCount, names[i], quantities[i], minStock[i], deficit, stockLevel);
            }
        }

        if (lowStockCount == 0) {
            System.out.println("All products have sufficient stock. None need restocking.");
        } else {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("[!] Alert: " + lowStockCount + " product(s) are below minimum threshold and require restocking.");
        }
    }

    // Module 4: Case-insensitive linear search by product name
    public static void searchProduct() {
        System.out.println("--- [Module 4: Product Search (Linear Search)] ---");
        if (productCount == 0) {
            System.out.println("[i] Inventory is empty. No products available to search.");
            return;
        }

        String searchName = readString("Enter the product name to search: ");
        boolean found = false;

        for (int i = 0; i < productCount; i++) {
            if (names[i].equalsIgnoreCase(searchName)) {
                double itemValue = calculateValue(prices[i], quantities[i]);
                String status = getStatus(quantities[i], minStock[i]);

                System.out.println("\n[OK] Product Found!");
                System.out.println("------------------------------------------");
                System.out.println("  Product Index : #" + (i + 1));
                System.out.println("  Product Name  : " + names[i]);
                System.out.printf("  Unit Price    : %s%.2f%n", CURRENCY, prices[i]);
                System.out.println("  Stock Quantity: " + quantities[i] + " units");
                System.out.println("  Min Threshold : " + minStock[i] + " units");
                System.out.printf("  Total Value   : %s%.2f%n", CURRENCY, itemValue);
                System.out.println("  Stock Status  : " + status);
                System.out.println("------------------------------------------");

                found = true;
                break;
            }
        }

        if (found == false) {
            System.out.println("[!] Product '" + searchName + "' not found in inventory.");
        }
    }

    // Module 5: Display complete inventory report in tabular format
    public static void displayReport() {
        System.out.println("--- [Module 5: Inventory Report] ---");
        if (productCount == 0) {
            System.out.println("[i] Inventory is currently empty. Nothing to display.");
            return;
        }

        System.out.println("========================================================================================");
        System.out.printf("%-4s | %-20s | %-10s | %-8s | %-9s | %-12s | %-10s%n",
                "No.", "Name", "Price (Rs)", "Quantity", "Min Stock", "Value (Rs)", "Status");
        System.out.println("========================================================================================");

        int totalUnits = 0;

        for (int i = 0; i < productCount; i++) {
            double itemValue = calculateValue(prices[i], quantities[i]);
            totalUnits += quantities[i];

            String status = getStatus(quantities[i], minStock[i]);

            System.out.printf("%-4d | %-20s | %10.2f | %8d | %9d | %12.2f | %-10s%n",
                    (i + 1), names[i], prices[i], quantities[i], minStock[i], itemValue, status);
        }

        double grandTotal = calculateTotalValue();

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("Total Distinct Products : %d%n", productCount);
        System.out.printf("Total Physical Units    : %d units%n", totalUnits);
        System.out.printf("Total Inventory Value   : %s%.2f%n", CURRENCY, grandTotal);
        System.out.println("========================================================================================");
    }

    // Helper: Read trimmed text line preventing Scanner newline skipping (handles EOF safely)
    public static String readString(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\n[!] Input ended. Exiting...");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    // Helper: Safely read integer with exception handling
    public static int readInt(String prompt) {
        while (true) {
            String input = readString(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input! Please enter a valid integer number.");
            }
        }
    }

    // Helper: Safely read double with exception handling
    public static double readDouble(String prompt) {
        while (true) {
            String input = readString(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input! Please enter a valid decimal number (e.g. 2.99).");
            }
        }
    }
}
