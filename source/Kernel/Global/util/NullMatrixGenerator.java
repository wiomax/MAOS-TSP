/**
 * Description: For initializing a matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.util;

import Global.basic.data.matrix.*;

public class NullMatrixGenerator {
  public static boolean[][] getNullBoolMatrix(IIrregular2DEngine cspMatrixEngine) {
    boolean[][] matrix = new boolean[cspMatrixEngine.getNodeNumber()][];
    for(int i=0; i<matrix.length; i++) {
      matrix[i] = new boolean[cspMatrixEngine.getElementNumberAt(i)];
    }
    return matrix;
  }

  public static int[][] getNullIntegerMatrix(IIrregular2DEngine cspMatrixEngine) {
    int[][] matrix = new int[cspMatrixEngine.getNodeNumber()][];
    for(int i=0; i<matrix.length; i++) {
      matrix[i] = new int[cspMatrixEngine.getElementNumberAt(i)];
    }
    return matrix;
  }

  public static double[][] getNullDoubleMatrix(IIrregular2DEngine cspMatrixEngine) {
    double[][] matrix = new double[cspMatrixEngine.getNodeNumber()][];
    for(int i=0; i<matrix.length; i++) {
      matrix[i] = new double[cspMatrixEngine.getElementNumberAt(i)];
    }
    return matrix;
  }

}
