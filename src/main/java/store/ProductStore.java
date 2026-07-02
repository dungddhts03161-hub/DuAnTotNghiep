package store;

import model.Category;
import model.Product;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ProductStore {
    private static final List<Category> CATEGORIES = new ArrayList<>();
    private static final List<Product> PRODUCTS = new ArrayList<>();

    static {
        Category dress = new Category(1, "Đầm", "Đầm công sở và đầm dạo phố", 1);
        Category shirt = new Category(2, "Áo", "Áo sơ mi, áo kiểu, áo thun", 1);
        Category skirt = new Category(3, "Chân váy", "Chân váy midi, chữ A, xếp ly", 1);
        Category set = new Category(4, "Set đồ", "Set phối sẵn theo phong cách boutique", 1);
        CATEGORIES.addAll(Arrays.asList(dress, shirt, skirt, set));

        PRODUCTS.add(new Product(1, "Đầm linen cổ vuông Celine", "Chất linen pha cotton, dáng xòe nhẹ, hợp đi làm và đi cà phê.", price(620000), 18, 1, dress, "assets/product-1.svg", sizes("S", "M", "L", "XL"), colors("Kem", "Đen", "Nâu")));
        PRODUCTS.add(new Product(2, "Đầm midi tay phồng Elise", "Thiết kế nữ tính, có lót trong, phần eo ôm vừa phải.", price(690000), 12, 1, dress, "assets/product-2.svg", sizes("S", "M", "L"), colors("Trắng", "Be", "Xanh olive")));
        PRODUCTS.add(new Product(3, "Áo sơ mi lụa mềm Luna", "Bề mặt vải rũ nhẹ, dễ phối cùng quần tây hoặc chân váy.", price(390000), 30, 1, shirt, "assets/product-3.svg", sizes("S", "M", "L", "XL"), colors("Trắng", "Hồng phấn", "Đen")));
        PRODUCTS.add(new Product(4, "Áo kiểu cổ nơ Paris", "Dáng áo nhẹ nhàng, điểm nhấn nơ cổ, phù hợp phong cách thanh lịch.", price(420000), 20, 1, shirt, "assets/product-4.svg", sizes("S", "M", "L"), colors("Kem", "Đen")));
        PRODUCTS.add(new Product(5, "Chân váy midi Grace", "Form chữ A, chất vải đứng dáng, dễ mặc trong nhiều hoàn cảnh.", price(450000), 15, 1, skirt, "assets/product-5.svg", sizes("S", "M", "L", "XL"), colors("Đen", "Nâu", "Be")));
        PRODUCTS.add(new Product(6, "Chân váy xếp ly Muse", "Thiết kế xếp ly mềm, chiều dài qua gối, tạo vẻ dịu dàng.", price(470000), 11, 1, skirt, "assets/product-6.svg", sizes("S", "M", "L"), colors("Kem", "Ghi", "Xanh rêu")));
        PRODUCTS.add(new Product(7, "Set áo vest và váy Ivy", "Set phối sẵn gồm áo khoác ngắn và chân váy, hợp dự tiệc nhẹ.", price(890000), 8, 1, set, "assets/product-7.svg", sizes("S", "M", "L"), colors("Đen", "Kem")));
        PRODUCTS.add(new Product(8, "Set dạo phố Minimal", "Áo tay ngắn phối chân váy đơn giản, dễ mặc hằng ngày.", price(760000), 16, 1, set, "assets/product-8.svg", sizes("S", "M", "L", "XL"), colors("Be", "Nâu sữa", "Đen")));
    }

    private ProductStore() {
    }

    public static List<Category> getCategories() {
        return Collections.unmodifiableList(CATEGORIES);
    }

    public static List<Product> getAllProducts() {
        return PRODUCTS.stream()
                .filter(Product::isAvailable)
                .collect(Collectors.toList());
    }

    public static List<Product> search(String keyword, int categoryId) {
        String cleanedKeyword = normalize(keyword);
        return PRODUCTS.stream()
                .filter(Product::isAvailable)
                .filter(product -> categoryId <= 0 || product.getDanhMuc().getMaDM() == categoryId)
                .filter(product -> cleanedKeyword.isBlank()
                        || normalize(product.getTenSP()).contains(cleanedKeyword)
                        || String.valueOf(product.getMaSP()).contains(cleanedKeyword)
                        || normalize(product.getDanhMuc().getTenDM()).contains(cleanedKeyword))
                .collect(Collectors.toList());
    }

    public static Optional<Product> findById(int maSP) {
        return PRODUCTS.stream()
                .filter(product -> product.getMaSP() == maSP)
                .findFirst();
    }

    public static Optional<Category> findCategoryById(int maDM) {
        return CATEGORIES.stream()
                .filter(category -> category.getMaDM() == maDM)
                .findFirst();
    }

    private static BigDecimal price(long value) {
        return BigDecimal.valueOf(value);
    }

    private static List<String> sizes(String... values) {
        return Arrays.asList(values);
    }

    private static List<String> colors(String... values) {
        return Arrays.asList(values);
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase(Locale.ROOT).trim();
    }

    public static List<Product> findAll() {
        return getAllProducts();
    }
}
