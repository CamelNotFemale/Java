package House;

public class House {
    private Walls walls;
    private Doors doors;
    private Roof roof;
    private Windows windows;
    private boolean garage = false;
    private boolean swimmingPool = false;

    public void setWalls(int count, String material) {
        walls = new Walls(count, material);
    }
    public void setDoors(int count, String material) {
        doors = new Doors(count, material);
    }
    public void setWindows(int count, String material) {
        windows = new Windows(count, material);
    }
    public void setRoof(String material) {
        roof = new Roof(material);
    }
    public void addGarage() {
        garage = true;
    }
    public void addSwimmingPool() {
        swimmingPool = true;
    }
    /** Получение информации о доме **/
    public void getInfo() {
        System.out.println("House info:");
        System.out.println("  " + walls.getCount() + " walls made of " + walls.getMaterial());
        System.out.println("  " + doors.getCount() + " doors made of " + doors.getMaterial());
        System.out.println("  " + "roof made of " + roof.getMaterial());
        System.out.println("  " + windows.getCount() + " windows made of " + windows.getMaterial());
        if (garage) System.out.println("  " + "with garage");
        if (swimmingPool) System.out.println("  " + "with swimming pool");
    }
}
