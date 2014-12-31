/**
 * Description: execute one trial in cycles.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 26, 2007
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 *
 */
package maosKernel;


public interface IExecCyclesEngine {

  public void execOneRun(ICycleInfoEngine cycleCheckEngine) throws Exception;
}

