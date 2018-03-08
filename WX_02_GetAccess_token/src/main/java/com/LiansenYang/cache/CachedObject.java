package com.LiansenYang.cache;

import java.util.Calendar;
import java.util.Date;


public class CachedObject {
    public Object object;
    private Date dateofExpiration;
    private Object identifier;
    //缓存过期时间
    private Date lastAccessTime;
    private long numAccess;

    public CachedObject(Object obj, Object key, int minutesToLive)
    {
        dateofExpiration = null;
        identifier = null;
        lastAccessTime = new Date();
        numAccess = 1L;
        object = obj;
        identifier = key;
        lastAccessTime = new Date();
        if(minutesToLive != 0)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(lastAccessTime);
            cal.add(12, minutesToLive);
            dateofExpiration = cal.getTime();
        }
    }

    public Object getObject()
    {
        return object;
    }


    public boolean isExpired()
    {
        return dateofExpiration != null && dateofExpiration.before(new Date());
    }

}
