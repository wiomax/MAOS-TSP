/**
 * Description: As the parameter is not found.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 22, 2000    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.exception;


@SuppressWarnings("serial")
public class ParamNotFoundException extends Exception {

  public ParamNotFoundException(String paramName) {
    super(paramName);
  }
  
  public String getMessage() {
    return "Cannot find Parameter \""+super.getMessage()+ "\"";
}
}

