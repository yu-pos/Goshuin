package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private PasswordUtil() {}

    // 登録・変更用：ハッシュ化
    public static String hash(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("password is empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // ログイン照合用：ハッシュ一致判定
    public static boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) return false;

        // BCryptの形式でないもの（古い平文など）はここでは false
        if (!isBCryptHash(hashedPassword)) return false;

        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // 文字列がBCryptハッシュっぽいか判定
    public static boolean isBCryptHash(String s) {
        if (s == null) return false;
        // $2a$ / $2b$ / $2y$ など
        return s.startsWith("$2a$") || s.startsWith("$2b$") || s.startsWith("$2y$");
    }
}
