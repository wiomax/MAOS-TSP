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
 *  [1] Thomas Stuetzle. acotsp, C Program, Copyright (C) 2004 
 *  [2] Gerhard Reinelt. TSPLIB, www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/
 */

package implement.TSP.infoIO;

import Global.methods.*;

import maosKernel.represent.problem.*;
import maosKernel.infoIO.instance.*;
import implement.TSP.represent.*;

public class TSPInstanceLoader extends AbsFileInstanceLoader {

  public AbsProblemData readProblem(String content) throws Exception {
    String[] lines = GlobalString.getMeaningfulLines(content);
    String lineContent = "";

    lineContent = getSubString(lines, "EDGE_WEIGHT_TYPE");
    if (lineContent!=null) {
      Abs_TSPData tspData = null;
      if (!lineContent.equalsIgnoreCase("EXPLICIT")) {
        tspData = readEUCContent(lines);
      } else {
        tspData = readExplicitContent(lines);
      }
      RealProblemData problemData =  new RealProblemData();
      problemData.initUtilities();
      problemData.setCostMatrix(tspData.getDistanceMatrix());
      return problemData;
    }
    throw new Exception("Not a TSP TYPE in TSPLIB format!!");
  }

  private static DIR_TSPData readExplicitContent(String[] lines) throws Exception {
    String lineContent = "";

    int index = StringSearch.getStringLoc(lines, "EDGE_WEIGHT_SECTION");
    if (index==-1) {
      throw new Exception("Errors ocurred in finding EDGE_WEIGHT_SECTION from tsp file!");
    }

    int tspN = -1;
    lineContent = getSubString(lines, "DIMENSION");
    if (lineContent!=null) {
      tspN = new Integer(lineContent).intValue();
    } else {
      return null;
    }

    DIR_TSPData tspData = new DIR_TSPData(tspN);
    readGeneralContent(tspData, lines);

    lineContent = getSubString(lines, "EDGE_WEIGHT_TYPE");
    if (lineContent!=null) {
      if (!tspData.setEDGE_WEIGHT_TYPE(lineContent)) {
        throw new Exception("Unkown EDGE_WEIGHT_TYPE: "+ lineContent);
      }
    }

    int endIndex = getEndIndex(lines);
    lineContent = getSubString(lines, "EDGE_WEIGHT_FORMAT");
    if (lineContent!=null) {
      if (!tspData.setDataBlock(lineContent, lines, index+1, endIndex)) {
        throw new Exception("Unkown EDGE_WEIGHT_FORMAT: "+ lineContent);
      }
    }
    return tspData;
  }

  private static int getEndIndex(String[] lines) {
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
    return endIndex;
  }

  private static void readGeneralContent(Abs_TSPData tspData, String[] lines) throws Exception {
    String lineContent = "";

    lineContent = getSubString(lines, "TYPE");
    if (lineContent!=null) {
      tspData.setKey(lineContent);
    }

    lineContent = getSubString(lines, "NAME");
    if (lineContent!=null) {
      tspData.name = lineContent;
    }

    lineContent = getSubString(lines, "COMMENT");
    if (lineContent!=null) {
      tspData.description = lineContent;
    }
  }

  public static EUC_TSPData readEUCContent(String[] lines) throws Exception {
    EUC_TSPData tspData = new EUC_TSPData();
    readGeneralContent(tspData, lines);

    String lineContent = "";

    int index = StringSearch.getStringLoc(lines, "NODE_COORD_SECTION");
    if (index==-1) {
      throw new Exception("Errors ocurred in finding NODE_COORD_SECTION from tsp file!");
    }
    lineContent = getSubString(lines, "EDGE_WEIGHT_TYPE");
    if (lineContent!=null) {
      if (!tspData.setEDGE_WEIGHT_TYPE(lineContent)) {
        throw new Exception("Unkown EDGE_WEIGHT_TYPE: "+ lineContent);
      }
    }

    int tspN = -1;
    lineContent = getSubString(lines, "DIMENSION");
    if (lineContent!=null) {
      tspN = new Integer(lineContent).intValue();
    }

    int endIndex = getEndIndex(lines);
    if (tspN!=endIndex-index-1) {
      throw new Exception("Invalid dimension: "+tspN+"(specified)!="+(endIndex-index-1)+"(readable lines)");
    }

    double[][] tspCOORDData = new double[tspN][2];
    for (int i=index+1; i<endIndex; i++) {
      String[] lineInfos = GlobalString.tokenize(lines[i], " \t");
      tspCOORDData[i-index-1][0] = Double.parseDouble(lineInfos[1]);
      tspCOORDData[i-index-1][1] = Double.parseDouble(lineInfos[2]);
    }
    tspData.tspCOORDData = tspCOORDData;
    return tspData;
  }

  private static String getSubString(String[] lines, String headStr) {
    return GlobalString.getFirstStringWithHead(lines, headStr, ":");
  }
}

