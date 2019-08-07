/**
 * Description: As the file format is not correct.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 06, 2008
 *
 * @version 1.0
 */

package Global.exception;


@SuppressWarnings("serial")
public class BasicFileFormatException extends Exception {

  public BasicFileFormatException(String paramName) {
    super(paramName);
  }
  
  public String getMessage() {
    return "Error format: \""+super.getMessage()+ "\"";
}
}

