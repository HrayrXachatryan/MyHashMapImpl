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

    override fun put(key: K, value: V): V? {
        val result = putInternal(key, value)
        if (size > buckets.size * loadFactor) {
            resize()
        }
        return result
    }

    private fun putInternal(key: K, value: V): V? {
        val hash = key.hashCode()
        val index = indexFor(hash)
        var current = buckets[index]

        if (current == null) {
            buckets[index] = Node(key, value, hash)
            size++
            return null
        } else {
            while (current != null) {
                if (current.key == key) {
                    val oldValue = current.value
                    current.value = value
                    return oldValue
                }
                if (current.next == null) {
                    current.next = Node(key, value, hash)
                    size++
                    return null
                }
                current = current.next
            }
        }
        return null
    }


    override fun get(key: K): V? {
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

        val first = buckets[index] ?: return null

        if (first.key == key) {
            buckets[index] = first.next
            size--
            return first.value
        }

        var prev = first
        var current = first.next

        while (current != null) {
            if (current.key == key) {
                prev.next = current.next
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