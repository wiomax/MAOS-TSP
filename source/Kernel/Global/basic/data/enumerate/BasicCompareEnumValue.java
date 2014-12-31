/**
 * Description: Global value for comparison.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 15, 2002
 * Xiaofeng Xie    Feb 18, 2004
 *
 * @version 1.0
 */


package Global.basic.data.enumerate;

public class BasicCompareEnumValue extends AbstractEnumValue {
  public static final int LARGE_THAN = 1;
  public static final int EQUAL_TO = 0;
  public static final int LESS_THAN = -1;

  public BasicCompareEnumValue() {}

  public BasicCompareEnumValue(int value) {
    this.setValue(value);
  }

  public boolean isValid(int val) {
    return (val>-2&&val<2);
  }
}
