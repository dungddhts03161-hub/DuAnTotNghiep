package util;

/**
 * Demo project: mật khẩu đang so khớp plain text để học CRUD/DAO dễ hiểu.
 * Khi làm thật nên đổi sang BCrypt/Argon2 và không lưu mật khẩu thô.
 */
public final class PasswordUtil {
    private PasswordUtil() {
    }

    public static boolean matches(String rawPassword, String storedPassword) {
        return rawPassword != null && rawPassword.equals(storedPassword);
    }
}
