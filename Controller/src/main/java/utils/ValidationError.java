package utils;

public class ValidationError extends Exception {
    private String help_msg; // вариативное сообщение о требуемом формате ввода
    public ValidationError() {
        super("Недопустимое значение для поля");
        help_msg = "Некорректное значение поля";
    }
    public ValidationError(String msg) {
        super("Недопустимое значение для поля");
        help_msg = msg;
    }
    public String getHelp() {
        return help_msg;
    }
}
