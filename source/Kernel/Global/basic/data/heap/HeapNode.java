/**
 * The node in a heap.
 *
 * @ Author        Create/Modi     Note
 * Xiaofeng Xie    Jan 02, 2009    MAOS M01.00.01
 *
 * @version M01.00.01
 * @since M01.00.01
 * 
 * [1] K. Helsgaun,"An Effective Implementation of the Lin-Kernighan Traveling
 *  Salesman Heuristic", European J Operational Research 126 (1), 106-130 (2000).
 * [2] K. Helsgaun, LKH 1.3
 */

package Global.basic.data.heap;


public class HeapNode {
   public int Id;        /* The number of the node (1...Dimension) */ 
   public int Loc;       /* The location of the node in the heap 
                            (zero, if the node is not in the heap) */ 
   public int Rank;      /* During the ascent, the priority of the node */
}

