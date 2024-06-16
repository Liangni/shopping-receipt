import java.util.List;

public class CaliforniaTaxStrategy implements TaxStrategy{
    private static final double TAX_RATE = 0.0975;
    private static final List<String> EXEMPT_CATEGORIES = List.of("food");

    @Override
    public double getTaxRate() {
        return TAX_RATE;
    }

    @Override
    public List<String> getExemptCategories() {
        return EXEMPT_CATEGORIES;
    }
}
