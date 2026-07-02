package store;

import model.User;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UserStore {
    private static final Map<String, User> USERS_BY_EMAIL = new ConcurrentHashMap<>();

    static {
        // Tài khoản mẫu để thử nhanh phần đăng nhập.
        register(new User("Celine Demo", "0901234567", "demo@celinecloset.vn", "123456"));
    }

    private UserStore() {
    }

    public static void register(User user) {
        USERS_BY_EMAIL.put(normalizeEmail(user.getEmail()), user);
    }

    public static boolean emailExists(String email) {
        return USERS_BY_EMAIL.containsKey(normalizeEmail(email));
    }

    public static boolean phoneExists(String phone) {
        return allUsers().stream().anyMatch(user -> user.getPhone().equals(phone));
    }

    public static User findByIdentity(String identity) {
        if (identity == null) {
            return null;
        }

        String cleaned = identity.trim();
        User byEmail = USERS_BY_EMAIL.get(normalizeEmail(cleaned));
        if (byEmail != null) {
            return byEmail;
        }

        return allUsers().stream()
                .filter(user -> user.getPhone().equals(cleaned))
                .findFirst()
                .orElse(null);
    }

    public static Collection<User> allUsers() {
        return USERS_BY_EMAIL.values();
    }

    private static String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
