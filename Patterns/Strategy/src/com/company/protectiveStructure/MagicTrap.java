package com.company.protectiveStructure;

import com.company.defenceStrategy.StrategyFactory;

public class MagicTrap extends ProtectiveStructure{

    public MagicTrap() {
        this.health = 400;
        buildingNotify();
    }

    @Override
    public void buildingNotify() {
        System.out.println("В окрестностях установлены магические ловушки, врага ждет сюрприз");
    }

    @Override
    public StrategyFactory.EnemyType protectsAgainst() {
        return StrategyFactory.EnemyType.UNDEAD;
    }
}
