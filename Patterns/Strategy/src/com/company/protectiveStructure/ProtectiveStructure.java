package com.company.protectiveStructure;

import com.company.defenceStrategy.StrategyFactory;

/** Способ обороны */
public abstract class ProtectiveStructure {
    double health;
    public abstract void buildingNotify();
    /** Проверка защиты от конкретного типа врага */
    public abstract StrategyFactory.EnemyType protectsAgainst();
}
