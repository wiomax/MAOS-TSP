/**
 * Description: For parsing knowledge elements
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 06, 2006
 * Xiaofeng Xie    Jun 11, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.parser;

import java.util.*;

import Global.basic.*;
import Global.basic.nodes.*;
import Global.basic.nodes.utilities.*;
import Global.basic.nodes.loader.*;
import Global.define.*;
import Global.methods.*;
import Global.system.*;
import Global.exception.*;
import maosKernel.infoIO.setting.*;
import maosKernel.represent.landscape.*;
import maosKernel.represent.information.*;
import maosKernel.represent.knowledge.*;

public class InformationParser {
  private AbsUtilSetNodeLoader utilSetNodeLoader = new ModuleUtilSetNodeLoader();
  private String REFER_TAG = "&";
  private String PARAM_TAG = "%";

  private boolean isSpecialName(String nodeName, String specString) {
    return (nodeName.startsWith(specString));
  }

  private String getSpecialName(String nodeName, String specString) {
    return nodeName.substring(specString.length(), nodeName.length());
  }

  public void parseSolver(String solverString, InformationContainer informationContainer, String problemType, AbsLandscape virtualLandscape) throws Exception {
    UtilSetNode solverNode = getRawCommandNode(solverString);
    BasicMap moduleContent = getModuleContent(problemType, solverNode.getKey(), SystemSettingPath.SolverPath);

    Hashtable<String, Object> settingParamSet = new Hashtable<String, Object>();
    parseMetaCMDLineParameters(solverNode, settingParamSet, informationContainer, new InformationContainer());
    execModuleContent(moduleContent.value, settingParamSet, informationContainer, problemType, virtualLandscape);
  }

  private void parseLine(UtilSetNode node, InformationContainer knowledgeContainer, AbsLandscape virtualLandscape) throws Exception {
    AbsInformationElement knowledgeNode = null;
    knowledgeNode = createInformationNode(node, knowledgeContainer);
    if (knowledgeNode instanceof ISetLandscapeInfoEngine) {
      ((ISetLandscapeInfoEngine)knowledgeNode).setInfo(virtualLandscape);
    }
    knowledgeContainer.putElement(knowledgeNode);
  }

  public AbsInformationElement createInformationNode(String nodeKey) throws Exception {
    Class<?> cls = Class.forName(nodeKey);
    AbsInformationElement knowledgeNode = (AbsInformationElement)cls.newInstance();
    knowledgeNode.initUtilities();
    return knowledgeNode;
  }

  public AbsInformationElement createInformationNode(UtilSetNode infoNode, InformationContainer knowledgeContainer) throws Exception {
    AbsInformationElement knowledgeNode = createInformationNode(infoNode.getKey());
    knowledgeNode.setName(infoNode.getName());

    for (int i=0; i<infoNode.getUtilitiesSize(); i++) {
      BasicUtility utility = infoNode.getUtilityAt(i);
      int index = knowledgeNode.getUtilityIndex(utility.getName());
      if (index==-1) {
        System.out.println("Cannot find the specified utility: "+utility.getName());
        continue;
      }
      String value = utility.getValue().toString();
      Object realValue = utility.getValue();
      if (isSpecialName(value, REFER_TAG)) realValue = knowledgeContainer.getElement(getSpecialName(value, REFER_TAG));
      if (isSpecialName(value, PARAM_TAG)) realValue = knowledgeContainer.getElement(getSpecialName(value, PARAM_TAG));
      if (realValue == null) {
        throw new ParamNotFoundException(value);
      }
      knowledgeNode.getUtilityAt(index).setValue(realValue);
    }
    knowledgeNode.shortcutInit();

    return knowledgeNode;
  }

  public BasicMap getModuleContent(String problemType, String moduleString, String pathString) throws Exception {
    String[] moduleInfos = GlobalString.tokenize(moduleString, "@");
    String fileString = moduleInfos[moduleInfos.length-1];
    String sectionString = "";
    if (moduleInfos.length>1) {
      sectionString = moduleInfos[0];
    }
    if (GlobalString.isNull(sectionString)) {
      //whole file
      return SystemSettingPath.getFileContent(problemType, fileString, pathString);
    } else {
      //the section "sectionString" in the file "fileString"
      try {
        return SystemSettingPath.getSectionContent(problemType, fileString, sectionString, pathString);
      } catch (Exception e) {
        //See [OneLine] Section
        String preContent = SystemSettingPath.getPreSectionConent(problemType, fileString, pathString);
        String[] objLines = GlobalString.getMeaningfulLines(preContent);
        int line = seekComponentLine(sectionString, objLines);
        if (line != -1) {
          BasicMap content = new BasicMap();
          content.setMap(sectionString, objLines[line]);
          return content;
        }
        throw new Exception("Cannot locate Section \""+sectionString+"\" in file \"" + fileString+"\"");
      }
    }
  }

  private int seekComponentLine(String name, String[] objLines) {
    for (int i=0; i<objLines.length; i++) {
      UtilSetNode node = new UtilSetNode();
      utilSetNodeLoader.loadUtilSetNode(node, getSeperateCMDLine(objLines[i]).value);
      if (node.getName().equalsIgnoreCase(name)) return i;
    }
    return -1;
  }
  
  public AbsInformationElement seekInformationNode(String name, String[] objLines, InformationContainer knowledgeContainer) throws Exception {
    UtilSetNode infoNode = seekUtilSetNode(name, objLines);
    try {
      return createInformationNode(infoNode, knowledgeContainer);
    } catch (Exception e) {
      throw new Exception(name+": component is not found.");
    }
  }

  private UtilSetNode seekUtilSetNode(String name, String[] objLines) {
    for (int i=0; i<objLines.length; i++) {
      UtilSetNode node = new UtilSetNode();
      utilSetNodeLoader.loadUtilSetNode(node, objLines[i]);
      if (node.getName().equalsIgnoreCase(name)) return node;
    }
    return null;
  }
  
  //inputs:  1) UtilSetNode solverNode; 2) InformationContainer parentInfoContainer
  //outputs: 1) BasicMap moduleContent; 2) Hashtable settingParamSet; 3) InformationContainer informationContainer
  public void parseMetaCMDLineParameters(UtilSetNode solverNode, Hashtable<String, Object> settingParamSet, InformationContainer informationContainer, InformationContainer parentInfoContainer) throws Exception {
    for (int i=0; i<solverNode.getUtilSize(); i++) {
      BasicUtility util = solverNode.getUtilityAt(i);
      AbsInformationElement referObj = null;
      boolean isSpecParamter = false;
      if (isSpecialName(util.getValueString(), REFER_TAG)) {
        isSpecParamter = true;
        referObj = parentInfoContainer.getElement(getSpecialName(util.getValueString(), REFER_TAG));
      } else if (isSpecialName(util.getValueString(), PARAM_TAG)) {
        isSpecParamter = true;
        referObj = parentInfoContainer.getElement(getSpecialName(util.getValueString(), PARAM_TAG));
      }
      if (isSpecParamter) {
        if (referObj!=null) {
          informationContainer.putElement(util.getName(), referObj);
        } else {
          throw new ParamNotFoundException(util.getValueString());
        }
      } else {
        settingParamSet.put(util.getName(), util.getValue());
      }
    }
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
	public void parseMappingParameter(String externName, String internName, Hashtable<String, Object> settingParamSet, Hashtable paramMappingSet) {
    String[] mapStrs = GlobalString.tokenize(internName, ":");
    if (mapStrs.length!=2) return;
    Object paramValue = settingParamSet.get(externName);
    if (paramValue==null) return;
    Hashtable subTable = (Hashtable)paramMappingSet.get(mapStrs[0].trim());
    if (subTable==null) {
      subTable = new Hashtable();
      paramMappingSet.put(mapStrs[0].trim(), subTable);
    }
    subTable.put(mapStrs[1].trim(), paramValue);
    settingParamSet.remove(externName);
  }

  public UtilSetNode getRawCommandNode(String cmdLine) throws Exception {
    UtilSetNode solverNode = new UtilSetNode();
    utilSetNodeLoader.loadUtilSetNode(solverNode, cmdLine);
    return solverNode;
  }
  
  @SuppressWarnings({ "rawtypes" })
  protected UtilSetNode getCommandNode(String name, String cmdLine, Hashtable<String, Object> settingParamSet, Hashtable paramMappingSet) throws Exception {
    UtilSetNode solverNode = getRawCommandNode(cmdLine);
    solverNode.setName(name);
    Hashtable mappedParams = (Hashtable)paramMappingSet.get(name);
    if (mappedParams!=null) {
      Enumeration keys = mappedParams.keys();
      while(keys.hasMoreElements()) {
        Object key = keys.nextElement();
        GradedOut.showNORMALMessage("Parse: Set mapping parameter "+ key+"="+mappedParams.get(key)+" for component \""+name+"\"");
        solverNode.setValue(key.toString(), mappedParams.get(key).toString());
      }
    } else {
      for (int i=0; i< solverNode.getUtilSize(); i++) {
        BasicUtility utility = solverNode.getUtilityAt(i);
        String realValue = utility.getValue().toString();
        if (!isSpecialName(realValue, REFER_TAG) && !isSpecialName(realValue, PARAM_TAG)) {
          Object v = settingParamSet.get(utility.getName());
          if (v!=null) {
            GradedOut.showNORMALMessage("Parse: Set setting parameter "+ utility.getName()+"="+v+" for component \""+name+"\"");
            solverNode.setValue(utility.getName(), v);
          }
        }
      }
    }
    return solverNode;
  }
  
  private BasicMap getSeperateCMDLine(String cmdLine) {
    BasicMap content = new BasicMap();
    if (!cmdLine.trim().startsWith("-")) {
      content.setMap("-D", cmdLine); // -D is the default command mode
    } else {
      String[] lineArray = GlobalString.splitFirst(cmdLine, BasicTag.NULL_SEPERATE_TAG);
      content.setMap(lineArray[0].trim(), lineArray[1].trim());
    }
    return content;
  }
  
  @SuppressWarnings({ "rawtypes"})
  public void execModuleContent(String moduleContent, Hashtable<String, Object> settingParamSet, InformationContainer informationContainer, String problemType, AbsLandscape virtualLandscape) throws Exception {
    Hashtable paramMappingSet = new Hashtable();
    
    String[] cmdLines = GlobalString.getMeaningfulLines(moduleContent);
    for (int i=0; i<cmdLines.length; i++) {
      GradedOut.showNORMALMessage("Parse: \""+cmdLines[i]+"\"");
      BasicMap cmdLine = getSeperateCMDLine(cmdLines[i]);
      String cmdType = cmdLine.name;
      String cmdStr = cmdLine.value;
      
      String[] cmdInfo = GlobalString.tokenize(cmdStr, "<");

      for (int j=0; j<cmdInfo.length; j++) {
        cmdInfo[j] = cmdInfo[j].trim();
      }
      
      if (cmdType.equalsIgnoreCase("-P")) {
        parseMappingParameter(cmdInfo[0], cmdInfo[1], settingParamSet, paramMappingSet);
      } else if (cmdType.equalsIgnoreCase("-L")) {
        UtilSetNode solverNode = getCommandNode(cmdInfo[0], cmdInfo[1], settingParamSet, paramMappingSet);
        InformationContainer tempInfoContainer = new InformationContainer();
        Hashtable<String, Object> tempSettingParamSet = new Hashtable<String, Object>();
        BasicMap tempModuleContent = getModuleContent(problemType, solverNode.getKey(), SystemSettingPath.PredefinedPath);
        parseMetaCMDLineParameters(solverNode, tempSettingParamSet, tempInfoContainer, informationContainer);
        execModuleContent(tempModuleContent.value, tempSettingParamSet, tempInfoContainer, problemType, virtualLandscape);
        String moduleName = tempModuleContent.name;
        AbsInformationElement element = tempInfoContainer.getElement(moduleName);
        element.setName(cmdInfo[0].trim());
        informationContainer.putElement(element);
        GradedOut.showNORMALMessage("Parse: Export \""+moduleName+"\" -> \""+cmdInfo[0].trim()+"\"");
      } else if (cmdType.equalsIgnoreCase("-D")) {
        UtilSetNode solverNode = getCommandNode(cmdInfo[0], cmdInfo[1], settingParamSet, paramMappingSet);
        parseLine(solverNode, informationContainer, virtualLandscape);;
      } else {
        GradedOut.showNORMALMessage("Parse: The command line is not valid!");
      }
    }
  }

}
