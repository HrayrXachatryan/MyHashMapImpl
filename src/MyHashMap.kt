interface MyHashMap<K, V> {
    val size: Int
    val isEmpty: Boolean

    fun remove(key: K): V?
    fun containsKey(key: K): Boolean
    operator fun get(key: K): V?
    operator fun set(key: K, value: V)
    fun put(key: K, value: V): Boolean
}