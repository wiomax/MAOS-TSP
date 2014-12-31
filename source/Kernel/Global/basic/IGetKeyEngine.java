/**
 * Description: The interface for get the key of an object.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Feb 20, 2004
*/


package Global.basic;

public abstract interface IGetKeyEngine {
  public static final String ANY_KEY = "_ANY_KEY";
  abstract public String getKey();
  public boolean isSameKey(String outKey);
}
