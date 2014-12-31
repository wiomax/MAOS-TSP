/**
 * Description: provide the out information in different levels
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 11, 2004
 */

package Global.system;

public class GradedOut {

  public static final int TRIVIAL = 0;
  public static final int NORMAL = 1;
  public static final int IMPORTANT = 2;
  public static final int CRITICAL = 3;
  public static final int EXTREME = 4;

  private static int level = NORMAL;

  public static void setShowLevel(int iLevel) {
    level = iLevel;
  }

  public static void showTRIVIALMessage(String msg) {
    showMessage(TRIVIAL, msg);
  }

  public static void showNORMALMessage(String msg) {
    showMessage(NORMAL, msg);
  }

  public static void showIMPORTANTMessage(String msg) {
    showMessage(IMPORTANT, msg);
  }

  public static void showCRITICALMessage(String msg) {
    showMessage(CRITICAL, msg);
  }

  public static void showEXTREMEMessage(String msg) {
    showMessage(EXTREME, msg);
  }

  public static void showMessage(int iLevel, String msg) {
    if (iLevel>=level) System.out.println(msg);
  }
}
