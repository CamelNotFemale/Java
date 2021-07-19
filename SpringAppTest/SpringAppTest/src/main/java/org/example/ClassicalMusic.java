package org.example;

public class ClassicalMusic implements Music {
    private ClassicalMusic() {}
    // factory method
    public static ClassicalMusic getMusic() {
        return new ClassicalMusic();
    }

    @Override
    public String getSong() {
        return "Hungarian Rhapsody";
    }
}
