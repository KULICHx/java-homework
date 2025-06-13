package edu.hw8.task3.cracker;

import java.util.Map;

public interface PasswordCracker {
    Map<String, String> crack(int maxPasswordLength);
}
