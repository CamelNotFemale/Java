package entitys;

public class Violation {
    public Violation(Bus bus, String violation_descript) {
        System.out.println("Проблемка с " + bus.getRegistr() + ": " + violation_descript);
        /*
        что-то типа:
        bus.setVialation(new Vialation(violation_descript));
        * */
    }
    public Violation(Driver driver, String violation) {

    }
    public Violation(Route route, String violation) {

    }
}
