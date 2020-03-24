package com.song.test.utils;

public class Utils {

    private static int count=0;

    /** 输出二维数组 */
    public static void outPutTwoArrayToString(Object[][] arrayCellValue){
        for (int i = 0; i<arrayCellValue.length;i++){
            for (int j = 0;j<arrayCellValue[i].length;j++){
                System.out.println("值："+arrayCellValue[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
