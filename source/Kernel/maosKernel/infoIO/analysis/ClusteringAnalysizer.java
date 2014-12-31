/**
 * Description: The clustering coefficient C(p) is defined as follows. Suppose 
 * that a vertex v has kv neighbours; then at most kv(kv-1)/2 edges can exist 
 * between them (this occurs when every neighbour of v is connected to every other 
 * neighbour of v). Let Cv denote the fraction of these allowable edges that actually 
 * exist. Define C as the average of Cv over all v.
 * 
 * For friendship networks, Cv reflects the extent to which friends of v are also 
 * friends of each other; and thus C measures the cliquishness of a typical friendship circle.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Oct 14, 2008 
 *
 * @version M01.00.02
 * 
 * @Reference:
 * [1] D. J. Watts and S. H. Strogatz, "Collective dynamics of 'small-world' networks," 
 *     Nature, vol. 393, pp. 440-442, 1998.
 */

package maosKernel.infoIO.analysis;

import java.util.Arrays;

import Global.basic.data.collection.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.topology.*;

public class ClusteringAnalysizer extends AbsCycleAnalysizer {
  private AbsTopology topology;
  private boolean[][] connectMatrix;

  public ClusteringAnalysizer(){
    this.setName("Clustering");
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
    connectMatrix = new boolean[nodeNumber][nodeNumber];
  }
  
  public double analysis(IGetEachEncodedStateEngine stateSet) {
    return analysis(stateSet, topology);
  }

  public double analysis(IGetEachEncodedStateEngine stateSet, AbsTopology topology) {
    for (int i=0; i<connectMatrix.length; i++) {
      Arrays.fill(connectMatrix[i], false);
    }
    for (int i=0; i<connectMatrix.length; i++) {
      IBasicICollectionEngine ids = topology.getConnectedNodeIDsAt(i);
      for (int j=0; j<ids.getSize(); j++) {
        connectMatrix[i][ids.getElementAt(j)] = true;
      }
    }
    
    int nodeNumber = stateSet.getLibSize();
    double kv, subC, C = 0;
    int i, j, k;
    for (i=0; i<nodeNumber; i++) {
      IBasicICollectionEngine ids = topology.getConnectedNodeIDsAt(i);
      
      kv = ids.getSize();
      if (kv>1) {
        subC = 0;
        for (j=0; j<kv; j++) {
          for (k=0; k<kv && k!=j; k++) {
            if (connectMatrix[ids.getElementAt(j)][ids.getElementAt(k)]) subC++;
          }
        }
        C += subC/(kv*(kv-1));
      }
    }
    return C/nodeNumber;
  }
}

