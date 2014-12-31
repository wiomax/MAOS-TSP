/**
 * Description: The interface for select next node in order to construct a tour
 *  in sequrence
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 24, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 */


package implement.TSP.behavior.construct;


public interface INextNodeSelectEngine {
  public int selectNextNodeAt(int cityID, boolean[] occupyFlags);
}
