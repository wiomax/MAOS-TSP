/**
 * Description: basic operations for IBasicICollectionEngine
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 27, 2005
 */

package Global.basic.data.collection;

import Global.methods.*;

public class BasicCollection {
  //finding a first location for larger than thValue
  public static int binaryLSearch(IBasicICollectionEngine valueArray, int thValue, int startID, int endID) {
    if (valueArray.getElementAt(startID)>thValue) return startID;
    if (valueArray.getElementAt(endID)<=thValue) return endID+1;
    int mid = -1;
    startID++;
    while(startID<=endID) {
      mid = (int)(endID+startID)/2;
      if(thValue > valueArray.getElementAt(mid)) {
        startID = mid+1;
      } else if (thValue < valueArray.getElementAt(mid)) {
        if (thValue>=valueArray.getElementAt(mid-1)) return mid;
        endID = mid-1;
      } else {
        return mid+1;
      }
    }
    return mid;
  }


  public static int getRandomElement(IBasicICollectionEngine srcArray) {
    return srcArray.getElementAt(RandomGenerator.intRangeRandom(srcArray.getSize()));
  }

  public static int getRandomElement(IBasicICollectionEngine srcArray, int exceptID) {
    return srcArray.getElementAt(RandomGenerator.intRangeRandomWithExceptID(srcArray.getSize(), exceptID));
  }

  public static int getExtremalElementIndices(int[] srcArray, int[] idArray, boolean isMax) {
    int extremalV = -Integer.MAX_VALUE;
    if (!isMax) extremalV = Integer.MAX_VALUE;
    int degree = -1;
    int idSize = 0;
    for (int i=0; i<srcArray.length; i++) {
      degree = srcArray[i];
      if ((extremalV < degree)==isMax && extremalV!=degree) {
        extremalV = degree;
        idArray[0] = i;
        idSize = 1;
      } else if (extremalV==degree) {
        idArray[idSize] = i;
        idSize++;
      }
    }
    return idSize;
  }

  public static void getElementIndices(int[] srcArray, int value, IPumpICollectionEngine idArray) {
    idArray.clear();
    for (int i=0; i<srcArray.length; i++) {
      if (value==srcArray[i]) idArray.addElement(i);
    }
  }

//  public static void getMinElementIndices(int[] srcArray, IPumpICollectionEngine idArray) {
//    int extremalV = Integer.MAX_VALUE;
//    int degree = -1;
//    for (int i=0; i<srcArray.length; i++) {
//      degree = srcArray[i];
//      if (extremalV > degree) {
//        extremalV = degree;
//        idArray.clear();
//        idArray.addElement(i);
//      } else if (extremalV==degree) {
//        idArray.addElement(i);
//      }
//    }
//  }

  public static void getExtremalElementIndices(int[] srcArray, IPumpICollectionEngine idArray, boolean isMax) {
    int extremalV = -Integer.MAX_VALUE;
    if (!isMax) extremalV = Integer.MAX_VALUE;
    int degree = -1;
    for (int i=0; i<srcArray.length; i++) {
      degree = srcArray[i];
      if ((extremalV < degree)==isMax && extremalV!=degree) {
        extremalV = degree;
        idArray.clear();
        idArray.addElement(i);
      } else if (extremalV==degree) {
        idArray.addElement(i);
      }
    }
  }

  public static void getExtremalElementIndices(IBasicICollectionEngine srcArray, IPumpICollectionEngine idArray, boolean isMax) {
    int extremalV = -Integer.MAX_VALUE;
    if (!isMax) extremalV = Integer.MAX_VALUE;
    int degree = -1;
    for (int i=0; i<srcArray.getSize(); i++) {
      degree = srcArray.getElementAt(i);
      if ((extremalV < degree)==isMax && extremalV!=degree) {
        extremalV = degree;
        idArray.clear();
        idArray.addElement(i);
      } else if (extremalV==degree) {
        idArray.addElement(i);
      }
    }
  }

  public static void toSegments(IDynamicICollectionEngine segmentIDArray, IDynamicICollectionEngine segmentValueArray, int[] orderredArray) {
    int currentW = -Integer.MAX_VALUE;
    int selectedW;
    segmentIDArray.clear();
    segmentValueArray.clear();
    for(int i=0; i<orderredArray.length; i++) {
      selectedW = orderredArray[i];
      if (selectedW!=currentW) {
        segmentIDArray.addElement(i);
        currentW = selectedW;
        segmentValueArray.addElement(currentW);
      }
    }
    segmentIDArray.addElement(orderredArray.length);
  }

  private static DualIAlienArray alienIDArray = new DualIAlienArray(100);
  public static DualIAlienArray initDualIAlienArray(int nodeNumber) {
    if (alienIDArray.getMaxSize()<nodeNumber) {
      alienIDArray = new DualIAlienArray(nodeNumber);
    } else {
      alienIDArray.clear();
    }
    return alienIDArray;
  }
}
