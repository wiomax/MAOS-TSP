/**
 * Description: provide an utility for counting multi time cost
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 07, 2005
 */

package Global.util.timecost;

import java.util.*;

public class MultiTimeCostCounter {
  private static final String DEFAULT_KEY = "DefaultKey";
  private Hashtable<String, Object> costCounterTable = new Hashtable<String, Object>();
  private boolean isCPUTime = false;

  public MultiTimeCostCounter(){}

  public MultiTimeCostCounter(boolean isCPUTime){
    this.isCPUTime = isCPUTime;
  }

  public boolean removeCounter(String countKey) {
    Object counterObj = costCounterTable.get(countKey);
    if (counterObj==null) return false;
    costCounterTable.remove(counterObj);
    return true;
  }

  public TimeCostCounter getCounter(String countKey) {
    TimeCostCounter counter = null;
    Object counterObj = costCounterTable.get(countKey);
    if (counterObj!=null) {
      counter = (TimeCostCounter)counterObj;
    } else {
      counter = new TimeCostCounter(isCPUTime);
      costCounterTable.put(countKey, counter);
    }
    return counter;
  }

  public void setStart() {
    setStart(DEFAULT_KEY);
  }

  public void setStart(String countKey) {
    getCounter(countKey).setStart();
  }

  public void setEnd() {
    setEnd(DEFAULT_KEY);
  }
  public void setEnd(String countKey) {
    getCounter(countKey).setEnd();
  }

  public long getTotalTime() {
    return getTotalTime(DEFAULT_KEY);
  }
  public long getTotalTime(String countKey) {
    TimeCostCounter counter = getCounter(countKey);
    return counter.getTotalTime();
  }

  public double getTotalTimeInSeconds() {
    return getTotalTimeInSeconds(DEFAULT_KEY);
  }

  public double getTotalTimeInSeconds(String countKey) {
    TimeCostCounter counter = getCounter(countKey);
    return counter.getTotalTimeInSeconds();
  }

  public void reset() {
    costCounterTable.clear();
  }

  public void reset(String countKey) {
    getCounter(countKey).reset();
  }

  public void printSelf() {
    Enumeration<?> counters = costCounterTable.keys();
    System.out.println("@@@ TimeCostCounter @@@");
    while(counters.hasMoreElements()) {
      String key = counters.nextElement().toString();
      System.out.println("->"+key+": "+ getTotalTimeInSeconds(key));
    }
  }
}
