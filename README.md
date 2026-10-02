# 🛒 Grocery Inventory Calculator & Store Management System

[![Java Version](https://img.shields.io/badge/Java-17%20%7C%2021%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Architecture](https://img.shields.io/badge/Architecture-CLI%20%26%20Swing%20GUI-2B7489?style=for-the-badge)](https://github.com/Ankitraj-17/Java-Final-Project)
[![License](https://img.shields.io/badge/License-Academic%20Project-green?style=for-the-badge)](LICENSE)

A complete, production-grade retail inventory tracking and financial valuation suite developed in Java. The project solves inventory management challenges for grocery stores—including manual stock miscalculations, sudden stock-outs, and untracked capital—by offering **two distinct operational versions**:

1. 💻 **Console-Only CLI Version** (`GroceryInventoryCalculator.java`): A pure, zero-dependency, menu-driven command-line system built strictly on foundational Java core primitives (parallel 1D arrays, deterministic loops, robust defensive scanner input).
2. 🖥️ **Graphical User Interface (GUI) Version** (`GroceryInventoryGUI.java` & `InventoryManager.java`): An interactive desktop application built with Java Swing and AWT, engineered with a decoupled business logic model, tabbed navigation, real-time data tables, and dynamic visual stock alerts.

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Two Implementations](#-two-implementations)
  - [Version 1: Console-Only Version (CLI)](#version-1-console-only-version-cli)
  - [Version 2: Desktop GUI Version (Swing)](#version-2-desktop-gui-version-swing)
- [Core Features & Modules](#-core-features--modules)
- [Architecture & Technical Design](#-architecture--technical-design)
- [Project File Structure](#-project-file-structure)
- [Prerequisites & System Requirements](#-prerequisites--system-requirements)
- [Compilation & Execution Guide](#-compilation--execution-guide)
  - [Running the Console-Only Version](#1-running-the-console-only-version)
  - [Running the GUI Version](#2-running-the-gui-version)
  - [Using Visual Studio Code](#3-using-visual-studio-code)
- [CLI Workflow & Sample Output](#-cli-workflow--sample-output)
- [GUI Capabilities & User Experience](#-gui-capabilities--user-experience)
- [Academic Case Study Alignment](#-academic-case-study-alignment)
- [Author & Credits](#-author--credits)

---

## 📖 Project Overview

Managing grocery stock is a daily mission-critical operational task. Small-to-medium retail stores often suffer from stock discrepancies, expired shelf life, inventory shrinkage, and lack of visibility into inventory asset values. 

The **Grocery Inventory Calculator** delivers a dependable, high-precision solution that:
- Captures and validates product entries (names, prices, physical stock quantities, and minimum safety thresholds).
- Calculates exact inventory valuations (individual product valuation and cumulative store value in `Rs.`).
- Automatically scans stock and flags items requiring replenishment with deficit unit analysis.
- Provides case-insensitive keyword search for immediate lookup.
- Generates formatted inventory ledgers and comprehensive store reports.

---

## 🔀 Two Implementations

This repository includes two parallel, full-featured implementations tailored for different runtime requirements and design patterns:

### Version 1: Console-Only Version (CLI)
* **Source File**: `GroceryInventoryCalculator.java`
* **Paradigm**: Procedural & Structured Java Programming
* **Target Environment**: Standard terminal, remote SSH shells, and headless systems.
* **Key Traits**:
  - Implements all business modules (Modules 1–5) in a single, self-contained executable class.
  - Relies solely on **synchronized parallel 1D arrays** (`names[]`, `prices[]`, `quantities[]`, `minStock[]`) without relying on the Java Collections Framework (`ArrayList`, `HashMap`), strictly matching foundational computer science criteria.
  - Employs defensive `Scanner` stream reading to prevent newline buffer issues (`InputMismatchException`).
  - Interactive, ANSI-styled menu navigation with input validation loops.

### Version 2: Desktop GUI Version (Swing)
* **Source Files**: `GroceryInventoryGUI.java` (Presentation Layer) and `InventoryManager.java` (Business Logic Layer)
* **Paradigm**: Object-Oriented Event-Driven Architecture with Separation of Concerns (SoC)
* **Target Environment**: Modern desktop window managers (macOS, Windows, Linux).
* **Key Traits**:
  - **Decoupled Business Logic**: `InventoryManager.java` acts as the headless backend controller managing state and calculations with zero GUI dependencies.
  - **Tabbed Interface**: 5 clean, organized navigation tabs (`Product Entry`, `Inventory Calculation`, `Stock Validation`, `Product Search`, and `Inventory Report`).
  - **Interactive JTable Grids**: Non-editable, styled data tables with custom table headers.
  - **Conditional Styling / Visual Alerting**: Custom `TableCellRenderer` dynamically tints low-stock and out-of-stock items in **soft red** (`#FFD7D7`) for immediate visual prioritization.
  - **macOS & Windows UI Adaptation**: Responsive button styling and unified color schemes (Forest Green `#187341`).

---

## ✨ Core Features & Modules

| Module # | Module Name | Description | Logic / Formula |
| :---: | :--- | :--- | :--- |
| **01** | **Product Entry & Validation** | Registers new products with duplicate checking, non-negative price validation, and initial stock warning. | Cap: `MAX_PRODUCTS = 100`, validates `price >= 0`, `qty >= 0`, `min >= 0` |
| **02** | **Inventory Valuation Engine** | Calculates per-product valuation and aggregates grand total store valuation. | $\text{Value} = \text{Price} \times \text{Quantity}$, $\text{Total} = \sum \text{Value}_i$ |
| **03** | **Stock Validation & Restock Alerts** | Scans stock against threshold limits, identifies missing units, and assigns status. | Deficit: $\text{Min} - \text{Qty}$, flags `LOW` or `OUT OF STOCK` |
| **04** | **Product Search Engine** | Performs fast linear search across product names with case-insensitive matching. | $O(n)$ search with early-exit termination upon match |
| **05** | **Inventory Ledger & Report** | Produces a complete matrix showing counts, total units, status flags, and valuations. | Full tabular ledger + statistical summary |

---

## 🏛️ Architecture & Technical Design

```
+-----------------------------------------------------------------------------------+
|                                  USER INTERFACES                                  |
|                                                                                   |
|   +--------------------------------------+   +--------------------------------+   |
|   |   GroceryInventoryCalculator.java    |   |    GroceryInventoryGUI.java    |   |
|   |       (Interactive CLI Menu)         |   |     (Java Swing 5-Tab Window)  |   |
|   +--------------------------------------+   +--------------------------------+   |
|                       |                                       |                   |
+-----------------------|---------------------------------------|-------------------+
                        |                                       |
                        v                                       v
+-----------------------------------------------------------------------------------+
|                                BUSINESS LOGIC & DATA                              |
|                                                                                   |
|   +--------------------------------------+   +--------------------------------+   |
|   |         Internal CLI Logic           |   |      InventoryManager.java     |   |
|   |      (Parallel Arrays & Loop)        |   |   (Headless Controller Engine) |   |
|   +--------------------------------------+   +--------------------------------+   |
|                       |                                       |                   |
|                       +-------------------+-------------------+                   |
|                                           |                                       |
|                                           v                                       |
|                  +-------------------------------------------------+              |
|                  |          SYNCHRONIZED PARALLEL ARRAYS           |              |
|                  |  - names[]      : String[100]                   |              |
|                  |  - prices[]     : double[100]                   |              |
|                  |  - quantities[] : int[100]                      |              |
|                  |  - minStock[]   : int[100]                      |              |
|                  |  - productCount : int                           |              |
|                  +-------------------------------------------------+              |
+-----------------------------------------------------------------------------------+
```

### 1. Synchronized Parallel 1D Arrays
Instead of utilizing dynamic high-level collections, both versions demonstrate core memory and array management using four synchronized one-dimensional arrays tied together by a common index pointer:
- `names[i]`: Stores product name (String).
- `prices[i]`: Stores unit price in INR (double).
- `quantities[i]`: Stores current available units (int).
- `minStock[i]`: Stores minimum safety threshold (int).
- Fixed capacity: `MAX_PRODUCTS = 100`.

### 2. Defensive Programming & Buffer-Safe Parsing
- Standard `Scanner.nextInt()` and `Scanner.nextDouble()` leave dangling newline tokens (`\n`) in input buffers, causing subsequent `nextLine()` reads to skip.
- The project implements defensive wrappers (`readString()`, `readInt()`, `readDouble()`) that always consume the full line via `nextLine().trim()` and safely parse values with `try-catch (NumberFormatException)`.
- Graceful `EOF` checking via `scanner.hasNextLine()` prevents program crashes on pipe redirection or stream close.

---

## 📂 Project File Structure

```
Java-Final-Project/
├── GroceryInventoryCalculator.java            # Version 1: Complete Console-only CLI implementation
├── GroceryInventoryGUI.java                   # Version 2: Modern Java Swing Desktop GUI interface
├── InventoryManager.java                      # Version 2: Core headless business logic & data model
├── Grocery_Inventory_Calculator_Case_Study_Report.md  # Formal Academic Case Study Report (CS161)
├── .vscode/
│   ├── launch.json                            # VS Code debug & run configurations
│   └── settings.json                          # IDE Java runtime settings
├── .gitignore                                 # Git rules excluding .class and OS binaries
└── README.md                                  # Comprehensive documentation (this file)
```

---

## 💻 Prerequisites & System Requirements

### Hardware Requirements
- **Processor**: Any x86/x64 or ARM processor (Intel Core i3+, AMD Ryzen, or Apple Silicon M1/M2/M3/M4).
- **RAM**: Minimum 2 GB (4 GB recommended).
- **Storage**: ~50 MB available disk space.
- **Display**: Minimum 1024x768 resolution (for GUI mode).

### Software Requirements
- **Operating System**: macOS, Windows 10/11, or Linux (Ubuntu, Debian, Fedora, Arch).
- **Java Platform**: Java Development Kit (JDK 17 LTS, JDK 21 LTS, or newer).
- Verify your Java installation:
  ```bash
  java -version
  javac -version
  ```

---

## 🚀 Compilation & Execution Guide

Clone the repository to your local machine:
```bash
git clone https://github.com/Ankitraj-17/Java-Final-Project.git
cd Java-Final-Project
```

### 1. Running the Console-Only Version

Compile the standalone console source file:
```bash
javac GroceryInventoryCalculator.java
```

Execute the console program:
```bash
java GroceryInventoryCalculator
```

---

### 2. Running the GUI Version

Compile both the GUI interface and the core inventory manager:
```bash
javac InventoryManager.java GroceryInventoryGUI.java
```

Launch the desktop GUI:
```bash
java GroceryInventoryGUI
```

---

### 3. Using Visual Studio Code

Pre-configured run profiles are provided in `.vscode/launch.json`. In VS Code:
1. Open the project root folder in VS Code (`File > Open Folder...`).
2. Press `Ctrl+Shift+D` (or `Cmd+Shift+D` on macOS) to navigate to the **Run & Debug** tab.
3. Select either:
   - **Launch GroceryInventoryCalculator (Console)**
   - **Launch GroceryInventoryGUI (Swing GUI)**
4. Click the green **Play** button or hit `F5`.

---

## 🖥️ CLI Workflow & Sample Output

### Main Menu Interface
```text
==================================================
   GROCERY INVENTORY CALCULATOR - STORE SYSTEM   
==================================================

===== MAIN MENU =====
1. Add Products
2. Calculate Inventory Value
3. Show Low-Stock Products
4. Search Product
5. Display Inventory Report
6. Exit
Enter your choice (1-6): 
```

### Sample Inventory Valuation Ledger (Module 2)
```text
--- [Module 2: Inventory Value Calculation] ---
Inventory Breakdown (per product):
  Basmati Rice         : Rs. 120.00 x 45 = Rs. 5400.00
  Amul Milk (1L)       : Rs. 66.00 x 12 = Rs. 792.00
  Refined Sugar        : Rs. 48.00 x 8 = Rs. 384.00
  Sunflower Oil (1L)   : Rs. 145.00 x 30 = Rs. 4350.00
--------------------------------------------------
Total Distinct Products : 4
Total Inventory Valuation: Rs. 10926.00
```

### Sample Low-Stock Deficit Alerts (Module 3)
```text
--- [Module 3: Stock Validation (Low-Stock Alert)] ---
No.   | Product Name         | Current    | Min Stock  | Deficit Units  | Stock Level   
-------------------------------------------------------------------------------------
1     | Amul Milk (1L)       | 12         | 25         | 13             | LOW           
2     | Refined Sugar        | 8          | 20         | 12             | LOW           
-------------------------------------------------------------------------------------
[!] Alert: 2 product(s) are below minimum threshold and require restocking.
```

### Sample Inventory Report (Module 5)
```text
--- [Module 5: Inventory Report] ---
========================================================================================
No.  | Name                 | Price (Rs) | Quantity | Min Stock | Value (Rs)   | Status    
========================================================================================
1    | Basmati Rice         |     120.00 |       45 |        20 |      5400.00 | OK        
2    | Amul Milk (1L)       |      66.00 |       12 |        25 |       792.00 | RESTOCK   
3    | Refined Sugar        |      48.00 |        8 |        20 |       384.00 | RESTOCK   
4    | Sunflower Oil (1L)   |     145.00 |       30 |        15 |      4350.00 | OK        
----------------------------------------------------------------------------------------
Total Distinct Products : 4
Total Physical Units    : 95 units
Total Inventory Value   : Rs. 10926.00
========================================================================================
```

---

## 🎨 GUI Capabilities & User Experience

The Swing GUI version (`GroceryInventoryGUI.java`) enhances the store management experience:

- **Tab 1: Product Entry**: Clean input form featuring field-level error messages (duplicate product detection, numeric constraints, negative value prevention) and auto-clearing inputs.
- **Tab 2: Inventory Calculation**: Real-time evaluation table calculating sub-totals per product with a highlighted `TOTAL` valuation summary row.
- **Tab 3: Stock Validation**: Dynamic filter displaying exclusively understocked items along with their required deficit units to order.
- **Tab 4: Product Search**: Fast search bar rendering instant product summary cards displaying price, stock quantity, min threshold, valuation, and restock status.
- **Tab 5: Inventory Report**: Full inventory matrix utilizing a custom `TableCellRenderer` that highlights items needing replenishment in **soft red** (`#FFD7D7`).
- **Footer Status Bar**: Real-time audit trail displaying the outcome of the most recent user action.

---

## 🎓 Academic Case Study Alignment

This project was developed in satisfaction of **Case Study 161 (Grocery Inventory Calculator)** under the B.Tech Computer Science & Engineering curriculum at **ITM Skills University**:

- **Core Competencies Demonstrated**:
  - Multi-attribute record tracking with synchronized 1D primitive arrays.
  - Linear Search algorithm with early-termination optimization.
  - Arithmetic accumulators and compound relational/logical evaluations (`&&`, `||`).
  - Robust exception handling (`try-catch`, `NumberFormatException`).
  - Separation of concerns through reusable static methods and headless models.
  - Event-driven desktop programming with Swing and custom cell renderers.

For detailed analysis, pseudocode, and mathematical formulation, refer to the [Academic Case Study Report](Grocery_Inventory_Calculator_Case_Study_Report.md).

---

## 👨‍💻 Author & Credits

* **Developer**: Ankit Raj Jha
* **Course**: Java Programming (B.Tech CSE)
* **Institution**: School of Future Tech, ITM Skills University
* **GitHub**: [@Ankitraj-17](https://github.com/Ankitraj-17)
* **Repository**: [Java-Final-Project](https://github.com/Ankitraj-17/Java-Final-Project)

---

⭐ *If you found this project helpful for learning Java fundamentals, parallel array architecture, or Swing GUI development, please consider giving this repository a star!*
