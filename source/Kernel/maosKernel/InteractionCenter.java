/**
 * Description: The interaction protocol in a distributed network topology.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Dec 12, 2006
 * Xiaofeng Xie    Aug 30, 2008    MAOS M01.00.00
 *
 * @version M01.00.00
 */

package maosKernel;

import Global.basic.data.collection.*;
import maosKernel.memory.*;
import maosKernel.represent.landscape.*;
import maosKernel.behavior.topology.*;
import maosKernel.represent.knowledge.*;

public class InteractionCenter {
  private StateSet socialLibrary; //a summary of non-private knowledge of agents & social available knowledge
  private AbsTopology topologyEngine = new FullConnectTopology(); //connection topology
  
  public InteractionCenter(AbsLandscape virtualLandscape, MiniAgent[] agents, AbsTopology topologyEngine) {
    if (topologyEngine!=null) this.topologyEngine = topologyEngine;

    socialLibrary = getSocialLibrary(agents);

    this.topologyEngine.basicInit(agents.length);

    if (this.topologyEngine instanceof ISetLandscapeInfoEngine) {
      ((ISetLandscapeInfoEngine)this.topologyEngine).setInfo(virtualLandscape);
    }
    if (this.topologyEngine instanceof ISetStateSetEngine) {
      ((ISetStateSetEngine)this.topologyEngine).setInfo(socialLibrary);
    }

  }

  //For non-private states of agents
  private StateSet getSocialLibrary(MiniAgent[] agents) {
    int N = agents.length;
    EncodedState[] states = new EncodedState[N];
    for(int i=0; i<N; i++) {
      states[i] = agents[i].getNonprivateState();
    }
    return new StateSet(states);
  }
  
  private IBasicICollectionEngine getConnectedNodeIDsAt(int nodeID) {
    return topologyEngine.getConnectedNodeIDsAt(nodeID);
  }
  
  
  public AbsTopology getTopology() {
    return this.topologyEngine;
  }
  
  public IGetEachEncodedStateEngine getSocialInfo() {
    return socialLibrary;
  }

  public void adjustStatus() {
    topologyEngine.adjustStatus();
  }
  
  //For each agent
  public ISocialAccessEngine getSocialAccessModuleAt(final int nodeID) {
    return new ISocialAccessEngine() {
      public int getLibSize() {
        if (nodeID==-1) return socialLibrary.getLibSize();
        return getConnectedNodeIDsAt(nodeID).getSize();
      }
      public EncodedState getSelectedPoint(int index) {
        if (nodeID==-1) return socialLibrary.getRandomPoint();
        return socialLibrary.getSelectedPoint(getConnectedNodeIDsAt(nodeID).getElementAt(index));
      }

      public boolean equals(Object obj) {
        return topologyEngine.isCentralized();
      }
    };
  }
}
