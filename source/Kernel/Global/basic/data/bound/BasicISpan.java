/**
 * Description: provide an integer span, and the corresponding operations
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb. 03, 2006
 */

package Global.basic.data.bound;

import Global.basic.*;
import Global.define.*;

public class BasicISpan extends BasicObject {
  public static final int UNDETERMINED = GlobalValue.MININTEGER;

  private int startValue = UNDETERMINED;
  private int spanValue = 0;

  public BasicISpan() {
  }

  public BasicISpan(int val) {
    this.setFixBound(val);
  }

  public BasicISpan(int min, int max) {
    startValue = min;
    spanValue = max - min;
  }

  public void importBasicISpan(BasicISpan span) {
    startValue = span.startValue;
    spanValue = span.spanValue;
  }

  public void setUndeterminedStart() {
    startValue = UNDETERMINED;
  }

  public void setStartValue(int startValue) {
    this.startValue = startValue;
  }

  public void setSpanValue(int spanValue) {
    this.spanValue = spanValue;
  }

  public boolean isStartUndetermined() {
    return (startValue==UNDETERMINED);
  }

  public int getLength() {
    return spanValue;
  }

  public int getMinValue() {
    return startValue;
  }

  public int getMaxValue() {
    return startValue+spanValue;
  }

  public boolean isSatisfyCondition(int value) {
    if (startValue==UNDETERMINED) {
      return true;
    }
    if(value > startValue+spanValue || value < startValue) {
      return(false);
    }
    return(true);
  }

  public void moveStartValue(int value) {
    startValue += value;
  }

  public void moveSpanValue(int value) {
    spanValue += value;
  }

  public void setFixBound(int val) {
    startValue = val;
    spanValue = 0;
  }

  public boolean isContain(BasicISpan basicISpan) {
    if (getMinValue() > basicISpan.getMinValue()) return false;
    if (getLength()< basicISpan.getLength()) return false;
    return true;
  }
}
