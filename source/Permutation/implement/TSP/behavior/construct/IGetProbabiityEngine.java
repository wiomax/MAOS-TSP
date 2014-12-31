/**
 * Description: The interface for get probability for next node
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 9, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;


public interface IGetProbabiityEngine {
  public double[] getProbabilityArrayAt(int index);
  public boolean isPartialMode();
}
