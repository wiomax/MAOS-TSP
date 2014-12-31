/**
 * Description: The interface for getting enumurate value.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 18, 2004
 *
 * @version 1.0
 */


package Global.basic.data.enumerate;

public abstract class AbstractEnumValue {
  public static final int INVALID_ENUM = Integer.MAX_VALUE;

  private int value = INVALID_ENUM;

  public boolean isValid(int value) {
    return (value!=INVALID_ENUM);
  }

  public boolean isValidValue() {
    return isValid(value);
  }

//  abstract public int getEnumValue(int index);

  public boolean setValue(int value) {
    if (isValid(value)) {
      this.value = value;
      return true;
    }
    return false;
  }

  public int getValue() {
    return value;
  }
}
