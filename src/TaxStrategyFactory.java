public class TaxStrategyFactory {
    public static TaxStrategy getTaxStrategy(String location) {
        switch (location) {
            case "CA":
                return new CaliforniaTaxStrategy();
            case "NY":
                return new NewYorkTaxStrategy();
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }
}
