/**
 * Description: provide the I/O paths for a project
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 02, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.setting;

import java.io.*;

import Global.define.*;
import Global.methods.*;
import Global.system.io.*;
import Global.basic.nodes.*;
import Global.basic.nodes.utilities.*;

public class ProjectIOPath extends UtilSetNode implements ISolutionIOEngine, IInstanceInputEngine {
  //instance
  private static String defaultProjectPath = "";
  private static String defaltRootProjectPath = "myprojects";
  private final String InstancePath = "instance";
  private String[] defaultProblemSuffixes = {".dat"};

  //solution
  private final String SolutionPath = "solution";
  private String solutionSuffix = ".sln";
  private String[] OptimumFlags = {".opt"};

  //results
  public String ResLABEL = "res";
  public String ResHOME = "temp";

  public ProjectIOPath() {
  }

  public void initUtilities() {
    super.initUtilities();
    initUtility(new BasicUtility("defaultProjectPath", defaultProjectPath));
    initUtility(new BasicUtility("defaltRootProjectPath", defaltRootProjectPath));

    initUtility(new StringArrayUtility("defaultProblemSuffixes", defaultProblemSuffixes));
    initUtility(new BasicUtility("solutionSuffix", solutionSuffix));
    initUtility(new StringArrayUtility("OptimumFlags", OptimumFlags));

    initUtility(new BasicUtility("ResLABEL", ResLABEL));
    initUtility(new BasicUtility("ResHOME", ResHOME));
  }

  public void shortcutInit() throws Exception {
    super.shortcutInit();
    defaultProjectPath = getValue("defaultProjectPath").toString();
    defaltRootProjectPath = getValue("defaltRootProjectPath").toString();

    defaultProblemSuffixes = ((StringArrayUtility) getUtility("defaultProblemSuffixes")).getDefaultValue();
    solutionSuffix = getValue("solutionSuffix").toString();
    OptimumFlags = ((StringArrayUtility) getUtility("OptimumFlags")).getDefaultValue();

    ResLABEL = getValue("ResLABEL").toString();
    ResHOME = getValue("ResHOME").toString();
  }
  
  public String getProjectPath() {
    return defaltRootProjectPath;
  }

  public static String getSpecProjectPath(String problemType) {
    if (GlobalString.isNull(defaultProjectPath)) return GlobalFile.getFileLocation(defaltRootProjectPath, problemType);
    else return defaultProjectPath.trim();
  }

  public String getInstancePath(String problemType) {
    return GlobalFile.getFileLocation(getSpecProjectPath(problemType), InstancePath);
  }

  public String getFullInstanceFileName(String problemType, String instanceName) throws Exception {
    return GlobalFile.getValidFileName(instanceName, new String[]{getInstancePath(problemType)}, defaultProblemSuffixes);
  }

  public String getInstanceName(String instanceFileName) {
    return  GlobalFile.getFilePrefix(GlobalFile.extractFileName(instanceFileName), defaultProblemSuffixes);
  }

  public String getOptimalSolutionFileName(String problemType, String instanceName, boolean isCreate) {
    String[] realOptFlags = ObjectMatrix.add2StringArray(new String[] {""}, OptimumFlags);
    for (int i=0; i<realOptFlags.length; i++) {
      String fullSolFileName = getNormalSolutionFileName(problemType, instanceName, realOptFlags[i]);
      File solFile = new File(fullSolFileName);
      if (solFile.isFile()) return fullSolFileName;
      if (isCreate) {
        try {
          solFile.createNewFile();
          return fullSolFileName;
        }
        catch (Exception e) {}
      }
    }
    return null;
  }

  public String getNormalSolutionFileName(String problemType, String instanceName, String label) {
    return getNormalSolutionFileName(problemType, instanceName, label, solutionSuffix);
  }

  private String getNormalSolutionFileName(String problemType, String instanceName, String label, String solutionSuffix) {
    return GlobalFile.getFileName(GlobalFile.getFileName(getSolutionPath(problemType)+BasicTag.FILE_SEP_TAG+instanceName, label), solutionSuffix);
  }

  public String getSolutionPath(String problemType) {
    return GlobalFile.getFileLocation(getSpecProjectPath(problemType), SolutionPath);
  }

  public FilenameFilter getSolutionFilter(final String instanceName) {
    return new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return (name.startsWith(instanceName + ".") && name.endsWith(solutionSuffix));
      }
    };
  }
}

