interface MyHashMap<K, V> {
    val size: Int

    val isEmpty: Boolean
        get() = size == 0

    fun remove(key: K): V?
    fun containsKey(key: K): Boolean
    fun get(key: K): V?
    fun put(key: K, value: V): V?
}