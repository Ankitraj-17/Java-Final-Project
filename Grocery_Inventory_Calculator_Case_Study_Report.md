# ACADEMIC PROJECT REPORT

## CASE STUDY 161: GROCERY INVENTORY CALCULATOR

* **Course**: Java Programming (B.Tech CSE, Semester III)
* **Institution**: School of Future Tech, ITM Skills University
* **Student Name**: Ankit Raj Jha
* **Subject**: Java Programming Laboratory & Case Study
* **Academic Year**: 2025–2026

---

## 1. INTRODUCTION & PROBLEM STATEMENT

### 1.1 Introduction
In retail operations, managing grocery stock is a daily mission-critical task. Small to medium-sized grocery enterprises often struggle with manual tracking of rapidly moving goods, resulting in stock discrepancies, expired shelf life, sudden stock-outs, and unaccounted capital tied up in slow-moving inventory. Automating inventory valuation and threshold monitoring ensures timely replenishment, financial clarity, and operational efficiency.

### 1.2 Problem Statement
A grocery store requires a lightweight, dependable computer application to:
1. Systematically capture product entries (name, unit price, stock quantity, and minimum required threshold).
2. Compute accurate financial inventory values for individual items and cumulative stock.
3. Automatically flag items falling below safety stock levels to prompt restocking.
4. Allow instant keyword searching for stock availability.
5. Produce a well-formatted summary inventory report.

### 1.3 Scope & Curriculum Alignment
Designed strictly around Semester III foundational Java core competencies, this project demonstrates mastery over:
* **Primitive Data Structures**: Multi-attribute record management using synchronized parallel 1D arrays without external collection frameworks.
* **Control Structures & Iteration**: Deterministic `for` loops and sentinel-controlled `do-while` loops driving interactive execution.
* **Conditional Branching & Logic**: Multi-way `if-else` decision trees with compound relational and logical operators (`&&`, `||`, `<`, `<=`).
* **Algorithms**: Unsorted Linear Search algorithm with early-exit optimization and case-insensitive string matching.
* **Defensive Programming**: Robust scanner input handling eliminating buffer overflows and `InputMismatchException` crashes.

---

## 2. SYSTEM REQUIREMENTS & SPECIFICATIONS

### 2.1 Hardware Requirements
* **Processor**: Dual-core x86/x64 or ARM-based processor (Intel Core i3+, AMD Ryzen 3+, or Apple Silicon M-series)
* **RAM**: 4 GB minimum (8 GB recommended)
* **Storage Space**: 150 MB available hard drive space
* **I/O Peripherals**: Standard keyboard, display monitor (minimum 1366x768 resolution), and console terminal

### 2.2 Software Environment
* **Operating System**: macOS 12+, Windows 10/11 (64-bit), or Ubuntu Linux 20.04+
* **Java Platform**: Java Development Kit (JDK 17 LTS / JDK 21 LTS / OpenJDK)
* **Compiler & VM**: `javac` compiler and Java Virtual Machine (`java`)
* **Development Environment**: Visual Studio Code with Java Extension Pack

---

## 3. CORE MODULE ARCHITECTURE & CODE SNIPPETS

### 3.1 Module 1: Data Storage Architecture (Parallel Arrays)
The application avoids dynamic heap-overhead collections (`ArrayList`) by utilizing four synchronized, fixed-size 1D arrays governed by a common capacity boundary `MAX_PRODUCTS = 100` and tracking pointer `productCount`.

```java
// Snippet: Synchronized Parallel Arrays Definition
static final int MAX_PRODUCTS = 100;
static int productCount = 0;

static String[] names = new String[MAX_PRODUCTS];
static double[] prices = new double[MAX_PRODUCTS];
static int[] quantities = new int[MAX_PRODUCTS];
static int[] minStock = new int[MAX_PRODUCTS];
```

---

### 3.2 Module 2: Product Entry & Validation
Accepts item details from the store manager. It performs real-time duplicate validation against existing stock records, strictly rejects zero or negative prices, and issues an immediate restocking advisory if initial stock is below minimum safe threshold.

```java
// Snippet: Record insertion with validation & duplicate check
if (nameExists(name)) {
    System.out.println("  Error: Product '" + name + "' already exists in inventory.");
}

names[productCount] = name;
prices[productCount] = price;
quantities[productCount] = qty;
minStock[productCount] = min;
productCount++;

if (qty < min) {
    System.out.println("  [!] Note: Quantity is below the minimum required stock (" + min + "). Restock needed.");
}
```

---

### 3.3 Module 3: Inventory Valuation Engine
Computes itemized financial asset value by multiplying unit price by units on hand (`price * quantity`), tabulating individual items, and accumulating the store's grand inventory valuation in Indian Rupees (Rs.).

```java
// Snippet: Valuation method and grand total accumulator
public static double calculateValue(double price, int quantity) {
    return price * quantity;
}

double grandTotal = 0.0;
for (int i = 0; i < productCount; i++) {
    double itemTotal = calculateValue(prices[i], quantities[i]);
    grandTotal += itemTotal;
}
```

---

### 3.4 Module 4: Stock Validation & Restock Deficit Analyzer
Scans active inventory to isolate under-stocked items. It calculates the exact replenishment deficit (`minStock - currentQuantity`) and categorizes each item into distinct operational states: `OUT OF STOCK`, `LOW`, or `ADEQUATE`.

```java
// Snippet: Restock logic & multi-way status classification
public static boolean isLowStock(int quantity, int minimum) {
    return quantity < minimum;
}

public static String getStatus(int quantity, int minimum) {
    if (quantity == 0) {
        return "OUT OF STOCK";
    } else if (quantity < minimum) {
        return "LOW";
    } else {
        return "ADEQUATE";
    }
}
```

---

### 3.5 Module 5: Search & Retrieval Engine
Executes a linear search traversal across the `names[]` array. It performs case-insensitive comparisons using `equalsIgnoreCase()`, terminating immediately upon encountering the match ($O(1)$ best-case, $O(n)$ worst-case).

```java
// Snippet: Optimized linear search traversal
int foundIndex = -1;
for (int i = 0; i < productCount; i++) {
    if (names[i].equalsIgnoreCase(query)) {
        foundIndex = i;
        break; // Early exit on match
    }
}
```

---

### 3.6 Defensive I/O: Buffer-Safe Scanner Parsing
Eliminates common Java `Scanner` pitfalls where `nextInt()` leaves dangling newline characters in the buffer. Uses `scanner.nextLine()` with safe parsing wrapped in structured exception handling.

```java
// Snippet: Exception-safe integer and double readers
static int readInt(String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  Error: Invalid number format. Please enter an integer.");
        }
    }
}
```

---

## 4. CONSOLE OUTPUTS & SCREENSHOT PLACEMENTS

> **Submission Note**: Paste your terminal execution screenshots into the designated placeholder boxes below before finalizing the document.

### 4.1 Main Menu Interface
Displays the formatted console header and user navigation menu containing options 1 through 6.

```
===================================================================================
[ PASTE SCREENSHOT 1 HERE: CONSOLE - MAIN MENU INTERFACE ]
(Terminal running: java GroceryInventoryCalculator -> Displays menu options 1 to 6)
===================================================================================
```

---

### 4.2 Module 1: Product Entry & Input Validation
Demonstrates interactive addition of grocery items (e.g., Basmati Rice, Amul Milk, Sugar, Dish Soap), exhibiting rejection of invalid prices, rejection of duplicate product names, and immediate restock notification notices.

```
===================================================================================
[ PASTE SCREENSHOT 2 HERE: CONSOLE - PRODUCT ENTRY & VALIDATION ]
(Shows entering item details, duplicate rejection message, and restock warning note)
===================================================================================
```

---

### 4.3 Module 2: Inventory Calculation Output
Shows the generated valuation ledger: individual item calculations (`Unit Price * Quantity = Total Value`) and the computed Grand Total in Rs.

```
===================================================================================
[ PASTE SCREENSHOT 3 HERE: CONSOLE - INVENTORY VALUE CALCULATION ]
(Shows formatted columns: No., Product Name, Price, Quantity, Total Value, and Grand Total)
===================================================================================
```

---

### 4.4 Module 3: Stock Validation & Low-Stock Alerts
Shows the automated filtering of all inventory items falling below safety thresholds, highlighting exact missing units (Deficit) and their specific status flags.

```
===================================================================================
[ PASTE SCREENSHOT 4 HERE: CONSOLE - STOCK VALIDATION & DEFICIT ALERTS ]
(Shows filtered table of deficient items with OUT OF STOCK / LOW status tags)
===================================================================================
```

---

### 4.5 Module 4: Product Search Operation
Demonstrates testing the lookup engine:
1. **Query Hit**: Searching for an existing item (e.g., "milk" or "RICE") displaying complete item dossier.
2. **Query Miss**: Searching for an unavailable item displaying "No product found".

```
===================================================================================
[ PASTE SCREENSHOT 5 HERE: CONSOLE - PRODUCT SEARCH (HIT & MISS) ]
(Shows search query input, product details card on match, and graceful miss message)
===================================================================================
```

---

### 4.6 Module 5: Comprehensive Inventory Status Report
Shows the complete store inventory matrix along with summary metrics (Total Distinct Items, Total Valuation, Adequate Stock Items, and Low/Out-of-Stock count).

```
===================================================================================
[ PASTE SCREENSHOT 6 HERE: CONSOLE - COMPREHENSIVE INVENTORY REPORT ]
(Shows master inventory table and final SUMMARY STATISTICS card)
===================================================================================
```

---

## 5. EXTENSION SECTION: GRAPHICAL USER INTERFACE (SWING)

> *Note: Although Case Study 161 strictly dictates a console-based array application, a companion desktop GUI (`GroceryInventoryGUI.java`) backed by a headless business logic model (`InventoryManager.java`) was engineered as an advanced academic extension to illustrate Java Swing event-driven architecture.*

### 5.1 Architecture & Design Highlights
* **Architectural Decoupling**: Business calculations and parallel array state reside inside `InventoryManager.java` with zero GUI dependencies, ensuring 100% logic reusability.
* **Component Framework**: Utilizes a 5-tab `JTabbedPane`, structured `JTable` data grids, native `BorderLayout` and `GridLayout` managers, and a custom `TableCellRenderer` that dynamically renders understocked rows in **soft red**.

---

### 5.2 GUI Screenshot Placements

```
===================================================================================
[ PASTE SCREENSHOT 7 HERE: GUI - TAB 1: PRODUCT ENTRY FORM ]
(Shows input fields for Name, Price, Quantity, Min Stock, and green Add Product button)
===================================================================================
```

```
===================================================================================
[ PASTE SCREENSHOT 8 HERE: GUI - TAB 2: INVENTORY VALUE CALCULATION ]
(Shows JTable with calculated item values and the bold highlighted TOTAL summary row)
===================================================================================
```

```
===================================================================================
[ PASTE SCREENSHOT 9 HERE: GUI - TAB 3: LOW STOCK VALIDATION ]
(Shows filtered JTable displaying under-stocked items with required replenishment deficit)
===================================================================================
```

```
===================================================================================
[ PASTE SCREENSHOT 10 HERE: GUI - TAB 4: PRODUCT SEARCH ]
(Shows live search input field with tabular product card results)
===================================================================================
```

```
===================================================================================
[ PASTE SCREENSHOT 11 HERE: GUI - TAB 5: INVENTORY REPORT TABLE ]
(Shows master table overview with critical low-stock rows visually highlighted in RED)
===================================================================================
```

---

## 6. CONCLUSION & FUTURE SCOPE

### 6.1 Conclusion
The **Grocery Inventory Calculator** case study successfully accomplishes all academic and functional requirements set forth by ITM Skills University. By synthesizing fundamental Java building blocks—synchronized parallel arrays, iterative structures, defensive exception handling, mathematical operators, and linear searching—the project provides an accurate, dependable inventory tracking system. The design demonstrates that robust, production-quality retail utilities can be constructed using pure foundational Java principles without heavy external dependencies.

### 6.2 Future Scope & Industrial Enhancements
1. **Persistent Data Storage**: Integrating standard File I/O (`java.io.File`, CSV serialization) or database connectivity via JDBC (SQLite / MySQL) to retain inventory state across application restarts.
2. **Hardware Barcode Integration**: Connecting a USB handheld barcode reader to pipe SKU numbers directly into the search and entry modules.
3. **Automated Supplier Email Alerts**: Incorporating `javax.mail` APIs to automatically draft and dispatch restock purchase orders to vendors when inventory items hit zero units.
4. **Role-Based Access Control (RBAC)**: Introducing login authentication to separate cashier functions (view/search) from manager functions (add/modify stock).
