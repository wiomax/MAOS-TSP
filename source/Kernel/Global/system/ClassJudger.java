/**
 * Description: judging on Classes
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 22, 2006
 *
 * @version 1.0
 */

package Global.system;


public class ClassJudger {

  public static boolean isCompatiable (Object object, String className) throws Exception {
    return Class.forName(className).isInstance(object);
  }

  public static boolean isCompatiable(Object object, String[] classNames) throws Exception {
    for (int i=0; i<classNames.length; i++) {
      if (!isCompatiable(object, classNames[i])) return false;
    }
    return true;
  }
}
