/**
 * Description: file information for solution I/O
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 05, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.setting;

import java.io.*;

public interface ISolutionIOEngine {
  public String getSolutionPath(String problemType);
  public String getNormalSolutionFileName(String problemType, String instanceName, String label);
  public String getOptimalSolutionFileName(String problemType, String instanceName, boolean isCreate);
  public FilenameFilter getSolutionFilter(final String instanceName);
}
