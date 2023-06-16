package com.logicea.cardsrestapi.card.utils;

import java.util.Arrays;

public class HelperUtility {
    // Helper method to validate the color format
    public static void validateColor(String color) {
        if (!color.matches("#[a-zA-Z0-9]{6}")) {
            throw new IllegalArgumentException("Invalid color format. Color must start with '#' and be followed by 6 alphanumeric characters.");
        }
    }

    // Helper method to validate the status
    public static void validateStatus(String status) {
        if (!Arrays.asList("TODO", "IN_PROGRESS", "DONE").contains(status)) {
            throw new IllegalArgumentException("Invalid status. Available statuses are: To Do, In Progress, Done.");
        }
    }
}
