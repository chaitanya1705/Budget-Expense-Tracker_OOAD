package com.budgettracker.util;

public class DatabaseManager {
    private static DatabaseManager instance;

    // private constructor to prevent instantiation
    private DatabaseManager() {
        System.out.println("DatabaseManager instance created.");
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void connect() {
        System.out.println("Connecting to the database...");
    }
}