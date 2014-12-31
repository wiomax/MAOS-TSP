/**
 * Description: provide the prefix strings for screen outputing messages
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Nov 02, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO.screen;

import Global.define.*;

public class MessageTags {
  public static final String MSGTAG_ACTION_SECT = BasicTag.RETURN_TAG+"=> ";
  public static final String MSGTAG_ACTION_NORM = BasicTag.RETURN_TAG+"-> ";
  public static final String MSGTAG_PLAIN = BasicTag.RETURN_TAG+"@ ";
  public static final String MSGTAG_COMMENT ="# ";
  public static final String MSGTAG_ERROR = "#ERROR: ";
  public static final String MSGTAG_WARN = "#WARN: ";
  public static final String MSGTAG_INFO = "#INFO: ";
}

