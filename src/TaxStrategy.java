import java.util.List;

public interface TaxStrategy {
    double getTaxRate();
    List<String> getExemptCategories();
}
