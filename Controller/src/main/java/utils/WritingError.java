package utils;
/**
 * Класс ошибки добавления/редактирования записи
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class WritingError extends Exception {
    /**
     * Конструктор объекта WritingError по-умолчанию
     */
    public WritingError() {
        super("Ошибка добавления(редактирования) записи");
    }
}
