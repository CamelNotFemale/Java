public class WeatherAnalyst {

    public static void main(String[] args) throws Exception
    {
        Parser parser = new Parser();
        TemperatureAnalyst temperatureAnalyst = new TemperatureAnalyst();
        RainfallAnalyst rainfallAnalyst = new RainfallAnalyst();
        String[] nextLine;
        while ((nextLine = parser.getNextLine()) != null) {
            // 1 пункт задания
            temperatureAnalyst.addData(nextLine);
            // 2 и 3 пункт задания
            rainfallAnalyst.addData(nextLine);
        }
        System.out.println("За период с 01.01.2015 по 31.12.2020");
        System.out.println("    Самый холодный день: " + temperatureAnalyst.getMinDate() + " Tmin = " + temperatureAnalyst.getMin());
        System.out.println("    Самый тёплый день: " + temperatureAnalyst.getMaxDate() + " Tmax = " + temperatureAnalyst.getMax());
        System.out.println("    Самый дождливый летний месяц: " + rainfallAnalyst.getSummerDate());
        System.out.println("    Самый длинный период без осадков: " + rainfallAnalyst.getRainlessDateStart() +
                " - " + rainfallAnalyst.getRainlessDateEnd());

    }
}
