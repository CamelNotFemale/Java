package org.example.models;

import java.util.Date;

/** Запись в таблице */
public class TableEntry {
    private byte[] value;

    public TableEntry(byte[] value) {
        this.value = value;
    }

    public String getValue() {
        return new String(value);
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
