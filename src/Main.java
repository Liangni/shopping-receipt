import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    // 產品 Class：用於儲存產品資訊如名稱、價格、數量、種類
    static class Product {
        String name;
        double price;
        int quantity;
        String category;

        Product(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.category = ProductCategory.getCategory(name);
        }
    }

    // 產品類別 Class：用以決定產品種類
    static class ProductCategory {
        private static final Map<String, String> CATEGORY_MAP = Map.of(
                "book", "non-food",
                "pencil", "non-food",
                "potato chips", "food",
                "shirt", "clothing"
        );

        public static String getCategory(String name) {
            return CATEGORY_MAP.getOrDefault(name, "non-food");
        }
    }

    // 將稅額進位至 0.05 倍數的方法
    private static double roundUpToNearestFiveCents(double value) {
        // 將值轉換為 BigDecimal
        BigDecimal bigDecimalValue = new BigDecimal(value);
        // 將值乘以 20，因為 0.05 乘以 20 剛好會是 1
        BigDecimal multiplied = bigDecimalValue.multiply(new BigDecimal("20"));
        // 無條件進位到整數，這樣可以確保待會除以 20 時，結果會是與原本數字最接近的 0.05 倍數
        BigDecimal rounded = multiplied.setScale(0, RoundingMode.CEILING);
        // 將取整後的數字除以 20，還原到原本的範圍
        BigDecimal result = rounded.divide(new BigDecimal("20"), 2, RoundingMode.HALF_UP);
        // 轉回 double 並返回
        return result.doubleValue();
    }

    // 計算稅率的方法
    private static double calculateSalesTax(Product product, double taxRate, List<String> exemptCategories) {
        if (exemptCategories.contains(product.category)) {
            return 0.0;
        } else {
            return roundUpToNearestFiveCents(product.price * product.quantity * taxRate);
        }
    }

    // 印出發票的方法
    private static void printReceipt(List<Product> products, String location) {
        TaxStrategy taxStrategy = TaxStrategyFactory.getTaxStrategy(location);

        double taxRate = taxStrategy.getTaxRate();
        List<String> exemptCategories = taxStrategy.getExemptCategories();

        double subtotal = 0.0;
        double totalTax = 0.0;

        System.out.printf("%-15s%-10s%-5s%n", "item", "price", "qty");
        for (Product product : products) {
            double productSubtotal = product.price * product.quantity;
            double productTax = calculateSalesTax(product, taxRate, exemptCategories);

            System.out.printf("%-15s$%-9.2f%-5d%n", product.name, product.price, product.quantity);
            subtotal += productSubtotal;
            totalTax += productTax;
        }

        double total = subtotal + totalTax;
        System.out.printf("subtotal:                $%.2f%n", subtotal);
        System.out.printf("tax:                     $%.2f%n", totalTax);
        System.out.printf("total:                   $%.2f%n", total);
    }

    // 處理客戶 input 並進行印出作業的方法
    public static void processInput(String location, Object[][] productsInput) {
        List<Product> products = new ArrayList<>();
        for (Object[] productInput : productsInput) {
            String name = (String) productInput[0];
            double price = (double) productInput[1];
            int quantity = (int) productInput[2];
            products.add(new Product(name, price, quantity));
        }
        printReceipt(products, location);
    }

    public static void main(String[] args) {
        // Use case 測試
        String locationCA = "CA";
        Object[][] productsCA = {
                {"book", 17.99, 1},
                {"potato chips", 3.99, 1}
        };

        String locationNY = "NY";
        Object[][] productsNY1 = {
                {"book", 17.99, 1},
                {"pencil", 2.99, 3}
        };

        Object[][] productsNY2 = {
                {"pencil", 2.99, 2},
                {"shirt", 29.99, 1}
        };

        System.out.println("Use case 1:");
        processInput(locationCA, productsCA);

        System.out.println("\nUse case 2:");
        processInput(locationNY, productsNY1);

        System.out.println("\nUse case 3:");
        processInput(locationNY, productsNY2);
    }
}