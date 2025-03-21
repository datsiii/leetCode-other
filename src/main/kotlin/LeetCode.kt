import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.max

class Solution {
    companion object {
        //88. Merge Sorted Array----------------------------------------------------------------------------------------
        fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
            var i = m - 1 //индекс nums1
            var j = n - 1 //индекс nums2
            var l = m + n - 1 //указатель
            while (j >= 0) {
                if (i < 0 || nums1[i] == nums2[j] || nums1[i] < nums2[j]) {
                    nums1[l] = nums2[j]
                    j--

                } else {
                    nums1[l] = nums1[i]
                    i--
                }
                l--
            }
        }


        //27. Remove Element--------------------------------------------------------------------------------------------
        fun removeDuplicates(nums: IntArray): Int {
            var (k, j) = arrayOf(1, 0)
            for (i in 0..<nums.size - 1) {
                if (nums[i] != nums[i + 1]) {
                    j++
                    nums[j] = nums[i + 1]
                    k++

                }
            }
            return k
        }


        //169. Majority Element-----------------------------------------------------------------------------------------
        fun majorityElement(nums: IntArray): Int {
            var candidate: Int? = null
            var cnt = 0
            for (n in nums) {
                if (cnt == 0) {
                    candidate = n
                }
                cnt = if (candidate == n) cnt + 1 else cnt - 1
            }
            return candidate!!
        }


        //189. Rotate Array---------------------------------------------------------------------------------------------
        //мое решение
        fun rotate(nums: IntArray, k: Int): Unit {
            val n = nums.size
            val k = k % n
            var f = 0
            var l = k - 1
            nums.reverse()
            for (i in 1..k / 2) {
                val temp = nums[l]
                nums[l] = nums[f]
                nums[f] = temp
                f++
                l--
            }
            f = k
            l = n - 1
            for (i in 1..(n - k) / 2) {
                val temp = nums[l]
                nums[l] = nums[f]
                nums[f] = temp
                f++
                l--
            }
        }

        //более красивое решение
        fun IntArray.rotate(start: Int = 0, end: Int = this.size - 1) {
            var left = start
            var right = end
            while (left < right) {
                this[left] = this[right].also { this[right] = this[left] }
                left++
                right--
            }
        }

        fun rotate2(nums: IntArray, k: Int) {
            val n = nums.size
            val steps = k % n
            nums.rotate()
            nums.rotate(0, steps - 1)
            nums.rotate(steps, n - 1)
        }


        //121. Best Time to Buy and Sell Stock--------------------------------------------------------------------------
        //мое решение
        fun maxProfit(prices: IntArray): Int {
            var minPrice = Int.MAX_VALUE
            var maxProfit = Int.MIN_VALUE
            prices.forEach { price ->
                minPrice = minOf(minPrice, price)
                maxProfit = maxOf(maxProfit, price - minPrice)
            }
            return maxProfit
        }

        //красивое решение
        fun maxProfit2(prices: IntArray): Int {
            prices.foldIndexed<Int>(prices[0]) { i, min, n ->
                prices[i] = n - min
                if (n < min) n else min
            }
            return prices.max() ?: 0
        }


        //122. Best Time to Buy and Sell Stock II-----------------------------------------------------------------------
        fun maxProfit3(prices: IntArray): Int {
            var sum = 0
            for (i in 0..<prices.size - 1) {
                val dif = prices[i + 1] - prices[i]
                sum = if (dif > 0) sum + dif else continue
            }
            return sum
        }

        //443. String Compression---------------------------------------------------------------------------------------
        fun compress(chars: CharArray): Int {
            var r = 0
            var w = 0
            while (r < chars.size) {
                var cnt = 0
                val char = chars[r]
                while (r < chars.size && chars[r] == char) {
                    r++
                    cnt++
                }
                chars[w++] = char
                if (cnt > 1) {
                    for (dig in cnt.toString()) {
                        chars[w++] = dig
                    }
                }

            }

            return w
        }


        //14. Longest Common Prefix-------------------------------------------------------------------------------------
        fun longestCommonPrefix(strs: Array<String>): String {
            var result = ""
            strs.sort()
            val first = strs[0]
            val last = strs[strs.size - 1]
            for (i in first.indices) {
                result = if (first[i] == last[i]) result + first[i] else break
            }
            return result
        }


        //1493. Longest Subarray of 1's After Deleting One Element-------------------------------------------------------
        fun longestSubarray(nums: IntArray): Int {
            var result = 0
            var left = 0
            var right = 0
            var zero = -1

            while (right < nums.size) {
                if (nums[right] != 1) {
                    left = zero + 1
                    zero = right
                }
                result = max(result, right - left)
                right++
            }

            return result
        }


        //2996. Smallest Missing Integer Greater Than Sequential Prefix Sum---------------------------------------------
        fun missingInteger(nums: IntArray): Int {
            var sum = 0
            var last = 0
            for (i in 1..<nums.size) {
                if (nums[i] != nums[i - 1] + 1) break
                last = i
            }
            sum = nums.slice(0..last).sum()
            while (sum in nums.toSet()) {
                sum++
            }
            return sum
        }


        //[1,3,5,6,2...]-----------------------------------------------------------------------------------------------
        fun findMinimum(nums: IntArray, k: Int): String? {
            val heap = PriorityQueue<Int>(compareByDescending { it })
            if (nums.size <= k) {
                return null
            }
            nums.forEach { n ->
                if (heap.size < k) {
                    heap.add(n)
                } else if (n < heap.peek()) {
                    heap.poll()
                    heap.add(n)
                }
            }
            return heap.joinToString()
        }


        //206. Reverse Linked List-------------------------------------------------------------------------------------
        /**
         * Example:
         * var li = ListNode(5)
         * var v = li.`val`
         * Definition for singly-linked list.
         * class ListNode(var `val`: Int) {
         *     var next: ListNode? = null
         * }
         */
        class ListNode(var `val`: Int) {
            var next: ListNode? = null
        }

        fun reverseList(head: ListNode?): ListNode? {
            var prev: ListNode? = null
            var cur = head
            while (cur != null) {
                val temp = cur.next
                cur.next = prev
                prev = cur
                cur = temp
            }
            return prev
        }


        //94. Binary Tree Inorder Traversal----------------------------------------------------------------------------
        /**
         * Example:
         * var ti = TreeNode(5)
         * var v = ti.`val`
         * Definition for a binary tree node.
         * class TreeNode(var `val`: Int) {
         *     var left: TreeNode? = null
         *     var right: TreeNode? = null
         * }
         */
        class TreeNode(var `val`: Int) {
            var left: TreeNode? = null
            var right: TreeNode? = null
        }

        fun inorderTraversal(root: TreeNode?): List<Int> {
            val result = mutableListOf<Int>()
            fun rec(root: TreeNode?) {
                if (root == null) return
                rec(root.left)
                result.add(root.`val`)
                rec(root.right)
            }
            rec(root)
            return result
        }


        //1. Two Sum---------------------------------------------------------------------------------------------------
        fun twoSum(nums: IntArray, target: Int): IntArray {
            val hmap = HashMap<Int, Int>()
            val res = intArrayOf(0, 0)
            for (i in 0..<nums.size) {
                val temp = target - nums[i]
                if (temp in hmap) {
                    res[0] = hmap[temp]!!
                    res[1] = i
                    break
                }
                hmap.put(nums[i], i)
            }
            return res
        }


        //125. Valid Palindrome-----------------------------------------------------------------------------------------
        fun isPalindrome(s: String): Boolean {
            var left = 0
            var right = s.length - 1
            while (true) {
                while (!s[left].isLetterOrDigit() and (left < right)) {
                    left++
                }
                while (!s[right].isLetterOrDigit() and (left < right)) {
                    right--
                }
                if (left >= right) break
                if ((s[left].lowercase() != s[right].lowercase())) return false
                left++
                right--
            }
            return true
        }


        //[1,5,3,5,7,2] => [1,5,3,7,2]----------------------------------------------------------------------------------
        fun removeDuplicates2(nums: IntArray): IntArray {
            val hset = HashSet<Int>()
            val result = mutableListOf<Int>()
            for (i in 0..<nums.size) {
                if (nums[i] !in hset) {
                    hset.add(nums[i])
                    result.add(nums[i])
                }
            }
            return result.toIntArray()
        }


        //21. Merge Two Sorted Lists------------------------------------------------------------------------------------
        /**
         * Example:
         * var li = ListNode(5)
         * var v = li.`val`
         * Definition for singly-linked list.
         * class ListNode(var `val`: Int) {
         *     var next: ListNode? = null
         * }
         */
        fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
            val head = ListNode(0)
            var cur = head
            var cur1 = list1
            var cur2 = list2
            while ((cur1 != null) and (cur2 != null)) {
                if (cur1!!.`val` < cur2!!.`val`) {
                    cur.next = cur1
                    cur1 = cur1.next
                } else {
                    cur.next = cur2
                    cur2 = cur2.next
                }
                cur = cur.next!!
            }
            cur.next = cur1 ?: cur2
            return head.next
        }


        //100. Same Tree------------------------------------------------------------------------------------------------
        /**
         * Example:
         * var ti = TreeNode(5)
         * var v = ti.`val`
         * Definition for a binary tree node.
         * class TreeNode(var `val`: Int) {
         *     var left: TreeNode? = null
         *     var right: TreeNode? = null
         * }
         */
        fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
            var res = true
            fun dfs(p: TreeNode?, q: TreeNode?) {
                if ((p == null) and (q == null)) return
                dfs(p?.left, q?.left)
                if (p?.`val` != q?.`val`) res = false
                dfs(p?.right, q?.right)
            }
            dfs(p, q)
            return res
        }


        //108. Convert Sorted Array to Binary Search Tree---------------------------------------------------------------
        /**
         * Example:
         * var ti = TreeNode(5)
         * var v = ti.`val`
         * Definition for a binary tree node.
         * class TreeNode(var `val`: Int) {
         *     var left: TreeNode? = null
         *     var right: TreeNode? = null
         * }
         */
        fun sortedArrayToBST(nums: IntArray): TreeNode? {
            if (nums.isEmpty()) return null
            val n = nums.size / 2
            val head = TreeNode(nums[n])
            head.left = sortedArrayToBST(nums.sliceArray(0..<n))
            head.right = sortedArrayToBST(nums.sliceArray(n + 1..<nums.size))
            return head
        }


        //141. Linked List Cycle---------------------------------------------------------------------------------------
        /**
         * Example:
         * var li = ListNode(5)
         * var v = li.`val`
         * Definition for singly-linked list.
         * class ListNode(var `val`: Int) {
         *     var next: ListNode? = null
         * }
         */
        fun hasCycle(head: ListNode?): Boolean {
            var tortoise = head
            var hare = head
            while ((hare != null) and (hare?.next != null)) {
                tortoise = tortoise?.next
                hare = hare?.next?.next
                if (tortoise == hare) return true
            }
            return false
        }


        //144. Binary Tree Preorder Traversal--------------------------------------------------------------------------
        /**
         * Example:
         * var ti = TreeNode(5)
         * var v = ti.`val`
         * Definition for a binary tree node.
         * class TreeNode(var `val`: Int) {
         *     var left: TreeNode? = null
         *     var right: TreeNode? = null
         * }
         */
        fun preorderTraversal(root: TreeNode?): List<Int> {
            val result = mutableListOf<Int>()
            fun rec(root: TreeNode?) {
                if (root == null) return
                result.add(root.`val`)
                rec(root.left)
                rec(root.right)
            }
            rec(root)
            return result
        }

        //1971. Find if Path Exists in Graph----------------------------------------------------------------------------
        fun validPath(n: Int, edges: Array<IntArray>, source: Int, destination: Int): Boolean {
            val graph = mutableMapOf<Int, MutableList<Int>>()
            for (i in 0 until n) {
                graph[i] = mutableListOf()
            }
            for ((u, v) in edges) {
                if (u != v) {
                    graph[u]?.add(v)
                    graph[v]?.add(u)
                }
            }

            fun dfs(graph: MutableMap<Int, MutableList<Int>>, start: Int, end: Int, visited: MutableSet<Int>): Boolean {
                if (start == end) {
                    return true
                }
                if (start in visited) return false
                visited.add(start)
                for (neighbor in graph[start] ?: emptyList()) {
                    if (dfs(graph, neighbor, end, visited)) {
                        return true
                    }

                }
                return false
            }
            return dfs(graph, source, destination, mutableSetOf())
        }


        //69. Sqrt(x)---------------------------------------------------------------------------------------------------
        fun mySqrt(x: Int): Int {
            var left: Long = 0
            var right: Long = x.toLong()
            while(left <= right){
                val mid = (left+right)/2
                if(mid*mid < x) left = mid + 1
                else if(mid*mid > x) right = mid - 1
                else return mid.toInt()
            }
            return right.toInt()
        }

    }


}

fun main() {
    var nums = intArrayOf(-10, -3, 0, 5, 9)
    println(Solution.sortedArrayToBST(nums))
    println(Solution.mySqrt(6))

}