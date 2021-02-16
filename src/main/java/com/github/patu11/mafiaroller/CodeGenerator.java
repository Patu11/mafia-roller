package com.github.patu11.mafiaroller;

import java.util.Random;

public class CodeGenerator {
    public static String generate() {
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String NUMBERS = "0123456789";
        final String ALPHA = UPPER + LOWER + NUMBERS;
        final int LENGTH = 6;

        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {

            int index = random.nextInt(ALPHA.length());

            char randomChar = ALPHA.charAt(index);

            builder.append(randomChar);
        }
        return builder.toString();
    }
}
