/**
 * Description: provide an utility for counting time
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 06, 2005
 * Xiaofeng Xie    Jun 27, 2008
 */

package Global.util.timecost;

import Global.util.GlobalTools;

public class TimeCostCounter {
  private double precision = 1000000000; //nano time
  private long totalTime = 0;
  private long segTime = 0;
  private boolean isCPUTime = false;

  public TimeCostCounter(){}

  public TimeCostCounter(boolean isCPUTime){
    this.isCPUTime = isCPUTime;
  }

  public void setPrecision(double precision) {
    this.precision = precision;
  }

  public long getCurrentTime() {
    //Remove the following line for JAVA Version 1.4 or below 
    if (isCPUTime&&GlobalTools.getCurrentJavaVerRelationTo("1.5")>=0) return java.lang.management.ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime(); else 

    return System.currentTimeMillis()*1000000;
  }
  
  public void setStart() {
    segTime = getCurrentTime();
  }

  public void setEnd() {
    totalTime += getCurrentTime()-segTime;
  }

  public void setEnd(long currentTime) {
    totalTime += currentTime-segTime;
  }
  public long getTotalTime() {
    return totalTime;
  }

  public double getTotalTimeInSeconds() {
    return totalTime/precision;
  }

  public void reset() {
    totalTime = 0;
  }
}
