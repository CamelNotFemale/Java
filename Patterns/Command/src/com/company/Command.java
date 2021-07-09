package com.company;
/** Абстрактная базовая комманда */
public abstract class Command {
    protected Editor editor;
    private String backup;

    Command(Editor editor) {
        this.editor = editor;
    }
    /** Создание резервной копии */
    void backup() {
        backup = editor.textField.getText();
    }
    /** Отмена команды */
    public void undo() {
        editor.textField.setText(backup);
    }
    /** Применение команды */
    public abstract boolean execute();
}
