package cn.edu.nju.story.map.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * RandomValueStringGenerator
 *
 * @author xuan
 * @date 2019-01-15
 */
public class RandomValueStringGenerator {

    private static final char[] DEFAULT_CODEC = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            .toCharArray();

    private Random random = new SecureRandom();

    private int length;

    public RandomValueStringGenerator() {
        this(6);
    }


    public RandomValueStringGenerator(int length) {
        this.length = length;
    }

    public String generate() {
        byte[] verifierBytes = new byte[length];
        random.nextBytes(verifierBytes);
        return getAuthorizationCodeString(verifierBytes);
    }

    protected String getAuthorizationCodeString(byte[] verifierBytes) {
        char[] chars = new char[verifierBytes.length];
        for (int i = 0; i < verifierBytes.length; i++) {
            chars[i] = DEFAULT_CODEC[((verifierBytes[i] & 0xFF) % DEFAULT_CODEC.length)];
        }
        return new String(chars);
    }


    public void setRandom(Random random) {
        this.random = random;
    }


    public void setLength(int length) {
        this.length = length;
    }

}

