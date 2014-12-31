/**
 * Description: provide the information for the direct TSP data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 16, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 *  [1] TSPLIB, Gerhard Reinelt, Gerhard.Reinelt{at}informatik.uni-heidelberg.de
 */

package implement.TSP.infoIO;

import Global.methods.*;

public class DIR_TSPData extends Abs_TSPData {
  private int[][] distanceMatrix;  //save the distances for any two nodes, just for accelating
  private boolean isSymmetric = true;

  private String[] RECOG_EW_TYPE = {"EXPLICIT"};
  private String[] RECOG_EW_FORMAT ={"FULL_MATRIX", "UPPER_ROW", "LOWER_ROW", "UPPER_DIAG_ROW", "LOWER_DIAG_ROW"};
  private int index_EDGE_WEIGHT_FORMAT = -1;

  public DIR_TSPData(int nodeNumber) {
    distanceMatrix = new int[nodeNumber][nodeNumber];
  }

  public boolean setEDGE_WEIGHT_TYPE(String type) {
    int index_EDGE_WEIGHT_TYPE = StringSearch.getStringLoc(RECOG_EW_TYPE, type);
    return (index_EDGE_WEIGHT_TYPE!=-1);
  }

  public int setEDGE_WEIGHT_FORMAT(String type) {
    index_EDGE_WEIGHT_FORMAT = StringSearch.getStringLoc(RECOG_EW_FORMAT, type);
    return index_EDGE_WEIGHT_FORMAT;
  }

  public int getNodeNumber() {
    return distanceMatrix.length;
  }

  public int[][] getDistanceMatrix() {
    return distanceMatrix;
  }

  public boolean isSymmetricMatrix() {
    return isSymmetric;
  }

  public boolean setDataBlock(String edge_WEIGHT_FORMAT_String, String[] lines, int startIndex, int endIndex) throws Exception {
    int edge_WEIGHT_FORMAT = setEDGE_WEIGHT_FORMAT(edge_WEIGHT_FORMAT_String);
    if (edge_WEIGHT_FORMAT==-1) return false;
    boolean isLowTRI = true;
    boolean hasDiagonal = true;
    switch (index_EDGE_WEIGHT_FORMAT) {
      case 0: //FULL_MATRIX
        isSymmetric = false;
        Matrix_Reader.readFullMatrix(this.distanceMatrix, lines, startIndex, endIndex);
        return true;
      case 1: //UPPER_ROW
        isLowTRI = false;
        hasDiagonal = false;
        break;
      case 2: //LOWER_ROW
        hasDiagonal = false;
        break;
      case 3: //UPPER_DIAG_ROW
        isLowTRI = false;
        break;
      case 4: //LOWER_DIAG_ROW
        break;
      default:
        return false;
    }
    Matrix_Reader.readTRIContent(distanceMatrix, isLowTRI, hasDiagonal, lines, startIndex, endIndex);
    return true;
  }

  public int getValueAt(int i, int j) {
    if (i==j) return 0;
    return distanceMatrix[i][j];
  }
}

