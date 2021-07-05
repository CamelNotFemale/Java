package House;

public class Windows {
    private int count;
    private String material;

    public Windows(int _count, String _material) {
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
