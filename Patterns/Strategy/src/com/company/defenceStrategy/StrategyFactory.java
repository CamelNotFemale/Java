package com.company.defenceStrategy;

import java.util.HashMap;
import java.util.Map;
/** Фабрика стратегий защиты */
public class StrategyFactory {
    /** Хранение всех пар "Враг - Стратегия Защиты" в единственном виде */
    static Map<EnemyType, IDefenceStrategy> strategys = new HashMap<>();
    /** Перечисление типов нападающих **/
    public enum EnemyType
    {
        HUMAN, // Люди
        ORC, // Орки
        UNDEAD // Нежить
    }
    /** Получение транспорта указанного типа **/
    public static IDefenceStrategy getStrategy(EnemyType ET)
    {
        IDefenceStrategy result = strategys.get(ET);
        if (result == null) {
            switch(ET)
            {
                case HUMAN:
                {
                    result = new OppositeHuman();
                    break;
                }
                case ORC:
                {
                    result = new OppositeOrcs();
                    break;
                }
                case UNDEAD:
                {
                    result = new OppositeUndead();
                    break;
                }
                default:
                {
                    break;
                }
            }
            strategys.put(ET, result);
        }
        return result;
    }
}
