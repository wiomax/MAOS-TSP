/**
 * Description: The Gini coefficient is a measure of statistical dispersion most prominently 
 * used as a measure of inequality of income distribution or inequality of wealth distribution.
 * The Gini coefficient is defined as a ratio of the areas on the Lorenz curve diagram.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 25, 2008 
 *
 * @version M01.00.02
 */

package maosKernel.infoIO.analysis;

import java.util.Arrays;

import Global.basic.data.collection.*;
import Global.math.ArrayMath;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.topology.*;

public class GiniIndexAnalysizer extends AbsCycleAnalysizer {
  private AbsTopology topology;
  private int[] degreeArray;

  public GiniIndexAnalysizer(){
    this.setName("GiniIndex");
  }

  public void setInfo(AbsLandscape landscape) {
  }
  
  public boolean isDecentralized() {
    return !(topology.isCentralized());
  }

  public boolean isUseful() {
    return !(topology.isCentralized());
  }
  
  
  public void setTopology(AbsTopology topology) {
    this.topology = topology;
    int nodeNumber = topology.getNodeNumber();
    degreeArray = new int[nodeNumber];
  }
  
  public double analysis(IGetEachEncodedStateEngine stateSet) {
    return analysis(stateSet, topology);
  }

  public double analysis(IGetEachEncodedStateEngine stateSet, AbsTopology topology) {
    Arrays.fill(degreeArray, 0);
    int nodeNumber = degreeArray.length;
    for (int i=0; i<nodeNumber; i++) {
      IBasicICollectionEngine ids = topology.getConnectedNodeIDsAt(i);
      for (int j=0; j<ids.getSize(); j++) {
        degreeArray[ids.getElementAt(j)] ++;
      }
    }
    
    Arrays.sort(degreeArray);
    
    double integrationV = 0;
    for (int i=0; i<nodeNumber; i++) {
      integrationV += (nodeNumber-i)*degreeArray[i];
    }
    
    double wholeV = (nodeNumber+1)*ArrayMath.totalSum(degreeArray)/2.0;
    
    return 1-(integrationV/wholeV);
  }
}

