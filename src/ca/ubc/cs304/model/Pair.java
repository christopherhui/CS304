package ca.ubc.cs304.model;

public class Pair<K, V> {

    private K first;
    private V second;

    public Pair(K key, V value) {
        this.first = key;
        this.second = value;
    }

    @Override
    public String toString() {
        return "{" +
                first +
                ", " + second +
                '}';
    }

    public void setFirst(K first) { this.first = first; }
    public void setSecond(V second) { this.second = second; }
    public K getFirst()   { return first; }
    public V getSecond() { return second; }
}
