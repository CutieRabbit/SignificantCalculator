# SignificantCalculator

一款好用的基礎計算機

## 特別的地方

1. 支援有效小數

2. 使用逆波蘭＋場調度演算法，因此可以直接parse數學式

3. 支援大數，且不會因為數字過長而看不到完整的數值

4. 防呆功能

## 防bug暴力窮舉

有效小數的部分已經窮舉了以下的測試範例(會再更新)

0.01+0.99 = 1.00

0.1+0.099 = 0.2

1.1\*1.1 = 1.2

4.6666-6.444 = -1.777

(0.719+3.210)\*3.14159 = 12.34

(0.01-0.01)\*1.0 = 0

(0.001-0.001)\*1.0+5.3 = 5

((0.001-0.001)\*1.0+5.3)*1.0 = 5

((0.001-0.001)\*1.0+5.3)+1.0 = 6

414332*5.0 = 2100000

128.1+1.72+0.457 = 130.3

0*0+0+0-0.0 = 0

(0.0-1.95)\*1.0\*1.95 = -4

(1.63\*4.71+0.59)\*0.001 = 0.008

0.0625+0.9+0.05 = 1.0

4.41+3.91+1111 = 1119

0.0009+0.008+0.07+0.6 = 0.7

1.0/3.0 = 0.33

90.9+9.1 = 100.0

4+4-4+4+4 = 12

3.86+2.4 = 6.3

409.2/11.4 = 35.9

100000000-1 = 99999999

((110+220)\*0.001+41.94)\*14587 = 617000

5.13\*3.78 = 19.4

## 下載連結

見release.
