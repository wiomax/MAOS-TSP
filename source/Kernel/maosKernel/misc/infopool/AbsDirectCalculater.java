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

public abstract class AbsDirectCalculater {
  abstract public Object calculate(Object[] inputParams);
}
