package com.nettopulikkusu.util;

public class KeyValue<K, V> implements Comparable<KeyValue<K, V>> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(KeyValue<K, V> other) {
        return ((Comparable<K>) key).compareTo(other.getKey());
    }
}