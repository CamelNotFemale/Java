package org.example.repositories;

import org.example.models.TableEntry;
import org.example.utils.TtlWrapper;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<List<String>> findAll() {
        List<List<String>> res = new ArrayList<>();

        Set<String> keySet = data.keySet();
        String[] keys = keySet.toArray(new String[keySet.size()]);
        for(String key: keys) {
            TableEntry entity = this.get(key);
            if (entity != null) {
                TtlWrapper<TableEntry> entryWithTtl = data.get(key);
                List<String> str = new ArrayList<>();
                str.add(key);
                str.add(entity.getValue());
                str.add(new Date(entryWithTtl.getTimeToLife()).toString());
                res.add(str);
            }
        }

        return res;
    }
}