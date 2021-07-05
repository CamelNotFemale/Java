import House.*;
/** Класс-строитель домов со стандартной планировкой **/
public class StandartHouseBuilder implements HouseBuilder {
    /** Поле для хранения строящегося дома **/
    private House house;

    @Override
    public HouseBuilder buildWalls(int count, String material) {
        if (house != null) {
            house.setWalls(count, material);
        }
        return this;
    }
    @Override
    public HouseBuilder buildDoors(int count, String material) {
        if (house != null) {
            house.setDoors(count, material);
        }
        return this;
    }
    @Override
    public HouseBuilder buildRoof(String material) {
        if (house != null) {
            house.setRoof(material);
        }
        return this;
    }
    @Override
    public HouseBuilder buildWindows(int count, String material) {
        if (house != null) {
            house.setWindows(count, material);
        }
        return this;
    }
    @Override
    public HouseBuilder buildGarage() {
        if (house != null) {
            house.addGarage();
        }
        return this;
    }
    @Override
    public HouseBuilder buildSwimmingPool() {
        if (house != null) {
            house.addSwimmingPool();
        }
        return this;
    }
    /** Строительство нового дома **/
    public void reset() {
        house = new House();
    }
    /** Получение построенного дома **/
    public House getResult() {
        return house;
    }
}
