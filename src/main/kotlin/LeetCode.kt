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


        //122. Best Time to Buy and Sell Stock II
        fun maxProfit3(prices: IntArray): Int {
            var sum = 0
            for (i in 0..<prices.size - 1) {
                val dif = prices[i + 1] - prices[i]
                sum = if (dif > 0) sum + dif else continue
            }
            return sum
        }

        //443. String Compression
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


        //14. Longest Common Prefix
        fun longestCommonPrefix(strs: Array<String>): String {
            var result = ""
            strs.sort()
            val first = strs[0]
            val last = strs[strs.size-1]
            for (i in first.indices){
                result = if (first[i] == last[i]) result + first[i] else break
            }
            return result
        }


    }

}

fun main() {
    var strs = arrayOf("flower","flow","flight")
    println(Solution.longestCommonPrefix(strs))
    strs = arrayOf("dog","racecar","car")
    println(Solution.longestCommonPrefix(strs))

}