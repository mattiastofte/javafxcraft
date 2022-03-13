package dev.mattiastofte;

public class Logger {
    public Logger() {
        System.out.println("A minecraft clone coded with ♡ by Mattias Tofte");
        System.out.println("");
        System.out.println("---------------- [DEBUG] ----------------");
        System.out.println("");
    }

    public void log(String type, String text) {
        System.out.println(String.format("[%s] %s", type, text));
    }
}
