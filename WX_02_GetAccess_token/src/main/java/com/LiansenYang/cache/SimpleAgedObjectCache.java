package com.LiansenYang.cache;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleAgedObjectCache implements ICache {
    private String cacheName;
    private ConcurrentHashMap cacheHashMap;

    public SimpleAgedObjectCache(String cacheName)
    {
        cacheHashMap = new ConcurrentHashMap();
        this.cacheName = cacheName;
    }

    public String getCacheName()
    {
        return cacheName;
    }

    public Object get(Object key)
    {
        CachedObject cachedObject = (CachedObject)cacheHashMap.get(key);
        if(cachedObject != null && !cachedObject.isExpired())
            return cachedObject.getObject();
        else
            return null;
    }

    public void invalidate(Object key)
    {
        cacheHashMap.remove(key);
    }

    public long getCurrentCacheSize()
    {
        return (long)cacheHashMap.size();
    }

    public void put(Object key, Object object, int minutesToLive)
    {
        CachedObject cachedObject = new CachedObject(object, key, minutesToLive);
        cacheHashMap.put(key, cachedObject);
    }

    public void put(Object key, Object object)
    {
        put(key, object, 0);
    }

    public void clear()
    {
        cacheHashMap.clear();
    }

    public void refresh()
    {
        clear();
    }


}
