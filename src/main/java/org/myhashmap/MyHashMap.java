package org.myhashmap;

import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {
    private LinkedList<Entry<K, V>>[] buckets;
    private int size;
    private int capacity;

    private static final float loadFactor = 0.75f;

    private static class Entry<K, V> implements Map.Entry<K, V>{
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    public MyHashMap() {
        size = 0;
        capacity = 16;
        buckets = new LinkedList[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        var index = findIndex(key);

        if (buckets[index] == null) {
            return false;
        }

        for (var bucket : buckets[index]) {
            if (Objects.equals(bucket.key, key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (var bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (var entry : bucket) {
                if (Objects.equals(entry.value, value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        var index = findIndex(key);

        if (buckets[index] == null) {
            return null;
        }

        for (var bucket : buckets[index]) {
            if (Objects.equals(bucket.key, key)) {
                return bucket.value;
            }
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        if (checkFullCapacity()) {
            resize();
        }

        var index = findIndex(key);

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        for (var bucket : buckets[index]) {
            if (Objects.equals(bucket.key, key)) {
                var oldValue = bucket.value;
                bucket.value = value;
                return oldValue;
            }
        }

        buckets[index].addLast(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        var index = findIndex(key);

        if (buckets[index] == null) {
            return null;
        }

        var iterator = buckets[index].iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (Objects.equals(entry.key, key)) {
                var oldValue = entry.value;
                iterator.remove();
                size--;
                return oldValue;
            }
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null){
            throw new NullPointerException();
        }
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        size = 0;
        buckets = new LinkedList[capacity];
    }

    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (var bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (var entry : bucket) {
                res.add(entry.key);
            }
        }

        return res;
    }

    @Override
    public Collection<V> values() {
        var res = new ArrayList<V>();
        for (var bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            for (var entry : bucket) {
                res.add(entry.value);
            }
        }

        return res;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> res = new HashSet<>();
        for (var bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            res.addAll(bucket);
        }

        return res;
    }


    private int findIndex(Object key) {
        var hashCode = 0;
        if (key != null) {
            hashCode = Math.abs(key.hashCode());
        }

        return hashCode % capacity;
    }

    private boolean checkFullCapacity() {
        return size >= capacity * loadFactor;
    }

    private void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        int oldCapacity = capacity;

        capacity = oldCapacity * 2;
        clear();
        for (int i = 0; i < oldCapacity; i++) {
            if (oldBuckets[i] != null) {
                for (var entry : oldBuckets[i]) {
                    put(entry.key, entry.value);
                }
            }
        }
    }
}
