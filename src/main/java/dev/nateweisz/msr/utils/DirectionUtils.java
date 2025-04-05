package dev.nateweisz.msr.utils;

public class DirectionUtils {
    public static String getDirection(float yaw) {
        yaw += 180;

        return switch (Math.round(yaw / 90) % 4) {
            case 0 -> "north";
            case 1 -> "east";
            case 2 -> "south";
            case 3 -> "west";
            default -> throw new IllegalStateException("Unexpected value: " + Math.round(yaw / 90) % 4);
        };
    }
}
