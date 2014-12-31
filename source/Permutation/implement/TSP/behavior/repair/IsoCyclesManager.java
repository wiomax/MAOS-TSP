/**
 * Description: manage the intermediate tsp path in many cycles
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    May 20, 2005    Adapted from Nagata's EAX-Rand code in C++
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 * [1] Yuichi Nagata. The EAX Algorithm Considering Diversity Loss. PPSN, 2004
 */

package implement.TSP.behavior.repair;

import java.util.*;
import implement.TSP.represent.*;

public class IsoCyclesManager {
  private boolean[] used_unit;
  private int n_remain_unit;

  private int n_unit;
  private int[] unit_start;
  private int[] n_unit_city;

  private int[] city_belong_unit;

  //temp for initIsoCycles
  boolean[] check_city;

  public IsoCyclesManager(int nodeNumber) {
    check_city = new boolean[nodeNumber];
    n_unit_city = new int[nodeNumber];
    city_belong_unit = new int[nodeNumber];
    unit_start = new int[nodeNumber];
    used_unit = new boolean[nodeNumber];
  }

  public int getMinCycleID() {
    //get the cycle with minimal nodes
    int min_unit_size= Integer.MAX_VALUE;
    int min_unit_id = 0;
    for(int i=0;i<n_unit;i++) {
      if(!used_unit[i] && n_unit_city[i]<min_unit_size) {
        min_unit_id=i;
        min_unit_size=n_unit_city[i];
      }
    }
    return min_unit_id;
  }

  public int getRemainedCycleNumber() {
    return n_remain_unit;
  }

  public int get_unit_start_ID_at(int unitID) {
    return unit_start[unitID];
  }

  public int get_city_belong_unit_at(int cityID) {
    return city_belong_unit[cityID];
  }

  public void mergeTwoCycles(int center_un, int select_un) {
    used_unit[center_un]= true;

    for(int j=0;j<city_belong_unit.length;j++) {
      if (city_belong_unit[j] == center_un) {
        city_belong_unit[j] = select_un;
      }
    }
    n_unit_city[select_un]+=n_unit_city[center_un];
    n_remain_unit--;
  }

  //change into the representation of cycles
  public void initIsoCycles(DChainState chainState) {
    int st,ci,lastci=0,pre,curr,next;

    n_unit=0;
    Arrays.fill(check_city, false);

    int n_city=chainState.getNodeNumber();
    while(n_city!=0) {
      //find first unvisited city as start
      ci=lastci;
      while(true) {
        if(!check_city[ci]) {
          st=ci;
          lastci = ci;
          break;
        }
        ci++;
      }

      // get independent cycles
      int ucm=0;
      unit_start[n_unit]=st;
      city_belong_unit[st]=n_unit;
      curr=-1;
      next=st;
      while(true) {
        pre=curr;
        curr=next;
        next = chainState.getNextID(pre, curr);
        check_city[next]=true;
        n_city--;
        ucm++;
        city_belong_unit[next]=n_unit;
        if(next==st) break;
      }
      n_unit_city[n_unit]=ucm;
      n_unit++;
    }

    Arrays.fill(used_unit, 0, n_unit, false);
    n_remain_unit=n_unit;
  }
}

