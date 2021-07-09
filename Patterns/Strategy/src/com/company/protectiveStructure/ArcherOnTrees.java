package com.company.protectiveStructure;

import com.company.defenceStrategy.StrategyFactory;

public class ArcherOnTrees extends ProtectiveStructure{

    public ArcherOnTrees() {
        this.health = 700;
        buildingNotify();
    }

    @Override
    public void buildingNotify() {
        System.out.println("Лучники снайперы заняли позиции!");
    }

    @Override
    public StrategyFactory.EnemyType protectsAgainst() {
        return StrategyFactory.EnemyType.HUMAN;
    }
}
