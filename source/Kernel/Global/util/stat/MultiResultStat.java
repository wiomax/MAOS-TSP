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
import java.util.*;
import Global.util.*;
import Global.define.*;
import Global.methods.*;
import Global.system.io.*;

public class MultiResultStat {

  public MultiResultStat(String[] args) throws Exception {
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
      String[] fileNames = file.list();
      Arrays.sort(fileNames);
      if (fileNames==null) {
        return;
      }
      int fileNum  = 0;
      for(int i=0; i<fileNames.length; i++) {
        String fileName = GlobalFile.getFileLocation(outPathName, fileNames[i]);
        file = new File(fileName);
        if(!file.isDirectory()&&(fileNames[i].endsWith(".log"))) {
          String[] info = getMean(fileName);
          if(info!=null) {
            fileNum ++;
            RandomAccessFile resultDataFile = null;
            String dataLogFile;
            
            dataLogFile = GlobalFile.getFileLocation(outPathName,"summary_files.txt");
            if (fileNum==1) new File(dataLogFile).delete();
            resultDataFile = new RandomAccessFile(dataLogFile,"rw");
            
            resultDataFile.seek(resultDataFile.length());
            resultDataFile.writeBytes(fileName+BasicTag.RETURN_TAG);
            resultDataFile.close();
            
            dataLogFile = GlobalFile.getFileLocation(outPathName,"summary_dataB.txt");
            if (fileNum==1) new File(dataLogFile).delete();
            resultDataFile = new RandomAccessFile(dataLogFile,"rw");
            resultDataFile.seek(resultDataFile.length());
            resultDataFile.writeBytes(info[0]);
            resultDataFile.close();
            
            for (int j=1; j<info.length; j++) {
              dataLogFile = GlobalFile.getFileLocation(outPathName,"summary_data"+j+".txt");
              if (fileNum==1) new File(dataLogFile).delete();
              resultDataFile = new RandomAccessFile(dataLogFile,"rw");
              resultDataFile.seek(resultDataFile.length());
              resultDataFile.writeBytes(info[j]);
              resultDataFile.close();
            }
          }
        }
      }
    }
  }

  static public String[] getMean(String fileName)  throws Exception {
    String[] meaningfulLines = GlobalString.getMeaningfulLines(GlobalFile.getStringFromFile(fileName));
    //get basic information
    if (meaningfulLines.length==0) return null;
    String[] lineInfos = GlobalString.tokenize(meaningfulLines[0], "|");
    if (lineInfos.length<2) return null;
    
    String[] dataInfo = GlobalString.tokenize(lineInfos[0].trim(), BasicTag.NULL_SEPERATE_TAG);
    int dataN = dataInfo.length;
    int dataN_R = dataN;
    int featN = GlobalString.tokenize(dataInfo[0].trim(), "/").length;
    
    int number = 0;
    double[] bestData = new double[featN+2];
    double[][] normData = new double[featN][dataN];
    Arrays.fill(bestData, 0);
    for (int i=0; i<normData.length; i++) {
      Arrays.fill(normData[i], 0);
    }
    
    for (int i=0; i<meaningfulLines.length; i++) {
      lineInfos = GlobalString.tokenize(meaningfulLines[i], "|");
      if (lineInfos.length<2) break;
      dataInfo = GlobalString.tokenize(lineInfos[1].trim(),"@");
      if (dataInfo.length!=2) break;
      String[] normInfo = GlobalString.tokenize(dataInfo[0].trim(),"/");
      for (int j=0; j<normInfo.length; j++) {
        bestData[j] += new Double(normInfo[j]).doubleValue();
      }
      bestData[normInfo.length] += new Double(dataInfo[1]).doubleValue();
      
      dataInfo = GlobalString.tokenize(lineInfos[0].trim(), BasicTag.SPACE_TAG);
      dataN_R = Math.min(dataInfo.length, dataN_R);
      for (int j=0; j<dataN_R; j++) {
        String[] featInfo = GlobalString.tokenize(dataInfo[j].trim(),"/");
        for (int k=0; k<featInfo.length; k++) {
          normData[k][j] += new Double(featInfo[k]).doubleValue();
        }
      }
      number++;
    }
    
    if (number==0) return null;
    
    for (int i=0; i<normData.length; i++) {
      for (int j=0; j<normData[i].length; j++) {
        normData[i][j] /=number;
      }
    }
    
    for (int i=0; i<bestData.length-1; i++) {
      bestData[i] /=number;
    }
    bestData[bestData.length-1] = number;
    
    String[] infoes = new String[featN+1];
    infoes[0] = OutputMethods.outputVectorAsStr(bestData);
    for (int i=0; i<featN; i++) {
      infoes[i+1] = OutputMethods.outputVectorAsStr(normData[i], dataN_R);
    }
      
    return infoes;
  }

/**
  * the main process.
  */
  public static void main(String[] args) {
    try  {
      new MultiResultStat(args);
      System.exit(0);
    }
    catch (Exception e) {
      System.out.println("ERROR: "+e.toString());
      System.exit(-1);
    }
  }

}



