package org.example;

public class RockMusic implements Music {
    private RockMusic() {}
    // factory method
    public static RockMusic getMusic() {
        return new RockMusic();
    }
    @Override
    public String getSong() {
        return "Beggin";
    }
}
