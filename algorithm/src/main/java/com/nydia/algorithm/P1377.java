package com.nydia.algorithm;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/9 00:06
 * @Description: com.nydia.algorithm.P1377
 * <p>Office 20 / P1377</p>
 */
public class P1377 {

    public static void main(String[] args) {
        System.out.println(isNumber("1.1.11"));;
    }

    public static boolean isNumber(String s) {
        s = s.trim();
        if(s.equals("") || s.equals(".")){
            return false;
        }
        String[] intArr = new String[]{"0","1","2","3","4","5","6","7","8","9","."};
        int cNum = 0;
        for(int j = 0; j < s.length(); j ++){
            String cs = String.valueOf(s.charAt(j));
            if(cs.equals("."))
                cNum += 1;
            boolean b = false;
            for(int i = 0; i < intArr.length; i++){
                if(intArr[i].equals(cs)){
                    b = true;
                }
            }
            if(!b){
                return false;
            }
            if(cNum > 1){
                return false;
            }
        }
        return true;
    }
}
