/**
 * Description: Output methods
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 22, 2001    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.util;

import Global.define.BasicTag;


public class OutputMethods {

  public static void outputMatrix(double[][] matrix){
    for (int i=0;i<matrix.length;i++){
      for( int j=0;j<matrix[i].length;j++ ){
        System.out.print(matrix[i][j]+BasicTag.NULL_SEPERATE_TAG);
      }
      System.out.println("");
    }
    System.out.println("matrix over.");
  }

  public static void outputMatrix(int[][] matrix){
  for (int i=0;i<matrix.length;i++){
    for( int j=0;j<matrix[i].length;j++ ){
      System.out.print(matrix[i][j]+BasicTag.NULL_SEPERATE_TAG);
    }
    System.out.println("");
  }
  System.out.println("matrix over.");
  }

  public static void outputMatrix(boolean[][] matrix){
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int v = 0;
        if (matrix[i][j]) {
          v = 1;
        }
        System.out.print(v);
        if (j != matrix[i].length - 1) {
          System.out.print(BasicTag.NULL_SEPERATE_TAG);
        }
      }
      System.out.println();
    }
    System.out.println("matrix over.");
  }

  public static String outputVectorAsStr(double[] vector, int size){
    if(vector==null) return "NULL";
    String totalStr = "";
    for(int i=0;i<size;i++){
      totalStr += vector[i];
      if(i!=size-1) {
        totalStr += BasicTag.NULL_SEPERATE_TAG;
      }
    }
    totalStr+=BasicTag.RETURN_TAG;
    return totalStr;
  }

  public static String outputVectorAsStr(double[] vector){
    return outputVectorAsStr(vector, vector.length);
  }

  public static void outputVector(double[] vector){
    for(int i=0;i<vector.length;i++){
      System.out.print(vector[i]+" \t");
    }
    System.out.println("");
//    System.out.println("vector over.");
  }

  public static void outputVector(int[] vector){
    for(int i=0;i<vector.length;i++){
      System.out.print(vector[i]+BasicTag.NULL_SEPERATE_TAG);
    }
    System.out.println("");
//    System.out.println("vector over.");
  }

  public static void outputVector(boolean[] vector){
    for(int i=0;i<vector.length;i++){
      System.out.print(vector[i]+BasicTag.NULL_SEPERATE_TAG);
    }
    System.out.println("");
    System.out.println("vector over.");
  }

  public static void outputVerticalVector(int[] vector){
    for(int i=0;i<vector.length;i++){
      System.out.print(vector[i]+BasicTag.RETURN_TAG);
    }
  }
}
