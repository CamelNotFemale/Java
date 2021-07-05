package House;

public class Doors {
    private int count;
    private String material;

    public Doors(int _count, String _material) {
        count = _count;
        material = _material;
    }

    public String getMaterial() {
        return material;
    }
    public int getCount() {
        return count;
    }
}
