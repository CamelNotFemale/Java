package com.company;

import com.company.defenceStrategy.*;

import java.awt.*;

/** База, требующая защиты */
public class NightElfBase {
    private String name;
    private double health;
    IDefenceStrategy iDefenceStrategy;

    public NightElfBase(String name, double health) {
        this.name = name;
        this.health = health;
        System.out.println("Создана лесная база ночных эльфов " + name);
    }

    public void setDefenceStrategy(IDefenceStrategy strategy) {
        iDefenceStrategy = strategy;
        iDefenceStrategy.executeStrategy();
    }

    public void enemyAttack(StrategyFactory.EnemyType ET){
        System.out.println(ET + " атакуют " + this.name);
        if (this.health <= 0 || iDefenceStrategy == null || iDefenceStrategy.getEnemyType() != ET) {
            System.out.println("  Защита не выдержала! Стратегия была провальна...");
            this.health = 0;
            System.out.println("  База стёрта с лица земли");
        }
        else {
            System.out.println("  Атака успешно отражена! Ваша стратегия сработала!");
        }
    }

}
