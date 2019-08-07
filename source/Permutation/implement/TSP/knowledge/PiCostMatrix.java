/**
 * The Minimum1TreeCost function returns the cost of a minimum 1-tree.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 21, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 */

package implement.TSP.knowledge;

import static Global.math.ArrayMath.totalSum;

import Global.basic.data.matrix.FullSquareIMatrix;
import Global.basic.data.matrix.ISquareIMatrixEngine;

public class PiCostMatrix implements ISquareIMatrixEngine {
  private int precision = 1;
  private ISquareIMatrixEngine sdMatrix;
  private int[] piArray;
  private int[][] piMatrix;

  public PiCostMatrix(ISquareIMatrixEngine sdMatrix) {
    this.sdMatrix = sdMatrix;
    int nodeNumber = sdMatrix.getNodeNumber();
    piMatrix = new int[nodeNumber][nodeNumber];
  }

  public PiCostMatrix(ISquareIMatrixEngine sdMatrix, double[] extPIArray, int precision) {
    this(sdMatrix);
    this.setPI(extPIArray, precision);
  }

  public void setPI(double[] extPIArray, int precision) {
    this.setPrecision(precision);
    this.setPIArray(extPIArray);
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public ISquareIMatrixEngine getCostMatrix() {
    if (piArray==null) return sdMatrix;
    return new FullSquareIMatrix(piMatrix);
  }

  public int getPrecision() {
    return precision;
  }

  public void setPIArray(double[] extPIArray) {
    int nodeNumber = sdMatrix.getNodeNumber();
    piArray = new int[nodeNumber];
    int i, j;
    for(i=0; i<nodeNumber; i++) {
      piArray[i] = (int) (extPIArray[i]*precision);
    }
    for (i=0; i<nodeNumber; i++) {
      for (j=0; j<nodeNumber; j++) {
        piMatrix[i][j] = precision*sdMatrix.getValueAt(i, j) + piArray[i] + piArray[j];
      }
    }
  }

  public int getNodeNumber() {
    return sdMatrix.getNodeNumber();
  }

  public int getPenaltyValue() {
    if (piArray==null) return 0;
    return 2*totalSum(piArray);
  }

  public int getValueAt(int i, int j) {
    if (piArray==null) return precision*sdMatrix.getValueAt(i, j);
    return piMatrix[i][j];
  }

}

