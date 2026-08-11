//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val map: MyHashMap<String, Int> = MyHashMapImpl(initialCapacity = 16)
    println("isEmpty: ${map.isEmpty}")
    println("size: ${map.size}")
    map.put("fshj",21)
    println(map.get("fshj"))
    println(map.containsKey("jfkso"))
    println(map.containsKey("fshj"))
}