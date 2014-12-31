/**
 * Description: Evaluate two quality values, if A is
 * not worse than B, then return true; else return false
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */


package maosKernel.represent.landscape.quality;
 
public interface IQualityEvaluateEngine {
  public boolean evaluate(double encodedA, double encodedB);
}
