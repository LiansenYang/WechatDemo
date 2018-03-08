package com.LiansenYang.cache;

public interface ICache {

    public abstract String getCacheName();

    public abstract Object get(Object key);

    public abstract void invalidate(Object key);

    public abstract void refresh();

    public abstract long getCurrentCacheSize();

    public abstract void  put(Object key, Object object, int minutesToLive);

    public abstract void  put(Object key, Object object);

    public abstract void clear();
}
