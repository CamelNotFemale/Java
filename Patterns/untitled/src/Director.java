public class Director {
    /** Поле, хранящее исполняющего проект строителя **/
    private HouseBuilder builder;
    /** создание "прораба" для выдачи указаний к постройке различных проектов домов **/
    public Director(HouseBuilder _builder) {
        builder = _builder;
    }
    /** Стандартный деревянный дом **/
    public void makeStandartHouse() {
        builder.reset();
        builder.buildWalls(4, "Wood").buildDoors(1, "Wood").buildRoof("Wood").buildWindows(2, "Glass");
    }
    /** Дом с премиальной отделкой, гаражом и бассейном **/
    public void makeLuxeryHouse() {
        builder.reset();
        builder.buildWalls(4, "Stone, Iron and Wood").buildDoors(3, "Iron").buildRoof("Tile").buildWindows(4, "Premium Glass").buildGarage().buildSwimmingPool();
    }
}
