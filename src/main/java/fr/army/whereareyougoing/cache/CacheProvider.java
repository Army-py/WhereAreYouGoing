package fr.army.whereareyougoing.cache;

import fr.army.whereareyougoing.cache.impl.ServerCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheProvider {

    private final Map<Class<? extends AbstractCache<?, ?>>, AbstractCache<?, ?>> repositoryCache;

    public CacheProvider() {
        this.repositoryCache = new ConcurrentHashMap<>();
        initCaches();
    }

    public void unload() {
        repositoryCache.values().forEach(AbstractCache::clearCache);
    }

    public <T extends AbstractCache<?, ?>> T getCache(Class<T> cacheClass) {
        return cacheClass.cast(repositoryCache.get(cacheClass));
    }

    private void initCaches() {
        repositoryCache.put(ServerCache.class, new ServerCache());
    }
}
