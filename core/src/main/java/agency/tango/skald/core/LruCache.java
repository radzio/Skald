package agency.tango.skald.core;

import android.support.annotation.CallSuper;

import java.util.ArrayList;
import java.util.List;

public class LruCache<K, V> extends android.util.LruCache<K, V> {
  private final List<CacheItemRemovedListener<K, V>> cacheItemRemovedListeners = new ArrayList<>();

  public LruCache(int maxSize) {
    super(maxSize);
  }

  @Override
  public void resize(int maxSize) {
    super.resize(maxSize);
  }

  @Override
  public void trimToSize(int maxSize) {
    super.trimToSize(maxSize);
  }

  @CallSuper
  @Override
  protected void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
    for (CacheItemRemovedListener<K, V> cacheItemRemovedListener : cacheItemRemovedListeners) {
      cacheItemRemovedListener.release(key, oldValue);
    }
  }

  @Override
  protected V create(K key) {
    return null;
  }

  @Override
  protected int sizeOf(K key, V value) {
    return 1;
  }

  public void addCacheItemRemovedListener(CacheItemRemovedListener<K, V> cacheItemRemovedListener) {
    cacheItemRemovedListeners.add(cacheItemRemovedListener);
  }

  public void removeCacheRemovedListener(CacheItemRemovedListener<K, V> cacheItemRemovedListener) {
    cacheItemRemovedListeners.remove(cacheItemRemovedListener);
  }

  public interface CacheItemRemovedListener<K, V> {
    void release(K key, V value);
  }
}
