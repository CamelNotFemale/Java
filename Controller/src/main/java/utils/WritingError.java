package utils;

public class WritingError extends Exception {
    public WritingError() {
        super("Ошибка добавления(редактирования) записи");
    }
}
