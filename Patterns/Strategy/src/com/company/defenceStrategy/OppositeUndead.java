package com.company.defenceStrategy;

import com.company.protectiveStructure.*;

public class OppositeUndead implements IDefenceStrategy{
    ProtectiveStructure protectiveStructure;

    @Override
    public void alarm() {
        System.out.println("Мерзкая нежить пачкает наши леса!");
    }

    @Override
    public void buildDefence(ProtectiveStructure protectiveStructure) {
        this.protectiveStructure = protectiveStructure;
    }

    @Override
    public void executeStrategy() {
        alarm();
        System.out.println("Установите для них ловушки, нежить лучше не подпускать!");
        buildDefence(new MagicTrap());
        System.out.println("Просим помощи у леса, дендроиды пойдут на передовую.");
    }

    @Override
    public StrategyFactory.EnemyType getEnemyType() {
        return StrategyFactory.EnemyType.UNDEAD;
    }
}
