package com.company;

import com.company.defenceStrategy.IDefenceStrategy;
import com.company.defenceStrategy.StrategyFactory;

public class Main {

    public static void main(String[] args) {
        NightElfBase nightElfBase = new NightElfBase("Лесная Чаща", 3000);
        IDefenceStrategy strategyAgainstHuman = StrategyFactory.getStrategy(StrategyFactory.EnemyType.HUMAN);
        IDefenceStrategy strategyAgainstOrc = StrategyFactory.getStrategy(StrategyFactory.EnemyType.ORC);

        nightElfBase.setDefenceStrategy(strategyAgainstHuman);
        nightElfBase.enemyAttack(StrategyFactory.EnemyType.HUMAN);
        nightElfBase.enemyAttack(StrategyFactory.EnemyType.ORC);
    }
}
