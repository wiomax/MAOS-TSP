/**
 * Description: Parser for behavioral modules
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 03, 2006
 * Xiaofeng Xie    Jun 03, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel;

import Global.define.*;

import maosKernel.behavior.generate.*;
import maosKernel.behavior.scratch.*;
import maosKernel.behavior.update.*;
import maosKernel.represent.landscape.*;
import maosKernel.infoIO.parser.*;

public class BehaviorBoxHandler {
  private String problemType = "";
  private AbsLandscape virtualLandscape = null;

  //the knowledge components to be loaded
  public AbsStateUpdater absStateUpdater = new DirectStateUpdater();
  public AbsScratchSearch absConstructor;
  public AbsMiniGenerator absGenerator;
//  public AbsTopology absTopology;

  private InformationContainer informationContainer = new InformationContainer();
  private InformationParser infomationParser = new InformationParser();

  public BehaviorBoxHandler(String problemType, AbsLandscape virtualLandscape) {
    this.problemType = problemType;
    this.virtualLandscape = virtualLandscape;
  }
  
  public void parseBehaviorScript(String behaviorLine) throws Exception {
    infomationParser.parseSolver(behaviorLine, informationContainer, problemType, virtualLandscape);
    extractElements(informationContainer);  
  }
  
  private void extractElements(InformationContainer informationContainer) throws Exception {
//    absTopology = (AbsTopology)informationContainer.getElement("TOPO");
    absConstructor  = (AbsScratchSearch)informationContainer.getElement("R_INI");
    absStateUpdater = (AbsStateUpdater)informationContainer.getElement("R_T");
    absGenerator = (AbsMiniGenerator)informationContainer.getElement("R_G");
  }

  public String getBehaviorsString() {
    String initBehaviorInfo = "";
//    initBehaviorInfo += "Topology(AbsTopology): "+absTopology.getSummary()+BasicTag.RETURN_TAG;
    initBehaviorInfo += "R_T(AbsStateUpdater): "+absStateUpdater.getSummary()+BasicTag.RETURN_TAG;
    initBehaviorInfo += "R_INI(AbsScratchSearch): "+absConstructor.getSummary()+BasicTag.RETURN_TAG;
    initBehaviorInfo += "R_G(AbsMiniGenerator): "+absGenerator.getSummary();
    return initBehaviorInfo;
  }
}

