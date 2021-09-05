package org.example.repositories;

import org.example.models.TableEntry;
import org.example.utils.TtlWrapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class TableRepository {
    /** наше хранилище в оперативной памяти */
    private final HashMap<String, TtlWrapper<TableEntry>> data;

    public TableRepository() {
        data = new HashMap<>();
    }

    public TableEntry get(String key) {
        TtlWrapper<TableEntry> record = data.get(key);
        // если срок хранения истёк, то удаляем запись
        if (record != null && new Date().getTime() >= record.getTimeToLife()) {
            data.remove(key);
            return null;
        }
        return data.get(key).getEntity();
    }

    public boolean set(String key, TableEntry entry) {
        if (key == null || entry == null) return false;
        data.put(key, new TtlWrapper<>(entry));
        return true;
    }

    public boolean set(String key, TableEntry entry, long ttl) {
        if (key == null || entry == null) return false;
        data.put(key, new TtlWrapper<>(entry, ttl));
        return true;
    }

    public TableEntry remove(String key) {
        return data.remove(key).getEntity();
    }

    public List<String[]> findAll() {
        List<String[]> res = new ArrayList<>();

        for(String key: data.keySet()) {
            TtlWrapper<TableEntry> entryWithTtl = data.get(key);
            TableEntry entity = this.get(key);
            if (entity != null) {
                String[] str = new String[3];
                str[0] = key;
                str[1] = entity.getValue().toString();
                str[3] = String.valueOf(entryWithTtl.getTimeToLife());
                res.add(str);
            }
        }

        return res;
    }
}