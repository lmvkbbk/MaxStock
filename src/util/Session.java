package util;

public class Session {
    private static boolean isAdmin;

    public static void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }
}
