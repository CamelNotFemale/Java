package com.company;

public class TransportFactory
{
    /** Перечисление типов транспорта **/
    public enum TransportType
    {
        TRUCK, // Грузовик
        BARGE  // Баржа
    }

    /** Получение транспорта указанного типа **/
    public static Transport getTransport(TransportType TT)
    {
        Transport transport = null;
        switch(TT)
        {
            case TRUCK:
            {
                transport = new Truck();
                break;
            }
            case BARGE:
            {
                transport = new Barge();
                break;
            }
            default:
            {
                break;
            }
        }
        return transport;
    }
}
