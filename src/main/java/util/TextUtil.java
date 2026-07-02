package util;

import java.text.Normalizer;
import java.util.Locale;

public final class TextUtil {
    private TextUtil() {
    }

    public static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    public static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
