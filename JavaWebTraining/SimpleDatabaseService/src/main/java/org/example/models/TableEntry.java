package org.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;

@Entity
@Table
public class TableEntry {

    @Id
    @Column
    private String key;
    @Column
    private byte[] value;

    public TableEntry() {
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public byte[] getValue() {
        return value;
    }
    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Table{" +
                "key='" + key + '\'' +
                ", value=" + Arrays.toString(value) +
                '}';
    }
}
