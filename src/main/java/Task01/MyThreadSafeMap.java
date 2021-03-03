package Task01;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class  MyThreadSafeMap<K,V> implements Map<K,V> {
    private final Map<K,V> innerMap;
    private final Object monitor;

    public MyThreadSafeMap(Map<K, V> innerMap) {
        this.innerMap = innerMap;
        this.monitor = this;
    }

    @Override
    public int size() {
        synchronized (monitor) {
            return innerMap.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (monitor) {
            return innerMap.isEmpty();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        synchronized (monitor) {
            return innerMap.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        synchronized (monitor) {
            return innerMap.containsValue(value);
        }
    }

    @Override
    public V get(Object key) {
        synchronized (monitor) {
            return innerMap.get(key);
        }
    }

    @Override
    public V put(K key, V value) {
        synchronized (monitor) {
            return innerMap.put(key,value);
        }
    }

    @Override
    public V remove(Object key) {
        synchronized (monitor) {
            return innerMap.remove(key);
        }
    }

    @Override
    public void putAll(Map m) {
        synchronized (monitor) {
            innerMap.putAll(m);
        }
    }

    @Override
    public void clear() {
        synchronized (monitor) {
            innerMap.clear();
        }
    }

    @Override
    public Set keySet() {
        synchronized (monitor) {
            return innerMap.keySet();
        }
    }

    @Override
    public Collection values() {
        synchronized (monitor) {
            return innerMap.values();
        }
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        synchronized (monitor) {
            return innerMap.entrySet();
        }
    }
}
