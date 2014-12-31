/**
 * Description: The OS-related variables and methods.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct. 24, 2002
 * Xiaofeng Xie    Apr. 12, 2003    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.system;

import java.net.*;

import Global.define.*;
import Global.methods.*;

public class OSRelativedMethods {
  static public final String OS_WIN_KEY = "win";
  static public final String OS_LINUX_KEY = "linux";
  static public final String OS_UNIX_KEY = "unix";

  static public final String PROCESS_HANDLER_UNIX = "";

  static public String getOSName() {
    return System.getProperty("os.name");
  }

  static public String getFirstHostName() {
    String hostName = getHostName();
    String[] names = GlobalString.tokenize(hostName, ".");
    return names[0];
  }

  static public String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      return "";
    }
  }
  static public String getDiskName(String absolutePath) {
    String osName = getOSName();
    if(osName.substring(0, 3).equalsIgnoreCase(OS_WIN_KEY)) {
      int colonIndex = absolutePath.indexOf(":");
      return absolutePath.substring(0, colonIndex+1)+BasicTag.RETURN_TAG;
    } else {
      return "";
    }
  }

  static public boolean isInWindows() {
    String osName = getOSName();
    return (osName.substring(0, 3).equalsIgnoreCase(OS_WIN_KEY));
  }

  static public String chmodToExec(String fileName) {
    if(!isInWindows()) {
      return "chmod +x "+fileName+BasicTag.RETURN_TAG;
    }
    return "";
  }

  static public String getTextEditorName() {
    String osName = getOSName();
    if(osName.substring(0, 3).equalsIgnoreCase(OS_WIN_KEY)) {
      return "notepad";
    } else {
      return "textedit";
    }
  }
}

