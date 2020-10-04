package com.example.significantcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class ReversePolish {
    public static int getIntegerLenght(String a){
        BigDecimal decimal = new BigDecimal(a);
        a = decimal.abs().toPlainString();
        return a.contains(".") ? a.split("\\.")[0].length() : a.length();
    }
    public static int getSignificantLenght(String a){
        boolean zero = true;
        int count = 0;
        for(char c : a.toCharArray()){
            if(c != '.' && c != '0') zero = false;
            if(zero) continue;
            if(c == '.') continue;
            count++;
        }
        return count;
    }
    public static String padleft(BigDecimal d, int count){
        if(d.doubleValue() == 0){
            System.out.println("Goodbye Zero");
            return "0";
        }
        String str = "";
        String data = d.toPlainString();
        System.out.println("Padleft: " + data + " Count: " + count);
        boolean zero = true;
        for(int i = 0, j = 0; j < count && i < data.length(); i++){
            str += data.charAt(i);
            if(data.charAt(i) == '-') continue;
            if(data.charAt(i) != '.' && data.charAt(i) != '0') zero = false;
            if(zero) continue;
            if(data.charAt(i) == '.') continue;
            j++;
        }
        while(data.length() < count){
            data += "0";
        }
        return str;
    }
    public static int SignificantIndex(BigDecimal d, int count){
        return padleft(d, count).contains(".") ? padleft(d, count).split("\\.")[1].length() : 0;
    }
    public static int firstSignificantIndex(BigDecimal d){
        String str = d.toPlainString();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != '0') return i+1;
        }
        return str.length();
    }
    public static int findVaildLength(BigDecimal d, int la){
        boolean find = false;
        int count = 0, ans = 0;
        boolean zero = true;
        String sd = d.toPlainString();
        for(int i = 0; count < la && i < sd.length(); i++){
            if(sd.charAt(i) != '0' && sd.charAt(i) != '.'){
                zero = false;
            }
            if(sd.charAt(i) == '.'){
                find = true;
            }
            if(!zero && sd.charAt(i) != '.') {
                count++;
            }
            if(find && sd.charAt(i) != '.'){
                ans++;
            }
        }
        return ans;
    }
    public static int getOperatorSignificantLength(BigDecimal a, BigDecimal b, int la ,int lb, boolean flag, String oper){
        if(flag) {
            int sl = Math.min(la, lb);
            return sl;
        }else{
            int ma = 0;
            String sa = a.toPlainString();
            String sb = b.toPlainString();
            int ia = getIntegerLenght(sa);
            int ib = getIntegerLenght(sb);
            if(a.doubleValue() != 0 || b.doubleValue() != 0) {
                if(oper.equals("+")) {
                    if (Math.floor(a.doubleValue() + b.doubleValue()) != 0) {
                        ma = String.valueOf((int)Math.floor(Math.abs(a.doubleValue() + b.doubleValue()))).length();
                    }
                }else if(oper.equals("-")){
                    if (Math.floor(a.doubleValue() - b.doubleValue()) != 0) {
                        ma = String.valueOf((int)Math.floor(Math.abs(a.doubleValue() - b.doubleValue()))).length();
                    }
                }
            }
            int da = findVaildLength(a, la);
            int db = findVaildLength(b, lb);
            System.out.println(ia + " " + da + " " + la);
            System.out.println(ib + " " + db + " " + lb);
            System.out.println(ma);
            return Math.max(getIntegerLenght(a.add(b).toPlainString()), ma + Math.min(da, db));
        }
    }
    public static String result(String postfix) {
        if (!postfix.contains(" ")) return postfix;
        String[] array = postfix.split(" ");
        Stack<BigDecimal> stack = new Stack<BigDecimal>();
        Stack<Integer> significantLenghtStack = new Stack<>();
        BigDecimal la = new BigDecimal(0), lb = new BigDecimal(0);
        String lo = "";
        for (String str : array) {
            if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                BigDecimal a = stack.peek();
                int sa = significantLenghtStack.peek();
                stack.pop();
                significantLenghtStack.pop();
                BigDecimal b = stack.peek();
                int sb = significantLenghtStack.peek();
                stack.pop();
                significantLenghtStack.pop();
                lo = str;
                la = a;
                lb = b;
                boolean flag = false;
                if (str.equals("+")) {
                    stack.push(b.add(a));
                } else if (str.equals("-")) {
                    stack.push(b.subtract(a));
                } else if (str.equals("*")) {
                    stack.push(b.multiply(a));
                    flag = true;
                } else {
                    stack.push(b.divide(a, 3000, RoundingMode.FLOOR));
                    flag = true;
                }
                int fs = getOperatorSignificantLength(la, lb, sa, sb, flag, str);
                if (stack.peek().doubleValue() == 0) {
                    System.out.println("Detect zero " + stack.peek());
                    fs = 1;
                    stack.pop();
                    stack.push(new BigDecimal(0));
                }
                System.out.println(String.format("a=%s, b=%s, oper=%s, la=%d, lb=%d, signifi=%d", a.toPlainString(), b.toPlainString(), str, sa, sb, fs));
                significantLenghtStack.push(fs);
            } else {
                stack.push(new BigDecimal(str));
                significantLenghtStack.push(getSignificantLenght(str));
            }
        }
        String result;
        if (lo.equals("*") || lo.equals("/")) {
            BigDecimal ss;
            if (lo.equals("*")) {
                ss = lb.multiply(la);
            } else {
                ss = lb.divide(la, 30, RoundingMode.FLOOR);
            }
            int sl = significantLenghtStack.peek();
            System.out.println(String.format("First sign. index: %d", firstSignificantIndex(ss)));
            if (getIntegerLenght(ss.toPlainString()) >= sl && firstSignificantIndex(ss) <= getIntegerLenght(ss.toPlainString())) {
                int tl = getIntegerLenght(ss.toPlainString());
                BigDecimal pow = new BigDecimal(10).pow(tl);
                ss = ss.divide(pow, sl, RoundingMode.HALF_UP);
                ss = ss.multiply(pow);
                ss = ss.setScale(0, RoundingMode.FLOOR);
                result = ss.toPlainString();
            } else {
                ss = ss.setScale(SignificantIndex(ss, sl), RoundingMode.HALF_UP);
                result = padleft(ss, sl);
            }
        } else {
            BigDecimal ss;
            if (lo.equals("+")) {
                ss = lb.add(la);
            } else {
                ss = lb.subtract(la);
            }
            int sl = significantLenghtStack.peek();
            if (getIntegerLenght(ss.toPlainString()) >= sl && firstSignificantIndex(ss) <= getIntegerLenght(ss.toPlainString())) {
                int tl = getIntegerLenght(ss.toPlainString());
                ss = ss.setScale(0, RoundingMode.FLOOR);
                BigDecimal pow = new BigDecimal(10).pow(tl);
                ss = ss.divide(pow, sl, RoundingMode.HALF_UP);
                ss = ss.multiply(pow);
                ss = ss.setScale(0, RoundingMode.FLOOR);
                result = ss.toPlainString();
            } else {
                ss = ss.setScale(SignificantIndex(ss, sl), RoundingMode.HALF_UP);
                result = padleft(ss, sl);
            }
        }
        return result;
    }
}