package org.example.utils;

import java.util.Date;

public class TtlWrapper<T> {
    /** ttl по-умолчанию - один час */
    private static final long DEFAULT_TTL = 60*60;
    private T entity;
    private long timeToLife;

    public TtlWrapper(T entity) {
        this.entity = entity;
        this.timeToLife = new Date().getTime() + DEFAULT_TTL;
    }
    public TtlWrapper(T entity, long ttl) {
        this.entity = entity;
        this.timeToLife = new Date().getTime() + ttl;
    }

    public T getEntity() {
        return entity;
    }

    public long getTimeToLife() {
        return timeToLife;
    }
}
