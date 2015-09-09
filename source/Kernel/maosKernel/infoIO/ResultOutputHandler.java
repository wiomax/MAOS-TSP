/**
 * Description: provide the capability for handling results
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jul 19, 2006
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */

package maosKernel.infoIO;

import java.io.File;
import java.io.RandomAccessFile;

import maosKernel.infoIO.runtime.CycleResult;
import maosKernel.infoIO.screen.MessageTags;
import Global.define.BasicTag;
import Global.methods.GlobalString;
import Global.system.io.GlobalFile;

public class ResultOutputHandler {
  String innerTag = "/";
  String seperTag = BasicTag.NULL_SEPERATE_TAG;
  String outerTag = "|";
  String cycleTag = "@";
//  String innerTag = " \t";
//  String seperTag = BasicTag.NULL_SEPERATE_TAG;
//  String outerTag = " ";
//  String cycleTag = " \t";
  
  private RandomAccessFile onlineResultFile = null;
  
  private boolean hasRunInfo = false;

  public ResultOutputHandler() {
  }

  public String setResultFile(String tempResultDir, String resultFileName) throws Exception {
    new File(tempResultDir).mkdirs();
    String outfileName = GlobalFile.getFileLocation(tempResultDir, resultFileName);
    onlineResultFile = new RandomAccessFile(outfileName, "rw");
    return outfileName;
  }

  private void writeBodyString(String message) throws Exception {
    onlineResultFile.writeBytes(message);
  }

  public void writeCommentString(String message) throws Exception {
    onlineResultFile.writeBytes(GlobalString.addHeadToAllLines(message, MessageTags.MSGTAG_COMMENT)+BasicTag.RETURN_TAG);
  }

  public void writeResultHeadInfo(CycleResult cycleResult) throws Exception {
    writeCommentString(GlobalString.serinize(cycleResult.getAllNames(), innerTag)+cycleTag+"NCycle");
  }

  public void writeRunInfo(CycleResult cycleResult) throws Exception {
    hasRunInfo = true;
    writeBodyString(GlobalString.serinize(cycleResult.getAllValues(), innerTag)+seperTag);
  }

  public void writeLastInfo(CycleResult cycleResult) throws Exception {
    if (hasRunInfo) {
      writeBodyString(outerTag+seperTag);
    }
    writeBodyString(GlobalString.serinize(cycleResult.getAllValues(), innerTag)+cycleTag+cycleResult.NCycle+BasicTag.RETURN_TAG);
    hasRunInfo = false;
  }
}

