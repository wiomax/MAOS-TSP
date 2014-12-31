/**
 * Description: Holds the parameters.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */
package maosKernel.infoIO.setting;

import Global.define.*;
import Global.methods.*;
import Global.system.io.*;
import Global.system.*;
import Global.basic.nodes.*;

public class BasicParamHolder {
  public ProjectIOPath prjIOPath = new ProjectIOPath();
  private CMDLineProblemSettings problemSettings = new CMDLineProblemSettings();
  private SwarmSettings swarmSettings = new SwarmSettings();

  public BasicParamHolder() {
    prjIOPath.initUtilities();
    problemSettings.initUtilities();
    swarmSettings.initUtilities();
  }

  public CMDLineProblemSettings getProblemSettings() {
    return this.problemSettings;
  }

  public SwarmSettings getSwarmSettings() {
    return swarmSettings;
  }

  public void loadDefaultFiles(String problemType) throws Exception {
    loadDefaultFile(prjIOPath, problemType, SystemSettingPath.DefaultPrjIOFile);
    loadDefaultFile(swarmSettings, problemType, SystemSettingPath.DefaultSwarmFile);
  }

  private void loadDefaultFile(UtilSetNode utilSetNode, String problemType, String fileName) throws Exception {
    String basicSettingFile = SystemSettingPath.getKernelSettingPath()+BasicTag.FILE_SEP_TAG+fileName;
    readDefaultSettingFile(utilSetNode, basicSettingFile);
    String specSettingFile = SystemSettingPath.getSpecSettingPath(problemType)+BasicTag.FILE_SEP_TAG+fileName;
    readDefaultSettingFile(utilSetNode, specSettingFile);
    utilSetNode.shortcutInit();
  }

  private void readDefaultSettingFile(UtilSetNode utilSetNode, String fileName) {
    try {
      String content = GlobalFile.getStringFromFile(fileName);
      String[] lines = GlobalString.getMeaningfulLines(content);
      parserInputs(utilSetNode, lines, false);
    }
    catch (Exception e) {
      System.out.println("*INFO: "+e.getMessage());
    }
  }

  public String getResultFileDir(String problemType) {
    String str = problemType + "_"+swarmSettings.getBasicResultDir()+"_"+GlobalFile.extractFileName(System.getProperty("user.dir"));
    return GlobalFile.getFileLocation(prjIOPath.ResHOME, str);
  }

  public String getResultFileName() {
    return problemSettings.getSummary()+"_"+OSRelativedMethods.getFirstHostName()+"_"+System.currentTimeMillis()+".log";
  }

  public void parseSwarmSettings(String[] args) throws Exception {
    parserInputs(swarmSettings, args, true);
    swarmSettings.shortcutInit();
  }

  /**
   * Support the command line inputs, format at follows: Name=value
   */
  private void parserInputs(UtilSetNode utilSetNode, String[] args, boolean isPrint) {
    for(int i=0; i<args.length; i++) {
      args[i] = args[i].trim();
      if (GlobalString.isNull(args[i])) continue;
      if (isPrint) System.out.println("Reading: "+args[i]);
      String[] vals = GlobalString.splitFirst(args[i], BasicTag.EQUAL_TAG);
      if(vals.length > 1) {
        try {
          if (!utilSetNode.setValue(vals[0], vals[1])) {
            if(isPrint) System.out.println("*WARN: No such parameter: "+vals[0]);
          }
        } catch (Exception e) {
          if(isPrint)System.out.println(" *WARN: "+e.getMessage()+", use default value");
        }
      } else {
        if(isPrint) System.out.println(" *WARN: not a valid parameter");
      }
    }
  }

}

