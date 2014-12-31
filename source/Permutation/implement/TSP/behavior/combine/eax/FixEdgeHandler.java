/**
 * Description: handle the common edges
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 26, 2009    MAOS M01.00.01
 *
 * @version M01.00.01
 * @since M01.00.01
 *
 * @ Reference:
 * [1] Yuichi Nagata. The EAX Algorithm Considering Diversity Loss. PPSN, 2004
 */

package implement.TSP.behavior.combine.eax;

import java.util.*;

public class FixEdgeHandler {
  private int[][] commonMarix;
  
  //temp data
  private boolean[] commonFlags;
  int nodeNumber;
  int minimalSize = 20;
  
  public FixEdgeHandler(int nodeNumber) {
    this.nodeNumber = nodeNumber;
    minimalSize = (int)Math.sqrt(nodeNumber)+2;
    commonFlags = new boolean[nodeNumber];
    commonMarix = new int[nodeNumber][nodeNumber];
  }
  
  private void inputArray(int[] array) {
    for (int i=0; i<nodeNumber-1; i++) {
      commonMarix[array[i]][array[i+1]] ++;
      commonMarix[array[i+1]][array[i]] ++;
    }
    commonMarix[array[nodeNumber-1]][array[0]] ++;
    commonMarix[array[0]][array[nodeNumber-1]] ++;
  }
  
  private void checkArray(int[] array) {
    for (int i=0; i<nodeNumber-1; i++) {
      if (commonMarix[array[i]][array[i+1]]==2) {
        commonFlags[i] = true;
      }
    }
    if (commonMarix[array[nodeNumber-1]][array[0]]==2) {
      commonFlags[nodeNumber-1] = true;
    }
  }

  public void initElements(int[] arrayA, int[] arrayB) {
    for (int i=0; i<nodeNumber; i++) {
      Arrays.fill(commonMarix[i], 0);
    }
    inputArray(arrayA);
    inputArray(arrayB);
    
    Arrays.fill(commonFlags, false);
    checkArray(arrayA);
    
    int startID = -1, size = 0;
    for (int i=0; i<nodeNumber; i++) {
      if (commonFlags[i]) {
        if (size==0) {
          size =1;
          startID = i;
        } else {
          size ++;
        }
      } else {
        if (size==0) continue;
        
        if (size>=minimalSize) {
          commonMarix[arrayA[startID]][arrayA[startID+1]] = 0;
          commonMarix[arrayA[startID+1]][arrayA[startID]] = 0;
          commonMarix[arrayA[(startID+size)%nodeNumber]][arrayA[startID+size-1]] = 0;
          commonMarix[arrayA[startID+size-1]][arrayA[(startID+size)%nodeNumber]] = 0;
        } else {
          for (int j=0; j<size; j++) {
            commonMarix[arrayA[startID+j]][arrayA[(startID+j+1)%nodeNumber]] = 0;
            commonMarix[arrayA[(startID+j+1)%nodeNumber]][arrayA[startID+j]] = 0;
          }
        }
        size = 0;
      }
    }
  }
  
  public boolean isCommonEdge(int i, int j) {
    return commonMarix[i][j]==2;
  }
}

