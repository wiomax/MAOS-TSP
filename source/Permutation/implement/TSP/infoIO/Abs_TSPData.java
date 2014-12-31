/**
 * Description: provide the information for the TSP data
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 16, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 */

package implement.TSP.infoIO;

import Global.basic.data.matrix.*;
import maosKernel.represent.problem.*;

public abstract class Abs_TSPData extends AbsProblemData implements ISquareIMatrixEngine {

  abstract public int[][] getDistanceMatrix();

//  public double getLowTriSD(double averageV) {
//    int nodeNumber = getNodeNumber();
//    double sum = 0;
//    int totalN = nodeNumber*(nodeNumber-1)/2;
//    for (int i=0; i<nodeNumber; i++) {
//      for (int j=0; j<i; j++) {
//        sum += Math.pow(getValueAt(i, j)-averageV, 2);
//      }
//    }
//    return Math.sqrt(sum/totalN);
//  }
//
//  public double getLowTriAverage() {
//    int nodeNumber = getNodeNumber();
//    double sum = 0;
//    int totalN = nodeNumber*(nodeNumber-1)/2;
//    for (int i=0; i<nodeNumber; i++) {
//      for (int j=0; j<i; j++) {
//        sum += getValueAt(i, j);
//      }
//    }
//    return sum/totalN;
//  }
}

