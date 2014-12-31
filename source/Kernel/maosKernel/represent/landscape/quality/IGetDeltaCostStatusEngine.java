/**
 * Description: .
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 25, 2008
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */


package maosKernel.represent.landscape.quality;

public interface IGetDeltaCostStatusEngine {
  public final static int INVALID = -3;
  public final static int UNKNOWN = -2;
  public final static int WORSE = -1;
  public final static int UNCHANGED = 0;
  public final static int BETTER = 1;
  public int getQualityImprovingFlag();
}
