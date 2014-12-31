/**
 * Description: Provide the initialization for the topology
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 04, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel;

import Global.methods.*;
import Global.system.io.*;

import maosKernel.behavior.topology.*;
import maosKernel.infoIO.setting.*;

import maosKernel.infoIO.parser.*;

public class TopologyHandler {
  private InformationContainer knowledgeContainer = new InformationContainer();
  private InformationParser knowledgeParser = new InformationParser();

  private String[] topoLines = new String[0];

  public TopologyHandler() {
    try {
      String contents = GlobalFile.getStringFromFile(SystemSettingPath.getTopologyFileName());
      topoLines = GlobalString.getMeaningfulLines(contents);
    } catch (Exception e){
      System.out.println(this.getClass().getName()+": "+e);
    }
  }

  public AbsTopology initTopology(String name) throws Exception {
    return (AbsTopology)knowledgeParser.seekInformationNode(name, topoLines, knowledgeContainer);
  }
}

