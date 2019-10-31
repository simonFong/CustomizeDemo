package cn.dlc.customizedemo.myapplication;

import org.junit.Test;

import java.util.ArrayList;

public class AlgorithmJavaText {
    @Test
    public void test() {
//        System.out.print(getNum(7));
//        printNum();
//        printFlower();
//        primeFactor(90);
//        countNum("  add3341233(&*(^%*&");
//        add(2, 4);
//        getAllFactor();
//        howHigh(100, 10);
//        howManyCombination();
//        whatNum();
//        getNumDay(2019, 10, 31);
        printSort(2, -85, -8);
    }

    /**
     * 题目13：输入三个整数x,y,z，请把这三个数由小到大输出。
     */
    public static void printSort(int x, int y, int z) {
        int[] array = {x, y, z};
//        bubbleSort(array);
        selectionSort(array);

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
    }

    /**
     * 选择排序
     * 按顺序取当前的数与后面的数逐一比较，如果当前的大则交换位置
     */
    public static void selectionSort(int[] array) {
        int t = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    t = array[j];
                    array[j] = array[i];
                    array[i] = t;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * 相邻的两个比较，把大的放在后面
     *
     * @param array
     */
    public static void bubbleSort(int array[]) {
        int t = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    t = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = t;
                }
            }
        }

    }

    /**
     * 题目12：输入某年某月某日，判断这一天是这一年的第几天？
     * 分析：闰年：能被4整除或整百能被400整除的年份为闰年，闰年2月有29日
     */
    public static void getNumDay(int year, int month, int day) {
        int FebruaryDays = 28;
        if (year % 4 == 0 || (year % 100 == 0 && year % 400 == 0)) {
            FebruaryDays = 29;
        }
        int[] months = {31, FebruaryDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int totalDays = 0;
        for (int i = 0; i < 12; i++) {
            if (month == i + 1) {
                totalDays = totalDays + day;
            } else if (month > i + 1) {
                totalDays = totalDays + months[i];
            } else {
                break;
            }
        }
        System.out.print("这一天是这一年的第" + totalDays + "天");

    }

    /**
     * 题目11：一个整数，它加上100后是一个完全平方数，再加上168又是一个完全平方数，请问该数是多少？
     */
    public static void whatNum() {
        for (int i = -100; i < 10000; i++) {
            if (Math.sqrt(i + 100) % 1 == 0 && Math.sqrt(i + 100 + 168) % 1 == 0) {
                System.out.print(i + ",");
            }
        }
    }

    /**
     * 题目10：有1、2、3、4四个数字，能组成多少个互不相同且一个数字中无重复数字的三位数？并把他们都输入。
     */
    public static void howManyCombination() {
        int count = 0;
        int n = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k = 1; k <= 4; k++) {
                    if (i != j && j != k && i != k) {
                        count++;
                        System.out.print(i * 100 + j * 10 + k + "，");
                    }
                    n++;
                }
            }
        }
        System.out.print("，一共" + count + "个" + ",一共计算" + n + "次");
    }

    /**
     * 题目9：一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在 第10次落地时，共经过多少米？第10次反弹多高？
     * 思路：第二次落地是需要计算反弹到落地的距离，所以要算两次。
     *
     * @param n 掉落次数
     */
    public static void howHigh(double originalHigh, int n) {
        double high = 0;
        double sumHigh = 0;
        //第几次反弹的高度
        high = originalHigh / (Math.pow(2, n));
        //反弹共经过米数
        for (int i = 1; i < n; i++) {
            sumHigh = sumHigh + originalHigh / (Math.pow(2, i)) * 2;
        }
        //总经过米数需要加上原始米数
        sumHigh = sumHigh + originalHigh;
        System.out.print("第" + n + "落地时，共经过了" + sumHigh + "米，第" + n + "次反弹" + high + "米");
    }


    /**
     * 题目1：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     *
     * @param i 多少个月
     * @return
     */
    public static int getNum(int i) {
        if ((i == 1) || (i == 2)) {
            return 1;
        } else {
            return getNum(i - 1) + getNum(i - 2);
        }
    }

    /**
     * 题目2：判断101-200之间有多少个素数，并输出所有素数。
     * 程序分析：判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除， 则表明此数不是素数，反之是素数。
     */
    public static void printNum() {
        for (int i = 101; i <= 200; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.print(i + "\n");
            }
        }
    }

    /**
     * 题目3：打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方
     */
    public static void printFlower() {
        int a, b, c;
        for (int i = 101; i < 1000; i++) {
            a = i / 100;//百位
            b = i / 10 % 10;//十位
            c = i % 10;//个位
            if (a * a * a + b * b * b + c * c * c == i) {
                System.out.print(i + "\n");
            }
        }
    }

    /**
     * 题目4：将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。   
     * 程序分析：对n进行分解质因数，应先找到一个最小的质数k，然后按下述步骤完成：   
     * (1)如果这个质数恰等于n，则说明分解质因数的过程已经结束，打印出即可。   
     * (2)如果n <> k，但n能被k整除，则应打印出k的值，并用n除以k的商,作为新的正整数你n,重复执行第一步。   
     * (3)如果n不能被k整除，则用k+1作为k的值,重复执行第一步。 
     *
     * @param num
     */
    public static void primeFactor(int num) {
        ArrayList<Object> list = new ArrayList<>();
        int n = num;
        int k = 2;

        while (n >= k) {
            if (n == k) {
                list.add(k);
                break;
            } else if (n % k == 0) {
                list.add(k);
                n = n / k;
            } else {
                k += 1;
            }
        }
        System.out.print(num + "=");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                System.out.print(list.get(i));
            } else {
                System.out.print(list.get(i) + "*");
            }

        }
    }

    /**
     * 题目5：输入两个正整数m和n，求其最大公约数和最小公倍数。
     * 在循环中，只要除数不等于0，用较大数除以较小的数，将小的一个数作为下一轮循环的大数，取得的余数作为下一轮循环的较小的数，如此循环直到较小的数的值为0，
     * 返回较大的数，此数即为最大公约数，最小公倍数为两数之积除以最大公约数。
     **/
    public static void getMaxAndMin(int a, int b) {
        int max;
        int min;
        int k = 2;
        while (a >= k && b >= k) {

        }
    }

    /**
     * 题目6：输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数。
     * 思路：Character类方法判断
     */
    public static void countNum(String string) {
        int abcNum = 0;
        int digitNum = 0;
        int whitespaceNum = 0;
        int otherNum = 0;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (Character.isLetter(aChar)) {
                abcNum++;
            } else if (Character.isDigit(aChar)) {
                digitNum++;
            } else if (Character.isWhitespace(aChar)) {
                whitespaceNum++;
            } else {
                otherNum++;
            }
        }
        System.out.print("英文字母数量为：" + abcNum + ";空格数量为：" + whitespaceNum + ";数字数量为：" + digitNum + ";其他数量为：" + otherNum);
    }

    /**
     * 题目7：求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)，几个数相加有键盘控制。
     *
     * @param n
     * @param num
     */
    public static void add(int n, int num) {
        int sum = 0;
        for (int i = 0; i < num; i++) {
            sum = sum + getAdd(n, i);
        }
        System.out.print("总和：" + sum);
    }

    private static int getAdd(int n, int i) {
        int addNum = 0;
        for (int j = 0; j <= i; j++) {
            addNum = addNum + (int) (n * Math.pow(10, j));
        }
        return addNum;
    }

    /**
     * 8题目：一个数如果恰好等于它的因子之和，这个数就称为 "完数 "。例如6=1＋2＋3.编程     找出1000以内的所有完数。
     */
    public static void getAllFactor() {
        for (int i = 1; i < 1000; i++) {
            int sum = 0;

            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    sum = sum + j;
                }
            }
            if (i == sum) {
                System.out.print(i + ",");
            }
        }
    }
}
