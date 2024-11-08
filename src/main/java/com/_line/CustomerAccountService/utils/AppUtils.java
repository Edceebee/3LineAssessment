package com._line.CustomerAccountService.utils;

import org.springframework.stereotype.Component;
import java.util.Random;


@Component
public class AppUtils {

    public String generateRandom10DigitNumber() {
        Random random = new Random();
        // Generate a random number between 1000000000 and 9999999999 (inclusive)
        long random10DigitNumber = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return String.valueOf(random10DigitNumber);
    }
}
