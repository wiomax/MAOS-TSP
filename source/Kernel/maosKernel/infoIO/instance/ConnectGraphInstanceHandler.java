/**
 * Description: provide the information for graph data in DIMACS format
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 06, 2005
 * Xiaofeng Xie    Aug 08, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.instance;

import java.util.*;
import Global.basic.data.*;
import Global.methods.*;

import maosKernel.represent.problem.graph.*;

public class ConnectGraphInstanceHandler {

  public static BasicConnectGraphData readDIMACSGraph(String content) throws Exception {
    String[] lines = GlobalString.getMeaningfulLines(content, "c");
    if (lines.length<2) return null;
    String[] pInfos = GlobalString.tokenize(lines[0], " \t");
    if (pInfos.length<4) return null;
    if(!pInfos[0].equalsIgnoreCase("p")) return null; // || !pInfos[1].equalsIgnoreCase("edge")


    BasicConnectGraphData gData = new BasicConnectGraphData();
    gData.nodeNumber = new Integer(pInfos[2]).intValue();

    Vector<BasicEdge> edges = new Vector<BasicEdge>();

    int minNodeValue = Integer.MAX_VALUE;
    for(int i=1; i<lines.length; i++) {
      String[] eInfos = GlobalString.tokenize(lines[i], " \t");
      if(eInfos[0].equalsIgnoreCase("e")) {
        minNodeValue = Math.min(minNodeValue, new Integer(eInfos[1]).intValue());
        minNodeValue = Math.min(minNodeValue, new Integer(eInfos[2]).intValue());
        BasicEdge edge = new BasicEdge(new Integer(eInfos[1]).intValue(), new Integer(eInfos[2]).intValue());
        edges.add(edge);
      }
    }

    BasicEdge[] fullAttackEdges = new BasicEdge[edges.size()];
    for(int i=0; i<fullAttackEdges.length; i++) {
      fullAttackEdges[i] = (BasicEdge)edges.elementAt(i);
    }
    if (minNodeValue!=0) {
      for(int i=0; i<fullAttackEdges.length; i++) {
         fullAttackEdges[i].startIndex-= minNodeValue;
          fullAttackEdges[i].endIndex -= minNodeValue;
      }
    }
    gData.setAttackEdges(fullAttackEdges);
    return gData;
  }
}

