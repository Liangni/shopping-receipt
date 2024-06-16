import java.util.List;

public class NewYorkTaxStrategy implements TaxStrategy {
    private static final double TAX_RATE = 0.08875;
    private static final List<String> EXEMPT_CATEGORIES = List.of("food", "clothing");

    @Override
    public double getTaxRate() {
        return TAX_RATE;
    }

    @Override
    public List<String> getExemptCategories() {
        return EXEMPT_CATEGORIES;
    }
}
