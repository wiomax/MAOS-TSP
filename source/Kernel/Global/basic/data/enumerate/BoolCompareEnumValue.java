/**
 * Description: Global value for comparison.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 12, 2005
 *
 * @version 1.0
 */


package Global.basic.data.enumerate;

public class BoolCompareEnumValue extends AbstractEnumValue {
  public static final int BETTER = 1;
  public static final int WORSE = 0;

  public boolean isValid(int val) {
    return (val>-1&&val<2);
  }

}
