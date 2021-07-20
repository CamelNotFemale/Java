package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        // задаёем файл для Spring context
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Использование IoC (инверсия управления) с ручным созданием созависимости
        Computer computer = context.getBean("computer", Computer.class);
        Computer secondComputer = context.getBean("computer", Computer.class);

        computer.openPlayer();

        computer.player.setVolume(10);
        System.out.println(computer.player.getName() + " " + computer.player.getVolume());
        System.out.println(secondComputer.player.getName() + " " + secondComputer.player.getVolume());

        context.close();
    }
}
