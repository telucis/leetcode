package medium.hashMap;

/**
 * @author karl.wy
 * @date 2019/05/05
 *
 * 最大交换
 *
    给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。

    示例 1 :

    输入: 2736
    输出: 7236
    解释: 交换数字2和数字7。
    示例 2 :

    输入: 9973
    输出: 9973
    解释: 不需要交换。
    注意:

    给定数字的范围是 [0, 108]

 */
public class maximum_swap_670 {
    public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] buckets = new int[10];
        for (int i=0; i<digits.length; i++) {
            buckets[digits[i]-'0'] = i;
        }
        for (int i=0; i<digits.length; i++) {
            for (int k=9; k>digits[i]-'0'; k--) {
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }
        return num;
    }
}
