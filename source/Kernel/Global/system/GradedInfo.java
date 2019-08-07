/**
 * Description: provide the information in different grades
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 11, 2004
 */

package Global.system;

import java.util.*;
import Global.methods.*;

public class GradedInfo {
  public static Vector<String> exclusiveInfo = new Vector<String>();

  private Object excObj;
  private String addMSG = "";
  public static final int NORMAL = 0;
  public static final int INFO = 1;
  public static final int WARNING = 2;
  public static final int ERROR = 3;
  public static final int FATAL = 4;

  private int level = WARNING;

  protected static int showLevel = WARNING;
  protected static int throwLevel = ERROR;

  public GradedInfo(String msg) {
    addMSG = msg;
  }

  public GradedInfo(Object obj, String msg) {
    this(msg);
    excObj =obj;
  }

  public GradedInfo(Object obj, int iLevel, String msg) {
    this(obj, msg);
    this.level = iLevel;
  }

  public GradedInfo(Object obj, int iLevel, String msg, String exclusiveString) {
    this(obj, iLevel, msg);
  }

  public GradedInfo(Object obj, int iLevel) {
    this(obj, iLevel, "");
  }

  public static void setShowLevel(int iLevel) {
    showLevel = iLevel;
  }

  public static void setThrowLevel(int iLevel) {
    throwLevel = iLevel;
  }

  private String getLevelMsg() {
    switch (level) {
      case NORMAL:
        return "NORMAL";
      case INFO:
        return "INFO";
      case WARNING:
        return "WARNING";
      case ERROR:
        return "ERROR";
      case FATAL:
        return "FATAL";
      default:
        return "Level "+ level;
    }
  }

  private String getObjMsg() {
    if (excObj != null) {
      return " \""+excObj.getClass().getName();
    } else {
      return "";
    }
  }

  public String getMessage() {
    return "# ["+getLevelMsg()+"]"+ getObjMsg()+"\": "+addMSG;
  }

  public void showMessage(String exclusiveString) throws Exception {
    int index = StringSearch.getSelectedIndex(GradedInfo.exclusiveInfo, exclusiveString);
    if(index == -1) {
      GradedInfo.exclusiveInfo.add(exclusiveString);
      showMessage();
      System.out.println("# All similar messages are hidden later.");
    }
  }

  public void showMessage() throws Exception {
    String msg = this.getMessage();
    if (this.level>=throwLevel) {
      System.out.println(msg);
      throw new Exception();
    }
    if (this.level>=showLevel) {
      System.out.println(msg);
    }
  }
}
