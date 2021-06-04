package utils;

/**
 * Класс ошибки валидности зазаваемого значения
 * @author Дмитрий Дементьев 9308
 * @version 0.1
 */
public class ValidationError extends Exception {
    /** Поле содержащее вариативное сообщение о требуемом формате ввода */
    private String help_msg;
    /**
     * Конструктор объекта ValidationError по-умолчанию
     */
    public ValidationError() {
        super("Недопустимое значение для поля");
        help_msg = "Некорректное значение поля";
    }
    /**
     * Конструктор - создание нового объекта ValidationError
     * @param msg - вариативное сообщение о требуемом формате ввода
     */
    public ValidationError(String msg) {
        super("Недопустимое значение для поля");
        help_msg = msg;
    }
    /**
     * Функция получения информации о требуемом формате ввода
     * @return сообщение о требуемом формате ввода
     */
    public String getHelp() {
        return help_msg;
    }
}
