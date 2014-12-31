/**
 * Description: For calculating the mean value of results.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 19, 2003
 * Xiaofeng Xie    Nov 06, 2003
 *
 * @version 1.0
 * @Since MAOS1.0
 */

package Global.util.stat;

import java.io.*;
import java.util.*;
import Global.util.*;
import Global.gui.*;
import Global.define.*;
import Global.methods.*;
import javax.swing.*;
import Global.system.io.*;

public class BasicResultStat {
  public static boolean hasDataRowSize = true;

  public BasicResultStat(String[] args) throws Exception {
    String outPathName ="";
    if(args.length==1) {
      outPathName = args[0];
      execAllMean(outPathName);
    } else {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      JFrame frame = new JFrame();
      MeanSetupBox msb = new MeanSetupBox(frame);
      WndManager.showDialogInScreenCenter(msb);
    }
  }

  static public void execAllEvalNum(String outPathName, double thresholdValue, int thresholdIndex, double ratio) throws Exception {
    execAllEvalNum(outPathName, thresholdValue, thresholdIndex, ratio, 1);
  }


  static public void execAllEvalNum(String outPathName, double thresholdValue, int thresholdIndex, double ratio, double scale) throws Exception {
    if(outPathName!="") {
      File file = new File(outPathName);
      String[] fileNames = file.list();
      if (fileNames==null) {
        return;
      }
      String totalInfo = "[ Theshold value = "+thresholdValue +"; allowed ratio = "+ratio+" scale = "+scale+" ]"+BasicTag.RETURN_TAG;
      for(int i=0; i<fileNames.length; i++) {
        String fileName = GlobalFile.getFileLocation(outPathName, fileNames[i]);
        file = new File(fileName);
        if(!file.isDirectory() && (fileNames[i].trim().endsWith(".log"))) {
          String info = getMeanEval(fileName, thresholdValue, thresholdIndex, ratio, scale);
          if(info!=null) {
            totalInfo+=info;
          }
        }
      }
      if(totalInfo.length()>0) {
        String dataLogFile = GlobalFile.getFileLocation(outPathName,"NuMsummary.txt");
        RandomAccessFile resultDataFile = new RandomAccessFile(dataLogFile,"rw");
        resultDataFile.writeBytes(totalInfo);
        resultDataFile.close();
      }
    }
  }


  static public void execAllMean(String outPathName) throws Exception {
    execAllMean(outPathName, Double.MAX_VALUE, Integer.MAX_VALUE, 1);
  }

  static public void execAllMean(String outPathName, double thresholdValue, int thresholdIndex, double ratio) throws Exception {
    if(outPathName!="") {
      File file = new File(outPathName);
      String[] fileNames = file.list();
      Arrays.sort(fileNames);
      if (fileNames==null) {
        return;
      }
      String[] totalInfo = new String[2];
      for(int i=0; i<2; i++) totalInfo[i] = "";
      for(int i=0; i<fileNames.length; i++) {
        String fileName = GlobalFile.getFileLocation(outPathName, fileNames[i]);
        file = new File(fileName);
        if(!file.isDirectory()&&(fileNames[i].trim().endsWith(".log"))) {
          String[] info = getMean(fileName, thresholdValue, thresholdIndex, ratio);
          if(info!=null) {
            totalInfo[0]+=info[0];
            totalInfo[1]+=info[1];
          }
        }
      }
      if(totalInfo[0].length()>0) {
        String dataLogFile = GlobalFile.getFileLocation(outPathName,"summary.txt");
        RandomAccessFile resultDataFile = new RandomAccessFile(dataLogFile,"rw");
        resultDataFile.writeBytes("[ Theshold value = "+thresholdValue +"; allowed ratio = "+ratio+" ]"+BasicTag.RETURN_TAG);
        resultDataFile.writeBytes(totalInfo[1]+BasicTag.RETURN_TAG);
        resultDataFile.writeBytes("****************** Other information ****************"+BasicTag.RETURN_TAG+BasicTag.RETURN_TAG);
        resultDataFile.writeBytes(totalInfo[0]+BasicTag.RETURN_TAG);
        resultDataFile.close();
      }
    }
  }

  static public TotalDataInfo getTotalDataInfo(String fileName)  throws Exception {
    TotalDataInfo tdi = new TotalDataInfo();
    tdi.fileName = fileName;
    String[] meaningfulLines = GlobalString.getMeaningfulLines(GlobalFile.getStringFromFile(fileName));
    tdi.getSuccessData(meaningfulLines);
    if(tdi.data==null) {
      return null;
    }
    return tdi;
  }

  static public String getMeanEval(String fileName, double thresholdValue, int thresholdIndex, double ratio, double scale)  throws Exception {
    TotalDataInfo tdi = getTotalDataInfo(fileName);
    if(tdi==null) return null;
    IndexedAndFlagedData[] ifData = tdi.getEvalNIndexedAndFlagedData(thresholdValue, thresholdIndex);
    int allowNum = tdi.getValidIndices(ifData, tdi.getDataSize()-1, ratio).length;
    double totalEval = 0;
    for(int i=0; i<allowNum; i++) {
      totalEval += ifData[i].value;
    }
    if(allowNum!=0) totalEval /= allowNum;
    totalEval *= scale;
    return "File: "+new File(fileName).getName()+"\t RV="+FormatOutput.smartFormatConvert(allowNum/(double)tdi.getDataLines()*100)+"%: "+allowNum+" in DUP_TIMES="+tdi.getDataLines()+" MeanEval(Scale)="+totalEval+"("+scale+")"+"\r\n";
  }

  static public String[] getMean(String fileName)  throws Exception {
    return getMean(fileName, Double.MAX_VALUE,Integer.MAX_VALUE, 1);
  }

  static public String[] getMean(String fileName, double thresholdValue, int thresholdIndex, double ratio)  throws Exception {
    TotalDataInfo tdi = getTotalDataInfo(fileName);

    if(tdi==null) return null;
    IndexedAndFlagedData[] ifData = tdi.getOptimIndexedAndFlagedData(thresholdIndex);
    int[] validIndices = tdi.getValidIndices(ifData, thresholdValue, ratio);
    double[] meanData = new double[tdi.getDataSize()];
    int[] failNArray = new int[tdi.getDataSize()];
    for (int i=0; i<validIndices.length; i++) {
      for (int j=0; j<tdi.getDataSize(); j++) {
        if (!tdi.dataFlag[validIndices[i]][j]) {
          failNArray[j] ++;
        } else {
          meanData[j] += tdi.data[validIndices[i]][j];
        }
      }
    }
    String[] infoes = new String[2];
    infoes[0] = "File: "+new File(fileName).getName()+"\t RV="+FormatOutput.smartFormatConvert(validIndices.length/(double)tdi.getDataLines()*100)+"%: "+validIndices.length+" in DUP_TIMES="+tdi.getDataLines()+BasicTag.RETURN_TAG;
    infoes[1] = "";
    for (int j=0; j<tdi.getDataSize(); j++) {
      if (validIndices.length==failNArray[j]){
//        infoes[1] += "("+failNArray[j]+")";
        infoes[1] += "-";
      } else {
        meanData[j] /= validIndices.length - failNArray[j];
        infoes[1] += meanData[j];
        if (failNArray[j]!=0) {
          infoes[1] += "(" + failNArray[j] + ")";
        }
      }
      infoes[1] += " \t";
    }
    if (hasDataRowSize) {
      infoes[1]+= tdi.getDataLines()+" \t"+validIndices.length;
    }

    infoes[1]+= BasicTag.RETURN_TAG;
    return infoes;
  }

/**
  * the main process.
  */
  public static void main(String[] args) {
    try  {
      new BasicResultStat(args);
      System.exit(0);
    }
    catch (Exception e) {
      System.out.println("ERROR: "+e.toString());
      System.exit(-1);
    }
  }

}


class IndexedAndFlagedData {
  int index;
  double value;
}

class TotalDataInfo {
  String fileName = "";
  double[][] data = null;
  boolean[][] dataFlag = null;
  double failedRatio = 1;

  public int[] getValidIndices(IndexedAndFlagedData[] ifData, double thresholdValue, double ratio) {
    sortData(ifData);
    int allowNum = (int)(getDataLines()*ratio);
    while(allowNum>0&&ifData[allowNum-1].value>thresholdValue) {
      allowNum --;
    }
    int[] validIndices = new int[allowNum];
    for (int i=0; i<allowNum; i++) {
      validIndices[i] = ifData[i].index;
    }
    return validIndices;
  }

  public int getEvalNum(int lineIndex, double thresholdValue, int thresholdIndex) {
    int i = Math.min(this.getDataSize(), thresholdIndex);
    while (dataFlag[lineIndex][i-1]&&data[lineIndex][i-1]<thresholdValue) {
      i--;
      if(i==0) {
        break;
      }
    }
    return i;
  }

  public IndexedAndFlagedData[] getEvalNIndexedAndFlagedData(double thresholdValue, int thresholdIndex) {
    IndexedAndFlagedData[] ifData = new IndexedAndFlagedData[getDataLines()];
    for (int i=0; i<ifData.length; i++) {
      ifData[i] = new IndexedAndFlagedData();
      ifData[i].value = getEvalNum(i, thresholdValue, thresholdIndex);
      ifData[i].index = i;
    }
    return ifData;
  }

  public IndexedAndFlagedData[] getOptimIndexedAndFlagedData(int thresholdIndex) {
    IndexedAndFlagedData[] ifData = new IndexedAndFlagedData[getDataLines()];
    int validSize = Math.min(this.getDataSize(), thresholdIndex)-1;
    for (int i=0; i<this.getDataLines(); i++) {
      ifData[i] = new IndexedAndFlagedData();
      if (dataFlag[i][validSize]) {
        ifData[i].value = data[i][validSize];
      } else {
        ifData[i].value = Double.MAX_VALUE;
      }
      ifData[i].index = i;
    }
    return ifData;
  }

  static public void sortData(IndexedAndFlagedData[] inData) {
    Arrays.sort(inData, new Comparator() {
      public int compare(Object a, Object b) {
        IndexedAndFlagedData dataA = (IndexedAndFlagedData)a;
        IndexedAndFlagedData dataB = (IndexedAndFlagedData)b;
        if(dataA.value>dataB.value) {
          return 1;
        } else if(dataA.value<dataB.value) {
          return -1;
        } else {
          return 0;
        }
      }
    });
  }

  public int getValidIndex(double thresholdValue)  throws Exception {
    int dataLength = data[0].length;

    int thresholdIndex = data.length;

    for(; thresholdIndex>=1; thresholdIndex--) {
      if(data[thresholdIndex-1][dataLength-1]<=thresholdValue) {
         break;
      }
    }
    return thresholdIndex;
  }

  public int getDataLines() {
    return data.length;
  }

  public int getDataSize() {
    return data[0].length;
  }

  public boolean getSuccessData(String[] allData)  throws Exception {
    int column = -1;
    Vector totalData = new Vector();
    Vector totalDataFlag = new Vector();
    String[] sData = null;
    String[] tData = null;
    double[] dbData = null;
    boolean[] dbDataFlag = null;
    for(int x=0; x<allData.length; x++) {
      sData = GlobalString.tokenize(allData[x], "\t ");
      if(column==-1) column = sData.length;
      if(totalData.size()==0||column==sData.length) {
        dbData = new double[column];
        dbDataFlag = new boolean[column];
        for(int i=0; i<column; i++) {
          tData = GlobalString.tokenize(sData[i], "|");
          dbData[i] = new Double(tData[0]).doubleValue();
          dbDataFlag[i] = (tData.length==1);
        }
        totalData.add(dbData);
        totalDataFlag.add(dbDataFlag);
      }
    }
    int tSize = totalData.size();
    if(tSize==0) return false;
    data = new double[tSize][column];
    dataFlag = new boolean[tSize][column];
    for(int i=0; i<tSize; i++) {
      data[i] = (double[]) totalData.elementAt(i);
      dataFlag[i] = (boolean[]) totalDataFlag.elementAt(i);
    }
    return true;
  }
}


