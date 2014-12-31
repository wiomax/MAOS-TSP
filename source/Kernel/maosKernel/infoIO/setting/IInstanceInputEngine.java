/**
 * Description: file information for loading an instance
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 05, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.setting;

public interface IInstanceInputEngine {
  public String getInstancePath(String problemType);
  public String getFullInstanceFileName(String problemType, String instanceName) throws Exception;
  public String getInstanceName(String instanceFileName);
}
