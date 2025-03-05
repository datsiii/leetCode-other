/*
fun main() {
    val stone = readln()
    val result = StringBuilder(stone.length)

    for (i in stone.indices) {
        if ((i + 1) % 5 == 0) {
            result.append("&")
        } else {
            result.append(stone[i])
        }
    }

    println(result.toString())
}
*/
//Task 2--------------------------------------------------------------------------------------------
/*
алгоритм:
записываем значения в hashmap, где ключ - это элемент строки,
а значение - частота встречи этого элемента
после чего проходимся по массиву и записываем топ к элементов в кучу, тада!
*/
/*
import java.util.*

fun main() {
    val k = readln().toInt()
    val n = readln().toInt()
    //val numbers = readln().splitToSequence(" ").map { it.toInt() }.toList()
    val freqMap = calculateFrequency(readln().splitToSequence(" ").map { it.toInt() })
    val result = findTopKElements(freqMap, k)
        .joinToString(" ")
    println(result)
}

fun calculateFrequency(numbers: Sequence<Int>): Map<Int, Int> {
    val freqMap = HashMap<Int, Int>()
    for (num in numbers) {
        freqMap[num] = freqMap.getOrDefault(num, 0) + 1
    }
    return freqMap
}

fun findTopKElements(freqMap: Map<Int, Int>, k: Int): List<Int> {
    val minHeap = PriorityQueue<Int>(compareBy { freqMap[it]!! })
    for (num in freqMap.keys) {
        minHeap.offer(num)
        if (minHeap.size > k) {
            minHeap.poll()
        }
    }
    val result = mutableListOf<Int>()
    while (minHeap.isNotEmpty()) {
        result.add(minHeap.poll())
    }
    return result.reversed()
}
*/


//Task 3---------------------------------------------------------------------------------------------
/*
Алгоритм:
проверяем по остатку при делении на период уникальные будильники (не повтор),
из повтора берем только самое !раннее! время,
после считаем кол-во прозвеневших будильников в произвольный момент времени T
(т.к. это арифм. прог-сия, можно взять формулу a_n=a_1+d*(n-1) ==> T=t_i+X*k ==> k=(T-t_i)/X+1),
и для нахождения момента времени для K использовать бинарный поиск
 */
/*
fun main() {
    val (n, x, k) = readln().split(" ").map { it.toLong() }
    val alarmsTime = readln().split(" ").map { it.toLong() }

    val minTimes = groupAlarms(alarmsTime, x)

    val result = findAlarmTime(minTimes, x, k)
    println(result)

}

//группировка по остатку
fun groupAlarms(alarmsTime: List<Long>, x: Long): List<Long> {
    val uniqueAlarms = mutableMapOf<Long, Long>()
    for (t in alarmsTime) {
        val rem = (t % x)
        if (rem !in uniqueAlarms || t < uniqueAlarms[rem]!!) {
            uniqueAlarms[rem] = t
        }
    }
    return uniqueAlarms.values.toList()
}

//бинарный поиск
fun findAlarmTime(minTimes: List<Long>, x: Long, k: Long): Long {
    var low = 0L
    var high = minTimes.maxOrNull()!! + k * x
    while (low < high) {
        val mid = (low + high) / 2
        val totalAlarms = countAlarms(minTimes, x, mid)
        if (totalAlarms >= k) {
            high = mid
        } else low = mid + 1
    }
    return high
}


//подсчет кол-ва будильников в произвольный момент времени
fun countAlarms(minTimes: List<Long>, x: Long, time: Long): Long {
    var total = 0L
    for (t in minTimes) {
        if (t > time) continue
        total += (time - t) / x + 1
    }
    return total
}
*/

//Task 5---------------------------------------------------------------------------------------------
/*
алгоритм:
Метод заметания плоскости ("Вычислительная геометрия: алгоритмы и приложения", глава 2, с.35)
"Мы рассматриваем воображаемую горизонтальную заметающую прямую ℓ, которая опускается вниз. В не
которых точках событий заметающая прямая останавливается; ... Когда заметающая прямая останавливается
в точке события, последовательность изменяется, и мы выполняем те или иные действия, за
висящие от типа события, чтобы обновить состояние и обнаружить пересечения ...
Важны лишь точки пересечения ниже заметающей прямой, поскольку те, что находятся выше нее,
уже были обнаружены раньше ... Если выяснится, что точка пересечения расположена ниже
заметающей прямой, значит, мы нашли новую точку события."

Конкретно для задачи про метеорит:
Три события:
1. Когда левая сторона кратера встречается с заметающей прямой. Это событие обозначаем как «открытие» (a)
2. Когда правая сторона кратера проходит заметающую прямую. Это событие обозначаем как «закрытие» (r)
3. Когда встречается точечный запрос, где нам нужно узнать, находится ли точка внутри какого-либо кратера. (q)
Сортируем все события по x-координате. Если x-координаты совпадают, порядок такой: открытие, запрос, закрытие.
Далее обработка кратеров с помощью дерева отрезков (расширим красно-черные деревья для поддержки операций
над динамическими множествами промежутков) с отложенными операциями. ("Алгоритмы. Построение и Анализ" Кормен, с.375)
После координатное сжатие.
*/
/*
fun main() {
    val (h, w, n) = readln().split(" ").map { it.toInt() }
    val events = mutableListOf<Event>()
    val yCoords = mutableSetOf<Int>()

    // Определяем события
    repeat(n) {
        val (x1, y1, x2, y2) = readln().split(" ").map { it.toInt() }
        yCoords.add(y1)
        yCoords.add(y2)
        events.add(Event(x1, 'a', y1, y2))
        events.add(Event(x2, 'r', y1, y2))
    }

    // Обработка запросов
    val q = readln().toInt()
    val result = IntArray(q)
    repeat(q) { idx ->
        val (x, y) = readln().split(" ").map { it.toInt() }
        yCoords.add(y)
        // для запроса передаём y как y1, а y2 оставляем произвольным и сохраняем индекс запроса
        events.add(Event(x, 'q', y, 0, idx))
    }

    // Координатное сжатие для y
    val sortedY = yCoords.sorted()
    val yIndex = sortedY.withIndex().associate { it.value to it.index }

    // Сортировка событий по x;
    events.sortWith(compareBy({ it.x }, { it.typeOrder() }))

    // Инициализируем дерево отрезков по сжатым координатам y
    val segTree = SegmentTree(sortedY.size)

    // Обработка событий
    for (event in events) {
        when (event.type) {
            'a' -> {
                val l = yIndex[event.y1]!!
                val r = yIndex[event.y2]!!
                segTree.update(l, r, 1)
            }

            'r' -> {
                val l = yIndex[event.y1]!!
                val r = yIndex[event.y2]!!
                segTree.update(l, r, -1)
            }

            'q' -> {
                val pos = yIndex[event.y1]!!  // для запроса y хранится в поле y1
                result[event.idx] = if (segTree.query(pos) > 0) 1 else 0
            }
        }
    }

    println(result.joinToString("\n"))
}

// Событие: x-координата, тип события, y-координаты и (для запроса) индекс запроса
data class Event(val x: Int, val type: Char, val y1: Int, val y2: Int = 0, val idx: Int = -1) {
    // Порядок типов: 'a' (add) → 0, 'q' (query) → 1, 'r' (remove) → 2
    fun typeOrder() = when (type) {
        'a' -> 0
        'q' -> 1
        'r' -> 2
        else -> 3
    }
}

// Дерево отрезков с поддержкой диапазонного обновления и точечного запроса
class SegmentTree(size: Int) {
    // Вычисляем минимальную степень двойки не меньше size
    private val n: Int = run {
        var k = 1
        while (k < size) k *= 2
        k
    }
    private val tree = IntArray(2 * n)
    private val lazy = IntArray(2 * n)

    // Проталкивание накопленных (lazy) обновлений
    private fun push(node: Int, left: Int, right: Int) {
        if (lazy[node] != 0) {
            tree[node] += lazy[node]
            if (left != right) {
                lazy[node * 2] += lazy[node]
                lazy[node * 2 + 1] += lazy[node]
            }
            lazy[node] = 0
        }
    }


    fun update(l: Int, r: Int, value: Int) {
        update(1, 0, n - 1, l, r, value)
    }

    private fun update(node: Int, left: Int, right: Int, l: Int, r: Int, value: Int) {
        push(node, left, right)
        if (left > r || right < l) return
        if (l <= left && right <= r) {
            lazy[node] += value
            push(node, left, right)
            return
        }
        val mid = (left + right) / 2
        update(node * 2, left, mid, l, r, value)
        update(node * 2 + 1, mid + 1, right, l, r, value)
    }


    fun query(pos: Int): Int {
        return query(1, 0, n - 1, pos)
    }

    private fun query(node: Int, left: Int, right: Int, pos: Int): Int {
        push(node, left, right)
        if (left == right) return tree[node]
        val mid = (left + right) / 2
        return if (pos <= mid) query(node * 2, left, mid, pos)
        else query(node * 2 + 1, mid + 1, right, pos)
    }
}
*/

fun countVowels(str: String): Int {
    val res = str.groupingBy { it }.eachCount()
    var sum = 0
    res.map { if (it.key in "aeiouAEIOU") sum += it.value }
    return sum
}

class Person(val name:String, var age:Int)

fun main() {
    println(countVowels("AAASSDFGGEeddaass"))
    var str = "hi"
    println(str.toLongOrNull())
    val person:Person = Person("bob", 21)
    val name = person.name
    person.age = 30
}