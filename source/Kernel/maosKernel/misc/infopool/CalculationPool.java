/**
 * Description: For storing temporary I/O mapping information.
 * Usually used as the output element needs a heavy time to be calculated.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 24, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.misc.infopool;

import java.util.*;

public final class CalculationPool {
  private Hashtable<String, IOMappingObject> ioMappingPool = new Hashtable<String, IOMappingObject>();

  public CalculationPool() {
  }

  public void clear() {
    ioMappingPool.clear();
  }
  
  public Object getOutputResult(String name, Object[] inputParams) {
    Object getElement = ioMappingPool.get(name);
    if (getElement == null) return null;
    IOMappingObject ioElement = (IOMappingObject)getElement;
    if (inputParams.length != ioElement.inputParams.length) return null;
    for (int i=0; i<inputParams.length; i++) {
      if (!inputParams[i].equals(ioElement.inputParams[i])) {
        return null;
      }
    }
    return ioElement.outputResult;
  }
  
  public void submitElement(String name, Object outputResult, Object[] inputParams) {
    IOMappingObject ioElement = new IOMappingObject();
    ioElement.outputResult = outputResult;
    ioElement.inputParams = inputParams;
    ioMappingPool.put(name, ioElement);
  }
}
