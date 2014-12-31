/**
 * Description: For set information to Log file with given timer
 *
 * @Author        Create/Modi     Note
 * Xiaofeng Xie   Feb. 14, 2003
 *
 * @version 1.0
 */

package Global.system.io;
import java.util.*;
import java.io.*;

public class DataLogTimerTask extends TimerTask {
  RandomAccessFile logFile;
  long loc = 0;

  public DataLogTimerTask(String logFileName) throws Exception {
    logFile = new RandomAccessFile(logFileName,"r");
  }

  public void run() {
    flush();
  }

  public void flush() {
    try {
      long length = logFile.length();
      byte[] data = new byte[(int)(length-loc)];
      logFile.seek(loc);
      logFile.read(data);
      String fileStr = new String(data);
      int newLoc = fileStr.lastIndexOf("\n");
      if(newLoc!=-1) {
        System.out.print(fileStr.substring(0, newLoc+1));
        loc += newLoc+1;
      }
    }catch (Exception e){}
  }
}

