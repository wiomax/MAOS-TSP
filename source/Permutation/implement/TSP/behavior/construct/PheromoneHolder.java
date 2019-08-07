/**
 * Description: provide the Pheromone information
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Apr 7, 2005
 * Xiaofeng Xie    Apr 28, 2006    MAOS-TSP Beta 1.1.002
 * Xiaofeng Xie    Aug 21, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 * @since M01.00.00
 *
 * @ Reference:
 *  [1] Program's name: acotsp
 *  Ant Colony Optimization algorithms (AS, ACS, EAS, RAS, MMAS, BWAS) for the
 *  symmetric TSP, Copyright (C) 2004  Thomas Stuetzle
 *  Email: stuetzle no@spam informatik.tu-darmstadt.de
 */

package implement.TSP.behavior.construct;

import Global.basic.*;
import Global.basic.data.collection.*;
import maosKernel.represent.landscape.space.*;
import implement.TSP.represent.*;
import implement.TSP.behavior.neighbor.*;
import maosKernel.represent.landscape.quality.*;

public class PheromoneHolder extends BasicObject implements IGetProbabiityEngine, INodeNumberEngine {
  IGetLocalCostEngine localCostEngine;
  ICalcGlobalCostEngine globalCostEngine;

  protected double[][] pheroMatrix;
  protected double[][] totalMatrix;

  double p_best = 0.05;                 /* the expected construct probability of the best solution as MMAS to be converged*/
  double rho = 0.02;                     /* parameter for evaporation */
  double alpha = 1.0;                   /* importance of trial */
  double beta = 2.0;                    /* importance of heuristic evaluation */
  double trial_max = Double.MAX_VALUE;  /* maximum pheromone trial in MMAS */
  double trial_min = 0;                 /* minimum pheromone trial in MMAS */


  public PheromoneHolder(ICalcGlobalCostEngine globalCostEngine, IGetLocalCostEngine localCostEngine, int nodeNumber) {
    this.localCostEngine = localCostEngine;
    this.globalCostEngine = globalCostEngine;
    pheroMatrix = new double[nodeNumber][nodeNumber];
    totalMatrix = new double[nodeNumber][nodeNumber];
  }

  public int getNodeNumber() {
    return pheroMatrix.length;
  }

  //set the limitations for pheromone trials according to a point (best in current cycle)
  public void adj_pheromone_limits(SearchState gbestPoint) {
    trial_max = 1. / ( (rho) * globalCostEngine.getGlobalCost(gbestPoint));

    trial_min = trial_max / (2. * getNodeNumber());
//    double p_x = Math.exp(Math.log(p_best)/getNodeNumber());
//    trial_min = trial_max * (1. - p_x) / (p_x * (getNodeNumber()/ 2.0-1));
//    trial_min = Math.min(trial_min, trial_max);
  }

  /*
    FUNCTION:      implements the pheromone trial evaporation
    INPUT:         none
    OUTPUT:        none
    (SIDE)EFFECTS: pheromones are reduced by factor (1-rho)
  */
  public void evaporation_full() {
    for (int i = 0 ; i < pheroMatrix.length; i++ ) {
      for (int j = 0 ; j < i ; j++ ) {
        pheroMatrix[i][j] *= (1 - rho);
        pheroMatrix[j][i] = pheroMatrix[i][j];
      }
    }
  }

  public void init_max_pheromone_trials() {
    init_pheromone_trials(trial_max);
  }

  /*
    FUNCTION:      initialize pheromone trials
    INPUT:         initial value of pheromone trials "initial_trial"
    OUTPUT:        none
    (SIDE)EFFECTS: pheromone matrix is reinitialized
  */
  public void init_pheromone_trials( double initial_trial ) {
    int i, j;
    /* Initialize pheromone trials */
    for ( i = 0 ; i < getNodeNumber(); i++ ) {
      for ( j =0 ; j < i ; j++ ) {
        pheroMatrix[i][j] = initial_trial;
        pheroMatrix[j][i] = initial_trial;
      }
    }
  }

  public void evaporation_nn(INeighborEngine tspNNManager) {
    for (int i = 0 ; i < getNodeNumber(); i++ ) {
      int[] sortedArray = tspNNManager.getNNArrayAt(i);
      for (int j = 0 ; j < tspNNManager.getElementNumberAt(i); j++ ) {
        pheroMatrix[i][sortedArray[j]] *= (1 - rho);
      }
    }
  }

  /*
    FUNCTION:      only for MMAS without local search:
                   keeps pheromone trials inside trial limits
    INPUT:         none
    OUTPUT:        none
    (SIDE)EFFECTS: pheromones are forced to interval [trial_min,trial_max]
  */
  public void check_pheromone_trial_limits( ) {
    int    i, j;
    for ( i = 0 ; i < getNodeNumber(); i++ ) {
      for ( j = 0 ; j < i ; j++ ) {
        if ( pheroMatrix[i][j] < trial_min ) {
          pheroMatrix[i][j] = trial_min;
          pheroMatrix[j][i] = trial_min;
        } else if ( pheroMatrix[i][j] > trial_max ) {
          pheroMatrix[i][j] = trial_max;
          pheroMatrix[j][i] = trial_max;
        }
      }
    }
  }

  /*
    FUNCTION:      reinforces edges used in ant k's solution
    INPUT:         a solution path
    OUTPUT:        none
    (SIDE)EFFECTS: pheromones of arcs in ant k's tour are increased
  */
  public void update_pheromone(SearchState point, boolean isAdd) {
    double d_tau = 1.0 / globalCostEngine.getGlobalCost(point);

    if (!isAdd) d_tau *= -1;

    int i, j, h;
    int nodeNumber = getNodeNumber();
    for ( i = 0 ; i <nodeNumber; i++ ) {
      j = point.getIArray()[i];
      h = point.getIArray()[(i+1)%nodeNumber];
      update_pheromone_at(j, h, d_tau);
    }
  }

  private void update_pheromone_at(int j, int h, double d_tau) {
    pheroMatrix[j][h] += d_tau;
    pheroMatrix[h][j] = pheroMatrix[j][h];
  }

  public void update_total_information(SearchState point) {
    int i, j, h;
    int nodeNumber = getNodeNumber();
    for ( i = 0 ; i <nodeNumber; i++ ) {
      j = point.getIArray()[i];
      h = point.getIArray()[(i+1)%nodeNumber];
      compute_total_information_at(j ,h);
    }
  }

  public double[] getProbabilityArrayAt(int index) {
    return totalMatrix[index];
  }

  public boolean isPartialMode() {
    return false;
  }
  /*
    FUNCTION: calculates heuristic info times pheromone for each arc
    INPUT:    none
    OUTPUT:   none
  */
  public void compute_total_information() {
    int i, j;
    for ( i = 0 ; i < totalMatrix.length; i++ ) {
      for ( j = 0 ; j < i ; j++ ) {
        compute_total_information_at(i, j);
      }
    }
  }

  private void compute_total_information_at(int i, int j) {
    totalMatrix[i][j] = Math.pow(pheroMatrix[i][j],alpha) * Math.pow(1.0/localCostEngine.getLocalCost(i, j),beta);
    totalMatrix[j][i] = totalMatrix[i][j];
  }

  public void initPheromoneHolder(SearchState goodPoint) {
    adj_pheromone_limits(goodPoint);
    init_max_pheromone_trials();
    compute_total_information();
  }

  public void updatePheromoneHolder(SearchState goodPoint, SearchState[] infoPoints, INeighborEngine tspNNManager) {
    adj_pheromone_limits(goodPoint);
    if (tspNNManager!=null) {
      evaporation_nn(tspNNManager);
    } else {
      evaporation_full();
    }
    for (int i=0; i<infoPoints.length; i++) {
      update_pheromone(infoPoints[i], true);
    }
    check_pheromone_trial_limits();
    compute_total_information();
  }

  public void updatePheromoneHolder(SearchState goodPoint, INeighborEngine tspNNManager) {
    updatePheromoneHolder(goodPoint, new SearchState[]{goodPoint}, tspNNManager);
  }
}

