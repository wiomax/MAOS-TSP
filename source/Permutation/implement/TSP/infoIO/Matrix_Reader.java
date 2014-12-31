/**
 * Description: reads the problem data in TSPLIB format
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 6, 2005     xiaofengxie@tsinghua.org.cn
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 *  [1] Program's name: acotsp
 *  Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
 *  symmetric TSP, Copyright (C) 2004  Thomas Stuetzle
 *  Email: stuetzle no@spam informatik.tu-darmstadt.de
 *  [2] TSPLIB, Gerhard Reinelt, Gerhard.Reinelt{at}informatik.uni-heidelberg.de
 */

package implement.TSP.infoIO;

import Global.methods.*;

public class Matrix_Reader {


  public static void readTRIContent(int[][] dataMatrix, boolean isLowTRI, boolean hasDiagonal, String[] lines, int startIndex, int endIndex) throws Exception {
    int ri = 0, rj = 0;
    int bias = 0;
    int nodeNumber = dataMatrix.length;
    if (!hasDiagonal) {
      bias=1;
    }
    if (isLowTRI) {
      ri += bias;
    } else {
      rj += bias;
    }

    for (int i=startIndex; i<endIndex; i++) {
      String[] lineInfos = GlobalString.tokenize(lines[i], " \t");
      for (int j=0; j<lineInfos.length; j++) {
        dataMatrix[ri][rj] = new Double(lineInfos[j]).intValue();
        if (ri!=rj) {
          dataMatrix[rj][ri] = dataMatrix[ri][rj];
        }
        rj ++;
        if (isLowTRI) {
          if (rj>ri-bias) {
            ri ++;
            rj = 0;
          }
        } else {
          if (rj >= nodeNumber) {
            ri++;
            rj = ri + bias;
          }
        }
      }
    }
  }

  public static void readFullMatrix(int[][] dataMatrix, String[] lines, int startIndex, int endIndex) throws Exception {
    int ri = 0, rj = 0;
    for (int i=startIndex; i<endIndex; i++) {
      String[] lineInfos = GlobalString.tokenize(lines[i], " \t");
      for (int j=0; j<lineInfos.length; j++) {
        dataMatrix[ri][rj] = new Double(lineInfos[j]).intValue();
        rj ++;
        if (rj>=dataMatrix[ri].length) {
          rj = 0;
          ri ++;
        }
      }
    }
  }
}

