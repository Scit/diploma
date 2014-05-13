package scit.diploma.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by scit on 5/13/14.
 */
public final class PrefixGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String getUniquePrefix() {
        return new BigInteger(130, random).toString(32);
    }
}
