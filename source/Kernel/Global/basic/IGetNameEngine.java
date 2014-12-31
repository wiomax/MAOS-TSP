/**
 * Description: The interface for get the name of an object.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 15, 2004
 */


package Global.basic;

public interface IGetNameEngine {
  public static final String ANY_NAME = "_ANY_NAME";
  public String getName();
  public boolean isSameName(String outName);
}
