package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class MusicPlayer {
    private List<Music> musicList= new ArrayList<>();
    @Value("${musicPlayer.name}")
    private String name;
    @Value("${musicPlayer.volume}")
    private int volume;

    // IoC - инверсия управления
    @Autowired
    // @Qualifier("...") - помогает избежать неоднозначности, для конструкторов  пишется в аргументах как в примере ниже
    public MusicPlayer(@Qualifier("rockMusic") List<Music> musicList) {
        this.musicList = musicList;
    }
    public MusicPlayer() {
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void playMusic() {
        for (Music music: musicList) {
            System.out.println("Playing " + music.getSong());
        }
    }
}
