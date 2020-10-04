package network;

public class Limits {

    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MAX_USERNAME_LENGTH = 15;

    public static boolean validUsernameLength(String username) {
        if(username.length() < Limits.MIN_USERNAME_LENGTH)
            return false;

        if(username.length() > Limits.MAX_USERNAME_LENGTH)
            return false;

        return true;
    }

    private Limits() {}
}
