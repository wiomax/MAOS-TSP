/**
 * Description: defines the basic informations of MAOS.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel;

import Global.system.GradedOut;

public class ProductDefinition {
  public static final String PRODUCTNAME = "MAOS";
  public static final String PRODUCTDETAILNAME = "Multiagent Optimization System";
  public static final String SubPRODUCTNAME = "The Mini Series";
  public static final String PRODUCTVERSION = "M01.00.02";
  public static final String PRODUCTCOPYRIGHT = "Open Source Softeware under GPL License 3.0";
  public static final String PRODUCTOR = "Xiao-Feng Xie";
  public static final String HOME_ADDR = "http://www.adaptivebox.net/doi/MAOS";
  public static final String EMAILADDR = "xiexiaofeng@tsinghua.org.cn";
  
  public static void printMAOSHead() {
    GradedOut.showEXTREMEMessage("**********************************************");
    GradedOut.showEXTREMEMessage("* " + PRODUCTNAME + " ("+PRODUCTDETAILNAME + ")");
    GradedOut.showEXTREMEMessage("* -> " + SubPRODUCTNAME+" "+PRODUCTVERSION);
    GradedOut.showEXTREMEMessage("* URL: " + HOME_ADDR);
    GradedOut.showEXTREMEMessage("* " + PRODUCTCOPYRIGHT);
    GradedOut.showEXTREMEMessage("**********************************************");
  }
}
