package ru.zinoviev.quest.request.handler.transport.protocol;

import java.util.Objects;

public class AnsiConsole {

    private AnsiConsole() { /* util */ }

    public enum BrightColor {
        RED("\u001B[91m"),
        GREEN("\u001B[92m"),
        YELLOW("\u001B[93m"),
        BLUE("\u001B[94m"),
        CYAN("\u001B[96m"),
        RESET("\u001B[0m");

        private final String code;
        BrightColor(String code) { this.code = code; }
        public String code() { return code; }
    }

    /** Вернуть строку, обёрнутую в ANSI цвет (и сброшенную в конце). */
    public static String colorize(String text, BrightColor color) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(color, "color");
        return color.code() + text + BrightColor.RESET.code();
    }

    /** println с цветом. */
    public static void println(String text, BrightColor color) {
        System.out.println(colorize(text, color));
    }

    /** print с цветом. */
    public static void print(String text, BrightColor color) {
        System.out.print(colorize(text, color));
    }

    /** printf с цветом (форматируется через String.format). */
    public static void printf(BrightColor color, String format, Object... args) {
        String formatted = String.format(format, args);
        System.out.print(colorize(formatted, color));
    }


}
