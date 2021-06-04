package entitys;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Класс тестирования
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class BusTest {
    /**
     * Функция тестирования определения регистрационного номера автобуса
     */
    @Test
    public void setRegistr() {
        // формат номера - от 1 до 6 символов, первый символ обязательно буква
        Bus model = new Bus();
        String new_reg = "777AA";
        model.setRegistr(new_reg);
        assertEquals(new_reg, model.getRegistr());
    }
    /**
     * Функция тестирования определения вместительности автобуса
     */
    @Test
    public void setCapacity() {
        // допускается 0 < capacity
        Bus model = new Bus();
        int new_capacity = 0;
        assertFalse(model.setCapacity(new_capacity));
        assertEquals(new_capacity, model.getCapacity()); // значение по-умолчанию
    }
    /**
     * Функция тестирования наёма водителя для автобуса
     */
    @Test
    public void hireToDriver() {
        // при наёме работника прошлый работник(если он был) должен быть уволен
        Bus model = new Bus();
        Driver person_one = new Driver(), person_two = new Driver();
        model.hireToDriver(person_one);
        model.hireToDriver(person_two);
        assertEquals(model.getDriver().getName(), person_two.getName());
        assertNull(person_one.getBus());
    }
    /**
     * Функция тестирования преобразования к табличному формату
     */
    @Test
    public void toTableFormat() {
        // проверка на корректность преобразования к формату для занесения в таблицу DefaultTableModel
        Bus model = new Bus("A123BC", 96);
        Driver person = new Driver();
        model.hireToDriver(person);
        String[] expected = {Integer.toString(model.getId()),"A123BC", "96", person.getName(), "Отсутствует", "Ок"},
                received = model.toTableFormat();
        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], received[i]);
        }
    }
    /**
     * Функция тестирования выбора маршрута для автобуса
     */
    @Test
    public void chooseRoute() {
        // все автобусы выставленные на маршрут должны оказаться в списке объекта Маршрут и совпасть ссылки на них
        // также нельзя выставить автобус на маршрут без водителя
        Bus model1 = new Bus(),
                model2 = new Bus(),
                model3 = new Bus();
        Driver person1 = new Driver(),
                person2 = new Driver(),
                person3 = new Driver();
        Route route = new Route();
        // проверка попытки выставить автобус на маршрут без водителя
        assertFalse(model1.chooseRoute(route));
        assertNull(model1.getRoute());
        // совпадение ссылок
        model1.hireToDriver(person1); model2.hireToDriver(person2); model3.hireToDriver(person3);
        model1.chooseRoute(route); model2.chooseRoute(route); model3.chooseRoute(route);
        List<Bus> list = new ArrayList<>();
        list.add(model1); list.add(model2); list.add(model3);
        for (int i = 0; i < list.size(); ++i) {
            assertSame(list.get(i), route.getBuses().get(i));
        }
    }
    /**
     * Функция тестирования определения нарушения
     */
    @Test
    public void setViolation() {
        Bus model = new Bus();
        Driver person = new Driver();
        Route route = new Route();

        model.hireToDriver(person);
        model.chooseRoute(route);
        assertEquals(model.getRoute(), route); // успешно выставлен на маршрут

        Violation repair = new Violation("Ремонт");
        route.setViolation(repair);
        assertNull(model.getRoute()); // из-за ремонта на маршруте - все автобусы снимаются с него
    }
}