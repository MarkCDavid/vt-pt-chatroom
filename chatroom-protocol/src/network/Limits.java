package network;

public class Limits {

    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MAX_USERNAME_LENGTH = 15;

    private Limits() {
    }

    public static boolean validUsernameLength(String username) {
        if (username.length() < Limits.MIN_USERNAME_LENGTH)
            return false;

        return username.length() <= Limits.MAX_USERNAME_LENGTH;
    }
}
