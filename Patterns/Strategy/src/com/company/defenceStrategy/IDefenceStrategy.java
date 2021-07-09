package com.company.defenceStrategy;

import com.company.protectiveStructure.*;
/** Общий интерфейс для стратегий защиты */
public interface IDefenceStrategy {
    /** Оповещение */
    void alarm();
    /** Возвести защиту */
    void buildDefence(ProtectiveStructure protectiveStructure);
    /** Реализовать стратегию защиты */
    void executeStrategy();
    /** Проверка защиты от конкретного типа врага */
    public abstract StrategyFactory.EnemyType getEnemyType();
}