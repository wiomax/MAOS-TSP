/**
 * Description: reads the solution data in TSPLIB format
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

import Global.define.*;
import Global.methods.*;

import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.landscape.space.SearchState;
import implement.common.infoIO.*;

public class TSPSolutionIOHandler extends AISolutionIOHandler {

  public String writeSolution(AbsLandscape virtualLandscape, EncodedState state) throws Exception {
    String tourStr = "";
    int[] tour = state.getSearchState().getIArray();
    tourStr += "NAME : see file name"+BasicTag.RETURN_TAG;
    tourStr += "COMMENT : Optimum tour with cost "+EncodedStateHandler.writeEncodedInfo(virtualLandscape, state.getEncodeInfo())+BasicTag.RETURN_TAG;
    tourStr += "TYPE : TOUR"+BasicTag.RETURN_TAG;
    tourStr += "DIMENSION : "+tour.length+BasicTag.RETURN_TAG;
    tourStr += "TYPE : TOUR"+BasicTag.RETURN_TAG;
    tourStr += "TOUR_SECTION"+BasicTag.RETURN_TAG;
    for (int i=0; i<tour.length; i++) {
      tourStr += (tour[i]+1)+BasicTag.RETURN_TAG;
    }
    tourStr += "-1"+BasicTag.RETURN_TAG;
    tourStr += "EOF"+BasicTag.RETURN_TAG;
    return tourStr;
  }

  public void naiveReadSolution(EncodedState state, String content) throws Exception {
  	naiveReadSolution(state.getSearchState(), content);
  }
  
  public void naiveReadSolution(SearchState state, String content) throws Exception {
  	state.initLocation(naiveReadSolution(content));
  }
  
  public int[] naiveReadSolution(String content) throws Exception {
    String[] lines = GlobalString.getMeaningfulLines(content);
    int index = StringSearch.getStringLoc(lines, "TOUR_SECTION");
    if (index==-1) return null;
    int endIndex1 = StringSearch.getStringLoc(lines, "-1");
    int endIndex2 = StringSearch.getStringLoc(lines, "EOF");
    int endIndex = lines.length;
    if (endIndex1==-1&&endIndex2==-1) {
      endIndex = lines.length;
    } else if (endIndex1==-1&&endIndex2!=-1) {
      endIndex = endIndex2;
    } else if (endIndex1!=-1&&endIndex2==-1) {
      endIndex = endIndex1;
    } else {
      endIndex = Math.min(endIndex1, endIndex2);
    }

    int tspN = -1;
    String lineContent = getSubString(lines, "DIMENSION");
    if (lineContent != null) {
      tspN = new Integer(lineContent).intValue();
    }

    int dataLines = endIndex-index-1;
    int[] tourPath;
    if (tspN==-1) {
      tourPath = new int[0];
      for (int i=index+1; i<endIndex; i++) {
        String[] lineAti = GlobalString.tokenize(lines[i], " \t");
        int[] contentAtLineI = new int[lineAti.length];
        for (int j=0; j<contentAtLineI.length; j++) {
          contentAtLineI[j] = (new Integer(lineAti[j])).intValue();
        }
        int[] currentContents = new int[tourPath.length+contentAtLineI.length];
        System.arraycopy(tourPath, 0, currentContents, 0, tourPath.length);
        System.arraycopy(contentAtLineI, 0, currentContents, tourPath.length, contentAtLineI.length);
        tourPath = currentContents;
      }
    } else {
      tourPath = new int[tspN];
      int inCount = 0;
      if (tspN != dataLines) {
        for (int i = index + 1; i < endIndex; i++) {
          String[] lineAti = GlobalString.tokenize(lines[i], " \t");
          for (int j = 0; j < lineAti.length; j++) {
            tourPath[inCount] = (new Integer(lineAti[j])).intValue();
            inCount++;
          }
        }
      }
      else {
        for (int i = index + 1; i < endIndex; i++) {
          tourPath[i - index - 1] = (new Integer(lines[i])).intValue();
        }
      }
    }
    return tourPath;
  }

  private static String getSubString(String[] lines, String headStr) {
    return GlobalString.getFirstStringWithHead(lines, headStr, ":");
  }
}

