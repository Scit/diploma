package scit.diploma.utils;

/**
 * Created by scit on 5/20/14.
 */
public class MetadataHasher {
    private static String result = "";

    public static void reset() {
        result = "";
    }

    public static void add(int type, String name) {
        result += String.format("%02d", type) + name + ":";
    }

    public static String get() {
        return result;
    }
}
