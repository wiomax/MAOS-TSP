/**
 * Description: For calculating the multiple mean values of results.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Sep 27, 2008
 *
 * @version 1.0
 * @Since MAOS1.0
 */

package Global.util.stat;

import java.io.*;
import Global.methods.*;
import Global.system.io.*;

public class BestResultStat {
  static String[] bestFiles = new String []{
    "dsj1000",
    "pr1002",
    "u1060",
    "vm1084",
    "pcb1173",
    "d1291",
    "rl1304",
    "rl1323",
    "nrw1379",
    "fl1400",
    "u1432",
    "fl1577",
    "d1655",
    "vm1748",
    "u1817",
    "rl1889",
    "d2103",
    "u2152",
    "u2319",
    "pr2392"
  };

  public BestResultStat(String[] args) throws Exception {
    String outPathName ="";
    if(args.length==1) {
      outPathName = args[0];
      execAll(outPathName);
    } 
  }

  private void execAll(String workDir) throws Exception {
    File fileDir = new File(workDir);
    if (!fileDir.isDirectory()) return;
    try {
      exec(workDir);
    } catch (Exception e) {}
    File[] files = fileDir.listFiles();
    for (int i=0; i<files.length; i++) {
      execAll(files[i].getAbsolutePath());
    }
  }

  public void exec(String outPathName) throws Exception {
    if(outPathName!="") {
      File file = new File(outPathName);
      if (!file.isDirectory()) return;
      File[] files = file.listFiles();
      if (files.length<4*bestFiles.length) return;
      String dataLogFile = GlobalFile.getFileLocation(outPathName,"_summary_best.txt");
      RandomAccessFile resultDataFile = new RandomAccessFile(dataLogFile,"rw");
      for (int i=0; i< bestFiles.length; i++) {
        String fileName = GlobalFile.getFileLocation(outPathName, "best."+bestFiles[i]);
        file = new File(fileName);
        String[] meaningfulLines = GlobalString.getMeaningfulLines(GlobalFile.getStringFromFile(fileName));
        resultDataFile.writeBytes(meaningfulLines[meaningfulLines.length-1]+"\n");
      }
      resultDataFile.close();
    }
  }

/**
  * the main process.
  */
  public static void main(String[] args) {
    try  {
      new BestResultStat(args);
      System.exit(0);
    }
    catch (Exception e) {
      System.out.println("ERROR: "+e.toString());
      System.exit(-1);
    }
  }

}



