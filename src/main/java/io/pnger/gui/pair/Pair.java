package io.pnger.gui.pair;

public interface Pair<K, V> {

    /**
     * This method returns the key of this pair.
     *
     * @return the key
     */

    K getKey();

    /**
     * This method returns the value of this pair.
     *
     * @return the value
     */

    V getValue();

}
