/**
 * Description: The clock for learning cycles: each cycle contains several steps.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 09, 2004
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */
package maosKernel;

public class LearningClock {
  public final static int C_PRE = 0;
  public final static int C_RUN_G = 1;
  public final static int C_RUN_T = 2;
  public final static int C_POST = 3;
  public final static int[] ENV_STEPS = new int[]{LearningClock.C_PRE, LearningClock.C_POST};
  public final static int[] AGENTS_STEPS = new int[]{LearningClock.C_RUN_G, LearningClock.C_RUN_T};

//  public static int t = 0;
  public static int T = 2000;
}
