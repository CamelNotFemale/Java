/** Абстрактный класс Строителя Домов **/
public interface HouseBuilder {
    HouseBuilder buildWalls(int count, String material);
    HouseBuilder buildDoors(int count, String material);
    HouseBuilder buildRoof(String material);
    HouseBuilder buildWindows(int count, String material);
    HouseBuilder buildGarage();
    HouseBuilder buildSwimmingPool();
    void reset();
}
