package org.nekobehemoth.hw04;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

public class CustomHashMap<K, V> implements Map<K, V> {

    private int size;
    private int deletedItemsAmount;
    private CustomEntry<K,V>[] entries;
    private static final int DEFAULT_SIZE = 10;
    private static final double DEFAULT_LOAD = 0.75;
    private int buffered_size;

    public CustomHashMap() {
        this.entries = new CustomEntry[DEFAULT_SIZE];
        buffered_size = DEFAULT_SIZE;
        deletedItemsAmount = 0;
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
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        for (CustomEntry<K, V> entry : entries) {
            if (entry != null
                    && !entry.isDeleted()
                    && entry.getValue().equals(value)) return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        CustomEntry<K,V> found = getEntry(key);
        return found == null || found.isDeleted()  ? null : found.getValue();
    }

    private CustomEntry<K,V> getEntry(Object key) {
        int h1 = first_hash((K) key, buffered_size);
        int h2 = second_hash((K) key, buffered_size);
        int i = 0;
        while (i < buffered_size) {
            if (entries[h1] != null
                    && !entries[h1].isDeleted()
                    && entries[h1].getKey().equals(key)) {
                return entries[h1];
            }
            else h1 = (h1 + h2) % buffered_size;
            i++;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        CustomEntry<K, V> newEntry = new CustomEntry<>(key, value);
        int futureSize = size + 1;
        if (futureSize > ((int) buffered_size * DEFAULT_LOAD)) resize(futureSize);
        V result = putEntry(newEntry);
        return result;
    }

    private V putEntry(CustomEntry<K,V> entry) {
        int h1 = first_hash(entry.getKey(), buffered_size);
        int h2 = second_hash(entry.getKey(), buffered_size);
        int i = 0;
        while (i < buffered_size) {
            if (entries[h1] == null) {
                entries[h1] = entry;

                size++;
                return null;
            }
            if (entries[h1].getKey().equals(entry.getKey())) {
                V oldValue = entries[h1].getValue();
                entries[h1] = entry;
                return oldValue;
            }
            else h1 = (h1 + h2) % buffered_size;
            i++;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        if ((size + deletedItemsAmount) > 2 * size) {
            rehash();
        }
        CustomEntry<K,V> found =  getEntry(key);
        if (found != null) {
            found.setDeleted(true);
            size--;
            deletedItemsAmount++;
            return found.getValue();
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        int futureSize = size + m.size();
        if ( (futureSize + 1) > ((int) buffered_size * DEFAULT_LOAD)) resize(futureSize);
        m.forEach((k, v) -> putEntry(new CustomEntry<>(k, v)));
    }

    @Override
    public void clear() {
        CustomEntry<K,V>[] ent = entries;
        if (size > 0 && ent != null ) {
            for (int i = 0; i < size; i++) {
                ent[i] = null;
            }
            size = 0;
        }
    }

    @Override
    public Set<K> keySet() {
        return Arrays.stream(entries).filter(entry -> entry != null && !entry.isDeleted()).map(CustomEntry::getKey).collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return Arrays
                .stream(entries)
                .filter(entry -> entry != null && !entry.isDeleted())
                .map(CustomEntry::getValue).toList();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Arrays.stream(entries).filter(entry -> entry != null && !entry.isDeleted()).collect(Collectors.toSet());
    }


    private int first_hash(K entryKey, int buffered_size) {
        return main_hash( entryKey, buffered_size,  buffered_size - 1);
    }

    private int second_hash(K entryKey, int buffered_size) {
        return main_hash( entryKey, buffered_size,  buffered_size + 1);
    }


    private int main_hash(K entryKey, int size, int key) {
        int hash_result = 31;
        hash_result = (key * hash_result + Math.abs(entryKey.hashCode())) % size;
        hash_result = (hash_result * 2 + 1) % size;
        return hash_result;
    }

    private void resize(int futureSize) {
        size = 0;
        buffered_size *= 2;
        if (futureSize >= buffered_size) buffered_size *= 2;
        CustomEntry<K,V>[] old_array = entries;
        entries = new CustomEntry[buffered_size];
        for (CustomEntry<K, V> customEntry : old_array) {
           if (customEntry != null && !customEntry.isDeleted())  putEntry(customEntry);
        }
        deletedItemsAmount = 0;
    }

    private void rehash() {
        size = 0;
        CustomEntry<K,V>[] old_array = entries;
        entries = new CustomEntry[buffered_size];

        for (CustomEntry<K, V> customEntry : old_array) {
            if (customEntry != null && !customEntry.isDeleted())  putEntry(customEntry);
        }
        deletedItemsAmount = 0;
    }


    static class CustomEntry<K, V> implements Entry<K, V> {
        @Setter
        @Getter
        private boolean deleted = false;
        private final K key;
        private V value;

        CustomEntry(K key, V value) {
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
            return this.value = value;
        }


        @Override
        public int hashCode() {
            int hash = 42;
            hash = 31 * hash + (key == null ? 0 : key.hashCode());
            hash = 31 * hash + (value == null ? 0 : value.hashCode());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != this.getClass()) return false;
            CustomEntry<?, ?> compare = (CustomEntry<?, ?>) obj;
            return (Objects.equals(this.key, compare.key) && Objects.equals(this.value, compare.value));
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }

    }
}
