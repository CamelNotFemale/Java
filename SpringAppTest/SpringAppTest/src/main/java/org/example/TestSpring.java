package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        // задаёем файл для Spring context
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Использование IoC (инверсия управления) с ручным созданием созависимости
        //Music music = context.getBean("musicBean", Music.class);
        //MusicPlayer player = new MusicPlayer(music);

        // Теперь используем DI - внедрение зависимостей
        MusicPlayer firstPlayer = context.getBean("musicPlayer", MusicPlayer.class);
        MusicPlayer secondPlayer = context.getBean("musicPlayer", MusicPlayer.class);

        firstPlayer.playMusic();

        firstPlayer.setVolume(20);
        System.out.println("Volume of the player " + firstPlayer.getName() +": " + firstPlayer.getVolume());
        System.out.println("Volume of the player " + secondPlayer.getName() +": " + secondPlayer.getVolume());

        context.close();
    }
}
