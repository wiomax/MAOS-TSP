/**
 * Description: operations for the GUI.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 22, 2001
 *
 * @version 1.0
 * @Since MAOS1.0
 */

package Global.gui;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import Global.methods.*;
import Global.define.*;

public class WndManager {
/**
  * Show specified dialog on the specified Window.
  * @param      frame   parent window that dialog be shown.
  * @param      dlg   the dialog to be shown.
  */
  public static void showDialog(Window frame, JDialog dlg) {
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation(Math.max((frmSize.width - dlgSize.width) / 2 + loc.x,0),
                              Math.max((frmSize.height - dlgSize.height) / 2 + loc.y,0));
    dlg.setResizable(false);
    dlg.show();
  }

/**
  * Show specified dialog on the parent window of itself.
  * @param      dlg   the dialog to be shown.
  */
  public static void showDialog(JDialog dlg) {
    Window frame = (Window)dlg.getParent();
    Dimension dlgSize = dlg.getSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation(Math.max((frmSize.width - dlgSize.width) / 2 + loc.x,0),
                              Math.max((frmSize.height - dlgSize.height) / 2 + loc.y,0));
    dlg.setResizable(false);
    dlg.show();
  }

/**
  * Show a specified Window on the center of screen.
  * @param      jWnd   window to be shown.
  */
  public static void showWndInScreenCenter(Window jWnd) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension logoWndSize = jWnd.getSize();
    if (logoWndSize.height > screenSize.height)
      logoWndSize.height = screenSize.height;
    if (logoWndSize.width > screenSize.width)
      logoWndSize.width = screenSize.width;
    jWnd.setLocation((screenSize.width - logoWndSize.width) / 2, (screenSize.height - logoWndSize.height) / 2);
    jWnd.setVisible(true);
  }

/**
  * Show a specified Window on the center of screen.
  * @param      jWnd   window to be shown.
  */
  public static void showWndInScreenEdge(Window jWnd) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension logoWndSize = jWnd.getSize();
    if (logoWndSize.height > screenSize.height)
      logoWndSize.height = screenSize.height;
    if (logoWndSize.width > screenSize.width)
      logoWndSize.width = screenSize.width;
    jWnd.setLocation((screenSize.width - logoWndSize.width), (screenSize.height - logoWndSize.height)/2);
    jWnd.setVisible(true);
  }

  public static void showDialogInScreenCenter(JDialog dlg) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dlgSize = dlg.getSize();
    Dimension frmSize = screenSize.getSize();
    dlg.setLocation(Math.max((frmSize.width - dlgSize.width) / 2,0),
                              Math.max((frmSize.height - dlgSize.height) / 2 ,0));
    dlg.setResizable(false);
    dlg.show();
  }

  public static void showMesssages(JFrame parentFrame, Vector msgs)  {
    int upperLimit = 25;
    if(msgs.size()!=0) {
      if(msgs.size()>upperLimit) {
        msgs.setSize(upperLimit);
        msgs.add("Too many messages, only "+ upperLimit+ " of them are shown.");
      }

      String[] txtMsgs = ObjectMatrix.convert1DVectorToStringArray(msgs);
      for(int i=0; i<Math.min(txtMsgs.length, 25); i++) {
        txtMsgs[i] = (i+1)+"). " +txtMsgs[i];
      }
      JOptionPane.showMessageDialog(parentFrame, GlobalString.serinize(txtMsgs, BasicTag.RETURN_TAG));
    }
  }
}
