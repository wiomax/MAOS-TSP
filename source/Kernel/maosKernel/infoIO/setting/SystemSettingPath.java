/**
 * Description: provide the system setting paths
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 02, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel.infoIO.setting;

import Global.define.*;
import Global.methods.*;
import Global.system.io.*;
import Global.util.parser.*;
import Global.basic.*;
import Global.basic.nodes.*;
import Global.basic.nodes.utilities.*;

public class SystemSettingPath {
  private static UtilSetNode usedFileCollection = new UtilSetNode();

  // file system
  public static String RootSettingPath = "setting";

  public static final String DefaultSettingFile = "default.par";
  public static final String DefaultScheduleFile = "schedule.txt";
  public static final String DefaultPrjIOFile = "projectIO.par";
  public static final String DefaultSwarmFile = "swarm.par";
  public static final String DefaultTopologyFile = "topology.par";
  public static final String DefaultAnalysisFile = "analysis.par";

  public static final String KernelPath = "kernel";

  public static final String DefaultSolverName = "default";
  public static final String SolverPath = "solver";
  public static final String PredefinedPath = "predefined";
  public static final String TemplatePath = "template";
  
  public static final String OneLineSection = "OneLine";
  
  public static final String MAOSDIR_PROPERTY = "SET_PATH";
  
  public static String getInitializerName(String problemType) {
    return "implement."+problemType+".RealMAOSInitializer";
  }

  public static String getTimeScheduleFileName() {
    return RootSettingPath+BasicTag.FILE_SEP_TAG+DefaultScheduleFile;
  }

  public static void initRootSettingPath() {
    String path = System.getProperty(MAOSDIR_PROPERTY);
    if (path!=null) RootSettingPath = path;
  }

  public static String getTopologyFileName() {
    return GlobalFile.getFileLocation(getKernelSettingPath(), DefaultTopologyFile);
  }

  public static String getSpecSettingPath(String problemType) {
    return GlobalFile.getFileLocation(RootSettingPath, problemType);
  }

  public static String getKernelSettingPath() {
    return GlobalFile.getFileLocation(RootSettingPath, KernelPath);
  }

//  public static String getCommonSettingPath() {
//    return GlobalFile.getFileLocation(getRootSettingPath(), CommonPath);
//  }

  public static String getFullSolverFileName(String problemType, String solverName) {
    return GlobalFile.getFileLocation(
        SystemSettingPath.getSpecSettingPath(problemType),
        SystemSettingPath.getSolverFileName(solverName));
  }

  public static String getSolverFileName(String solverName) {
    if (solverName.trim().length()==0) solverName = DefaultSolverName;
    return GlobalFile.getFileLocation(SolverPath, solverName);
  }

  public static void submitUsedFile(String fileType, String fileName) {
    if (!GlobalString.isNull(fileName)) usedFileCollection.initUtility(new BasicUtility(fileType, fileName));
  }

  public static String getAllUsedFilesString() {
    String str = "";
    String subStr = "";
    for (int i=0; i<usedFileCollection.getUtilitiesSize(); i++) {
      subStr = usedFileCollection.getUtilityAt(i).getSummary();
      if (subStr.length()>0) {
        str += subStr+BasicTag.RETURN_TAG;
      }
    }
    return str;
  }

  private static BasicMap getSubSectionContent(String sectionString, String fileContent) throws Exception {
    BasicMap[] sections = SectionParser.parseSections(fileContent);
    int index = StringSearch.getSelectedIndex(sections, sectionString);
    if (index != -1) return sections[index];
    else throw new Exception();
  }

  private static String getGenericFileContent(String suffixString, String fileString, String pathString) throws Exception {
    String fileName= GlobalFile.getFileLocation(suffixString, pathString);
    fileName = GlobalFile.getFileLocation(fileName, fileString);
    return GlobalFile.getStringFromFile(fileName);
  }

  public static BasicMap getSectionContent(String problemType, String fileString, String sectionString, String pathString) throws Exception {
    try {
      String fileContent = getGenericFileContent(getSpecSettingPath(problemType), fileString, pathString);
      return getSubSectionContent(sectionString, fileContent);
    } catch (Exception e) {
      String fileContent = getGenericFileContent(getKernelSettingPath(), fileString, pathString);
      return getSubSectionContent(sectionString, fileContent);
    }
  }
  
  public static String getPreSectionConent(String problemType, String fileString, String pathString) throws Exception {
    String content = "";
    try {
      String fileContent = getGenericFileContent(getSpecSettingPath(problemType), fileString, pathString);
      try {
        BasicMap sectContent = getSubSectionContent(OneLineSection, fileContent);
        content += sectContent.value;
      } catch(Exception e0) {
        fileContent = SectionParser.toSectionLine(OneLineSection)+BasicTag.RETURN_TAG+fileContent;
        BasicMap sectContent = getSubSectionContent(OneLineSection, fileContent);
        content += sectContent.value;
      }
    } catch(Exception e) {}

    try {
      String fileContent = getGenericFileContent(getKernelSettingPath(), fileString, pathString);
      try {
        BasicMap sectContent = getSubSectionContent(OneLineSection, fileContent);
        content += sectContent.value;
      } catch(Exception e0) {
        fileContent = SectionParser.toSectionLine(OneLineSection)+BasicTag.RETURN_TAG+fileContent;
        BasicMap sectContent = getSubSectionContent(OneLineSection, fileContent);
        content += sectContent.value;
      }
    } catch(Exception e) {}
    return content;
  }

  private static BasicMap getSubFileContent(String suffixString, String fileString, String pathString) throws Exception {
    BasicMap basicMap = new BasicMap();
    basicMap.setMap(fileString, getGenericFileContent(suffixString, fileString, pathString));
    return basicMap;
  }

  public static BasicMap getFileContent(String problemType, String fileString, String pathString) throws Exception {
    try {
      return getSubFileContent(getSpecSettingPath(problemType), fileString, pathString);
    } catch (Exception e) {
      return getSubFileContent(getKernelSettingPath(), fileString, pathString);
    } 
  }
}

