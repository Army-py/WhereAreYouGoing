package fr.army.whereareyougoing.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractCache<K, V> {

    private final Cache<K, V> cache;

    public AbstractCache(){
        this.cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .build();
    }

    @Nullable
    public V getCachedObject(K key) {
        return cache.getIfPresent(key);
    }

    public void putCachedObject(K key, V cachedObject) {
        cache.put(key, cachedObject);
    }

    public void removeCachedObject(K key) {
        cache.invalidate(key);
    }

    public void clearCache() {
        cache.invalidateAll();
    }

    public boolean containsCachedObject(K key) {
        return cache.asMap().containsKey(key);
    }
}
