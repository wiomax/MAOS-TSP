/**
 * Description: For calculating the output element with directCalculator and calculationPool.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 24, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel.misc.infopool;

public class IndirectCalculater {
  public static Object calculate(String name, Object[] inputParams, AbsDirectCalculater directCalculator, CalculationPool calculationPool) {
    Object object = calculationPool.getOutputResult(name, inputParams);
    if (object==null) {
      object = directCalculator.calculate(inputParams);
      calculationPool.submitElement(name, object, inputParams);
    }
    return object;
  }
}
