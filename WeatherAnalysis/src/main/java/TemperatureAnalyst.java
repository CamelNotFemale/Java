public class TemperatureAnalyst {
    private float tMin = 1000f;
    private String tMinDate;
    private float tMax = -1000f;
    private String tMaxDate;

    public void addData(String[] data) {
        float temp;
        try {
            if ((temp = Float.parseFloat(data[1])) > tMax) {
                tMax = temp;
                tMaxDate = data[0].split(" ")[0];
            }
            if ((temp = Float.parseFloat(data[1])) < tMin) {
                tMin = temp;
                tMinDate = data[0].split(" ")[0];
            }
        } catch (NumberFormatException e) {
            // данные о температуре отсутствуют
        }
    }

    public float getMin() {
        return tMin;
    }

    public String getMinDate() {
        return tMinDate;
    }

    public float getMax() {
        return tMax;
    }

    public String getMaxDate() {
        return tMaxDate;
    }
}
