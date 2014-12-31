/**
 * A binary heap is used to implement a priority queue. 
 *
 * A heap is useful in order to speed up the computations of minimum 
 * spanning trees. The elements of the heap are the nodes, and the
 * priorities (ranks) are their associated costs (their minimum distance 
 * to the current tree). 
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


public class H_Heap {
  public int HeapCount;          /* Its current number of elements */
  private HeapNode[] Heap;

/*      
  MakeHeap creates an empty heap. 
*/

  public void MakeHeap(int Size)
  {
    Heap = new HeapNode[Size];
    for (int i=0; i<Size; i++) {
      Heap[i] = new HeapNode();
    }
   HeapCount = 0;
  }

/*
  The SiftUp function is called when the rank of a node is decreased.
  The function moves the node forward in the heap (the foremost node
  of the heap has the lowest rank).

  When calling SiftUp(N), node N must belong to the heap.              
*/

  public void SiftUp(HeapNode N)
{
   int Loc = N.Loc, P = Loc / 2;

   while (P>0 && N.Rank < Heap[P].Rank) {
       Heap[Loc] = Heap[P];
       Heap[Loc].Loc = Loc;
       Loc = P;
       P /= 2;
   }
   Heap[Loc] = N;
   Heap[Loc].Loc = Loc;
}

/*       
  The DeleteMin function deletes the foremost node from the heap. 
  The function returns a pointer to the deleted node (0, if the heap
  is empty).
*/

  public HeapNode DeleteMin()
{
   HeapNode Remove, Item;
   int Ch, Loc;

   if (HeapCount==0)
       return null;
   Remove = Heap[1];
   Item = Heap[HeapCount];
   HeapCount--;
   Loc = 1;
   Ch = 2 * Loc;

   while (Ch <= HeapCount) {
       if (Ch < HeapCount && Heap[Ch + 1].Rank < Heap[Ch].Rank)
           Ch++;
       if (Heap[Ch].Rank >= Item.Rank)
           break;
       Heap[Loc] = Heap[Ch];
       Heap[Loc].Loc = Loc;
       Loc = Ch;
       Ch *= 2;
   }
   Heap[Loc] = Item;
   Item.Loc = Loc;
   Remove.Loc = 0;
   return Remove;
}

/*       
  The Insert function insert a HeapNode into the heap.
  When calling Insert(N), HeapNode N must belong to the heap.
*/

  public void Insert(HeapNode N)
  {
   int Ch, P;

   Ch = ++HeapCount;
   P = Ch / 2;
   while (P>0 && N.Rank < Heap[P].Rank) {
       Heap[Ch] = Heap[P];
       Heap[Ch].Loc = Ch;
       Ch = P;
       P /= 2;
   }
   Heap[Ch] = N;
   N.Loc = Ch;
  }
}

