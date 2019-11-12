package site.jsun999.common.utils;


import net.sf.ehcache.Element;
import site.jsun999.web.context.SpringContext;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheUtil {

    public static void deleteByName(String cacheName) {
        CacheManager cacheManager = SpringContext.getBeanByType(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        cache.removeAll();
        cache.flush();
    }

    public static void putObj(String cacheName,String key,Object value){
        CacheManager cacheManager = SpringContext.getBeanByType(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value,3600,3600);
        cache.put(element);
        cache.flush();
    }

    public static Element getObj(String cacheName,String key){
        CacheManager cacheManager = SpringContext.getBeanByType(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        return cache.get(key);
    }

    public static void deleteAll() {
        CacheManager cacheManager = SpringContext.getBeanByType(CacheManager.class);
        String[] cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            cache.removeAll();
            cache.flush();
        }
    }
}
