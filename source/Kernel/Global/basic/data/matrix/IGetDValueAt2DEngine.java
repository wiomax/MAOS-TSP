/**
 * Description: The interface for get data from a 2D Matrix
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 22, 2005
 *
 * @version 1.0
 */


package Global.basic.data.matrix;

public interface IGetDValueAt2DEngine {
  public static final double INVALID = Double.MAX_VALUE;
  public double getValueAt(int nodeI, int nodeJ);
}
