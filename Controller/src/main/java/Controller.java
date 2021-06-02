import app.*;
import org.apache.log4j.Logger;

public class Controller {

    public static final Logger logger = Logger.getLogger(Controller.class);

    public static void main(String[] args) {
        logger.info("Start app");
        // Создание и отображение экранной формы
        App controller= new App();
        logger.info("Finish app");
    }
}