public class RainfallAnalyst {
    // безосадочный период
    private int temp = 0;
    private String tempDateStart;
    private String tempDateEnd;
    private int rainlessPeriod = 0;
    private String rainlessDateStart;
    private String rainlessDateEnd;
    // наиболее дождливый месяц лета
    private int tempSum = 0;
    private String tempDateSum;
    private String curDay;
    private int summerPeriod = 0;
    private String summerDate;

    public int getRainlessPeriod() {
        return rainlessPeriod;
    }

    public String getRainlessDateStart() {
        return rainlessDateStart;
    }

    public String getRainlessDateEnd() {
        return rainlessDateEnd;
    }

    public int getSummerPeriod() {
        return summerPeriod;
    }

    public String getSummerDate() {
        return summerDate;
    }

    public void addData(String[] data) {
        calculateTheRainlessPeriod(data);
        String month = data[0].split("\\.")[1];
        if (month.equals("06") || month.equals("07") || month.equals("08")) {
            calculateTheRainiestSummerMonth(data);
        }
    }
    private void calculateTheRainiestSummerMonth(String[] data) {
        float precipitation;
        try {
            String str = data[23].replaceAll(",",".");
            // если хотим считать "следы осадков" за дождливые дни, следует раскомментировать следующую строку
            //if (str.equals("Следы осадков")) str = "0.01";
            precipitation = Float.parseFloat(str);
            String day = data[0].split("\\.")[0];
            String date = data[0].split(" ")[0].substring(3,10);
            String month = date.split("\\.")[0];
            if (tempSum == 0) {
                curDay = day;
                tempSum++;
                tempDateSum = date;
            }
            // если начался следующий месяц
            if (tempDateSum != null && !month.equals(tempDateSum.split("\\.")[0])) {
                throw new Exception();
            }
            if (!day.equals(curDay)) {
                tempSum++;
                curDay = day;
            }
        }
        catch (NumberFormatException e) {
            // нет осадков
        }
        catch (Exception e) {
            if (tempSum > summerPeriod) {
                summerPeriod = tempSum;
                summerDate = tempDateSum;
            }
            tempDateSum = null;
            tempSum = 0;
        }
    }
    private void calculateTheRainlessPeriod(String[] data) {
        float precipitation;
        try {
            String str = data[23].replaceAll(",",".");
            // если хотим считать "следы осадков" за дождливые дни, следует раскомментировать следующую строку
            //if (str.equals("Следы осадков")) str = "0.01";
            precipitation = Float.parseFloat(str);
            if (temp > rainlessPeriod) {
                rainlessPeriod = temp;
                rainlessDateStart = tempDateStart;
                rainlessDateEnd = tempDateEnd;
            }
            tempDateStart = null;
            temp = 0;
        }
        catch (NumberFormatException e) {
            // осадки отсутствуют, "следы осадков" также считаются за отсутствие осадков
            // в учёт идут только те дни, в которые осадков не было за весь день (с 00:00 до 21:00)
            String time = data[0].split(" ")[1];
            String date = data[0].split(" ")[0];
            if (temp == 0 && time.equals("21:00")) tempDateStart = date;
            if (time.equals("00:00") && tempDateStart != null) {
                temp++;
                tempDateEnd = date;
            }
        }
    }
}
