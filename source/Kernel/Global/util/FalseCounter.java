/**
 * Description: count the number of repeat FALSE values.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Mar 17, 2008    
 *
 * @version 1.0
 */

package Global.util;

public class FalseCounter {
  private int number = 0;
  
  public int getNumber() {
    return number;
  }
  
  public void clear() {
    number = 0;
  }

  public void submitValue(boolean newValue) {
    if (newValue) {
      number = 0;
    } else {
      number++;
    }
  }
}

