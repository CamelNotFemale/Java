import House.*;

public class Main {
    public static void main(String[] args) {
        // создаём строителя стандартных планировок
        StandartHouseBuilder builder = new StandartHouseBuilder();
        // менеджер реализации различных шаблонов построения
        Director director = new Director(builder);

        // реализовать стандартный шаблон дома
        director.makeStandartHouse();
        builder.getResult().getInfo();
        // реализовать премиальный шаблон дома
        director.makeLuxeryHouse();
        builder.getResult().getInfo();
    }
}
