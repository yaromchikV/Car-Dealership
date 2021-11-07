package com.yaromchikv.dealership.utils;

public class Hash {

    public static String convert(int id, String pswd) {
        int salt = getControlSum(id, pswd);
        int hashSize = 32;

        StringBuilder password = new StringBuilder(pswd);
        StringBuilder hash = new StringBuilder();

        for (int i = 0; i < hashSize * 4 - pswd.length(); i++) {
            password.append(getRequiredChar(password.charAt(i) + password.charAt(i + 1)));
        }
        for (int i = 0, center = password.length() / 2; i < center; i++) {
            hash.append(getRequiredChar(password.charAt(center - i) + password.charAt(center + i)));
        }
        password = new StringBuilder(hash);
        hash.setLength(0);

        for (int i = 0; hash.length() < hashSize; i++) {
            if (i % 5 == 0)
                hash.append(getRequiredChar(password.charAt(i) + salt));
            else if (i % 2 == 0)
                hash.append(getRequiredChar(password.charAt(i) + password.charAt(i + 1)));
            else
                hash.append(password.charAt(i));
        }

        return hash.toString();
    }

    private static char getRequiredChar(int ch) {
        ch += 256;
        while (!((ch >= 48 && ch <= 57) || (ch >= 97 && ch <= 122))) {
            if (ch < 48)
                ch += 24;
            else
                ch -= 80;
        }
        return (char) ch;
    }

    private static int getControlSum(int id, String password) {
        int salt = 0;
        for (int i = 0; i < password.length(); i++) {
            salt += (id % 100) + password.charAt(i);
        }
        return salt;
    }

}
