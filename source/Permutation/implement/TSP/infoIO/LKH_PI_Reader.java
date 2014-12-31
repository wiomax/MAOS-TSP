/**
 * Description: provide the information for read the penalty values for TSP nodes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 22, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 * [2] Reinelt, G. The Traveling Salesman: Computational Solutions for TSP
 *  Applications. Berlin: Springer, 1994.
 */

package implement.TSP.infoIO;

import Global.define.*;
import Global.methods.*;

public class LKH_PI_Reader {
  public static final int precision = 100;

  public static String writeResult(double[] piValues) throws Exception {
    return writeResult(BasicArray.getFullIDArray(piValues.length), piValues);
  }

  public static String writeResult(int[] tour, double[] piValues) throws Exception {
    String piStr = "";
    for (int i=0; i<piValues.length; i++) {
      piStr += (tour[i]+1)+" "+(int)(piValues[i]*precision)+BasicTag.RETURN_TAG;
    }
    return piStr;
  }

  public static double[] readResult(String piContent) throws Exception {
    String[] lines = GlobalString.getMeaningfulLines(piContent);
    double[] piValues = new double[lines.length];
    for (int i=0; i<lines.length; i++) {
      String[] lineInfos = GlobalString.tokenize(lines[i], " \t");
      piValues[new Integer(lineInfos[0]).intValue()-1] = new Double(lineInfos[1]).doubleValue()/precision;
    }
    return piValues;
  }
}
