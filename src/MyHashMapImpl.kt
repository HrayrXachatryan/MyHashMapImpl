class MyHashMapImpl<K, V>(
    initialCapacity: Int = 16
) : MyHashMap<K, V> {

    private class Node<K, V>(
        val key: K,
        var value: V,
        val hash: Int,
        var next: Node<K, V>? = null
    )

    private var buckets: Array<Node<K, V>?> = arrayOfNulls(initialCapacity)
    private val loadFactor = 0.75
    override var size: Int = 0
        private set

    override val isEmpty: Boolean
        get() = size == 0


    private fun indexFor(hash: Int): Int {
        return hash and (buckets.size - 1)
    }

    private fun resize() {
        val oldBuckets = buckets
        val newCapacity = oldBuckets.size * 2
        val newBuckets = arrayOfNulls<Node<K, V>?>(newCapacity)

        for (i in oldBuckets.indices) {
            var current = oldBuckets[i]

            while (current != null) {
                val next = current.next

                val newIndex = current.hash and (newCapacity - 1)

                current.next = newBuckets[newIndex]
                newBuckets[newIndex] = current

                current = next
            }
        }

        buckets = newBuckets
    }

    override fun put(key: K, value: V): Boolean {
        val result = putInternal(key, value)
        if (size > buckets.size * loadFactor) {
            resize()
        }
        return result
    }

    private fun putInternal(key: K, value: V): Boolean {
        val hash = key.hashCode()
        val index = indexFor(hash)
        var current = buckets[index]

        if (current == null) {
            buckets[index] = Node(key, value, hash)
            size++
            return true
        } else {
            while (current != null) {
                if (current.key == key) {
                    current.value = value
                    return false
                }
                if (current.next == null) {
                    current.next = Node(key, value, hash)
                    size++
                    return true
                }
                current = current.next
            }
        }
        return false
    }

    override operator fun set(key: K, value: V) {
        put(key, value)
    }

    override operator  fun get(key: K): V? {
        val hash = key.hashCode()
        val index = indexFor(hash)
        var current = buckets[index]

        while (current != null) {
            if (current.key == key) {
                return current.value
            }
            current = current.next
        }
        return null
    }

    override fun remove(key: K): V? {
        val hash = key.hashCode()
        val index = indexFor(hash)
        var prev: Node<K, V>? = null
        var current = buckets[index]

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    buckets[index] = current.next
                } else {
                    prev.next = current.next
                }
                size--
                return current.value
            }
            prev = current
            current = current.next
        }

        return null
    }

    override fun containsKey(key: K): Boolean {
        return get(key) != null
    }

}