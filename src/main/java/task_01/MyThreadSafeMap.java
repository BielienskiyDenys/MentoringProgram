package task_01;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyThreadSafeMap<K, V> implements Map<K, V> {
    private final Map<K, V> innerMap;

    public MyThreadSafeMap(Map<K, V> innerMap) {
        this.innerMap = innerMap;
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return innerMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        synchronized (this) {
            return innerMap.put(key, value);
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (this) {
            return innerMap.remove(key);
        }
    }

    @Override
    public void putAll(Map m) {
        synchronized (this) {
            innerMap.putAll(m);
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            innerMap.clear();
        }
    }

    @Override
    public Set keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection values() {
        return innerMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return innerMap.entrySet();
    }
}
