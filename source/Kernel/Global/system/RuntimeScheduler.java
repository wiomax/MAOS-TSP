/**
 * Description: for scheduing the running time period
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jun 07, 2006    xiaofengxie@tsinghua.org.cn
 *
 * @version 1.0
 */

package Global.system;

import java.util.*;
import Global.methods.*;

public class RuntimeScheduler {
  public static final int HOURSPERDAY = 24;
  public static final int MINUTESPERHOUR = 60;
  public static final int SECONDSPERMINUTE = 60;
  public static final int MILLIS = 1000;

  public final int MINSECONDS = 0;
  public final int MAXSECONDS = HOURSPERDAY*MINUTESPERHOUR*SECONDSPERMINUTE;
  private int startSeconds = 0;
  private int endSeconds = 0;

  //temp
  private Calendar calendar = Calendar.getInstance();

  public void initSchedule(String content) throws Exception{
    String[] lines = GlobalString.getMeaningfulLines(content);
    if (lines.length<1) return;
    String[] lineContent = GlobalString.tokenize(lines[0], " \t");
    if (lineContent.length!=2) return;
    setSleepSpan(TypeConverter.toInteger(lineContent[0]), TypeConverter.toInteger(lineContent[1]));
  }

  public void setSleepSpan(int startSeconds, int endSeconds) {
    this.startSeconds = startSeconds;
    this.endSeconds = endSeconds;
  }

  public void schedule() throws Exception {
    this.schedule(System.currentTimeMillis());
  }

  public void schedule(long currentTimeMillis) throws Exception {
    int currentSeconds = this.getSeconds(currentTimeMillis);
    this.schedule(currentSeconds);
  }

  public void schedule(int currentSeconds) throws Exception {
    if (this.isSleepSpan(currentSeconds)) {
      Thread.sleep((endSeconds-currentSeconds)*(long)MILLIS);
    }
  }

  public boolean isSleepSpan(int currentSeconds) {
    return (currentSeconds>startSeconds&&currentSeconds<endSeconds);
  }

  public int getCurrentSeconds() {
    return getSeconds(System.currentTimeMillis());
  }

  public int getSeconds(long currentTimeMillis) {
    calendar.setTimeInMillis(currentTimeMillis);
    return getSeconds(calendar);
  }

  public static int getSeconds(int hours) {
    return getSeconds(hours, 0);
  }

  public static int getSeconds(int hours, int minutes) {
    return getSeconds(hours, minutes, 0);
  }

  public static int getSeconds(int hours, int minutes, int seconds) {
    return hours*MINUTESPERHOUR*SECONDSPERMINUTE+minutes*SECONDSPERMINUTE+seconds;
  }

  public int getSeconds(Calendar calendar) {
    return getSeconds(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
  }

}
