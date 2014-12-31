/**
 * Description: for loading an instance of problem according to ProblemSettings,
 *  which refers to a file by the parameter "Problem"
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 12, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.instance;

import Global.methods.*;
import Global.system.io.*;
import Global.exception.*;

import maosKernel.represent.problem.*;
import maosKernel.infoIO.setting.*;

public abstract class AbsFileInstanceLoader extends AbsInstanceLoader {
  private IInstanceInputEngine instanceInputEngine;

  public AbsFileInstanceLoader() {};

  public void setInstanceInputEngine(IInstanceInputEngine instanceInputEngine) {
    this.instanceInputEngine = instanceInputEngine;
  }

  protected AbsProblemData loadPrimaryProblem(InternelProblemSettings problemSettings) throws Exception {
    String problemName = null;
    try {
      problemName = TypeConverter.toString(problemSettings.getValue("Problem"));
    } catch (Exception e) {
      throw new ParamNotFoundException("Problem");
    }
    problemName = instanceInputEngine.getInstanceName(problemName);
    return initInstanceData(problemSettings.getKey(), problemName, instanceInputEngine);
  }
  protected abstract AbsProblemData readProblem(String content) throws Exception;

  private AbsProblemData readFormattedProblem(String content) throws BasicFileFormatException {
    if (GlobalString.isNull(content)) throw new BasicFileFormatException("No content");
    try {
      AbsProblemData problemData = readProblem(content);
      if (problemData == null) throw new BasicFileFormatException ("Wrong Problem Data");
      return problemData;
    } catch (Exception e) {
      throw new BasicFileFormatException(e.getMessage());
    }
  }

  protected AbsProblemData initInstanceData(String problemType, String problemName, IInstanceInputEngine instanceInputEngine) throws Exception {
    String validFileName = instanceInputEngine.getFullInstanceFileName(problemType, problemName);
    SystemSettingPath.submitUsedFile("$INS_FILE", validFileName);
    String content = GlobalFile.getStringFromFile(validFileName);
    return readFormattedProblem(content);
  }
}

