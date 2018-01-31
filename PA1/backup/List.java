//Joshua Cheung
//jotcheun
//List.java
//CMPS101

public class List
{
	private class Node
	{
		public int data;
		public Node left;
		public Node right;

		public Node(int data)
		{
			this.data = data;
			left = null;
			right = null;
		}
	}

	private int numitems = 0;
	private Node front;
	private Node back;
	private Node cursor; 
	private int index;

	public List()
	{
		front = back = null;
		cursor = null; //used for iteration
		numitems = 0;
		index = 0;
	}

	public int length()
	{
		return numitems;
	}

	int index()
	{  // If cursor is defined, returns the index of the cursor element, // otherwise returns -1.
		if (cursor != null)
		{
			return index; 
		}
		return -1;
	}

	int front()
	{ // Returns front element. Pre: length()>0
		if (isEmpty())
		{
			throw new RuntimeException("List Error: Front() called on empty List");
		}
		return front.data;
	}

	int back() 
	{ // Returns back element. Pre: length()>0
		if (isEmpty())
		{
			throw new RuntimeException("List Error: back() called on empty List");
		}
		return back.data;
	}

	int get()
	{ // Returns cursor element. Pre: length()>0, index()>=0
		if (isEmpty() || index() < 0)
		{
			throw new RuntimeException("List Error: get() called on empty List");
		}
		return cursor.data;
	}

	boolean equals(List l)
	{ // Returns true if this List and L are the same integer   // sequence. The cursor is ignored in both lists.
		boolean equal = false; 
		Node n, m;
		n = this.front;
		m = l.front;
		equal = (length() == l.length());
		while(equal && n!= null)
		{
			equal = (n.data == m.data);
			m = m.right;
			n = n.right;
		}
		return equal;
	}

	boolean isEmpty()
	{
		return numitems == 0;
	}


//---------------------------------------------------------------------------------------------------------------------------------------
//														Manipulation procedures
//---------------------------------------------------------------------------------------------------------------------------------------
	void clear()
	{	 // Resets this List to its original empty state.
		front = back = null;
		numitems = 0;
		cursor = null;
		index = 0;
	}
	
	void moveFront()
	{    // If List is non-empty, places the cursor under the front element, otherwise does nothing
		if (isEmpty())
		{
			throw new RuntimeException("List Error: moveFront() called on empty List");
		}
		else 
		{
			cursor = front;
			index = 0;
		}
	}

	void moveBack()
	{    // If List is non-empty, places the cursor under the back element, otherwise does nothing
		if (isEmpty())
		{
			throw new RuntimeException("List Error: moveback() called on empty List");
		}
		else
		{
			cursor = back;
			index = length()-1;
		}
	}

	void movePrev()
	{    // If cursor is defined and not at front, moves cursor one step toward front of this List, if cursor is defined and at front, cursor becomes undefined, if cursor is undefined does nothing.
		if (cursor == front)
		{
			index = -1;
			cursor = null;
		}
		else if (cursor != null)
		{
			cursor = cursor.left;
			index--;
		}
	}

	void moveNext()
	{    // If cursor is defined and not at front, moves cursor one step toward back of this List, if cursor is defined and at back, cursor becomes undefined, if cursor is undefined does nothing.
		if (cursor == back)
		{
			index = -1;
			cursor = null;
		}
		else 
		{
			cursor = cursor.right;
			index++;
		}
	}

	void prepend(int data)
	{		//  Insert new element into this List. If List is non-empty, insertion takes place before front element.
		Node n = new Node(data);
		if (isEmpty())
		{
			front = back = n;
			index++;
			numitems++;
		}
		else
		{
			n.right = front;
			front.left = n;
			front = n;
			index++;
			numitems++;
		}
	}

	void append(int data) 
	{		// Insert new element into this List. If List is non-empty, insertion takes place after back element.
		Node n = new Node(data);
		if (isEmpty())
		{
			front = back = n;
			numitems++;
		}
		else
		{
			back.right = n;
			n.left = back;
			back = n;
			numitems++;
		}
		
	}

	void insertBefore(int data){	// Insert new element before cursor. Pre: length() > 0, index() >= 0
		Node n = new Node(data);
		if (isEmpty() || index() < 0)
		{
			front = back = n;
			index++;
			numitems++;
		} 
		else 
		{
			if (cursor.left == null)
			{
				prepend(data);
			} 
			else 
			{
				Node temp = cursor.left;
				temp.right = n;
				n.right = cursor;
				cursor.left = n;
				n.left = temp;
				numitems++;
				index++;
			}
		}
	}

	void insertAfter(int data)
	{		// Insert new element after cursor. Pre: length() > 0, index() >= 0
		Node n = new Node(data);
		
		if (isEmpty() || index() < 0)
		{
			front = back = n;
			numitems++;
		}
		else
		{
			if (cursor.right == null)
			{
				append(data);
			}
			else
			{
				Node temp = cursor.right;
				cursor.right = n;
				temp.left = n;
				n.right = temp;
				n.left = cursor;
				numitems++;
			}
		}	
	}

	void deleteFront()
	{     // Deletes the front element. Pre: length()>0
		if (isEmpty())
		{
			throw new RuntimeException("List Error: deleteFront() called on empty List");
		} 
		else if (front == back)
		{
			cursor = front = back = null;
		}
		else
		{
			if (cursor == front)
			{
				cursor = null;
			}
			front = front.right;
			front.left = null;
		}
		numitems--;
		index--;
	}

	void deleteBack()
	{		// Deletes the back element. Pre: length()>0
		if (isEmpty())
		{
			throw new RuntimeException("List Error: deleteBack() called on empty List");	
		} 
		else if (front == back)
		{
			cursor = front = back = null;
		} 
		else 
		{
			if (cursor == back)
			{
				cursor = null;
			}
			back = back.left;
			back.right = null;
		}
		numitems--;
	}

	void delete()
	{      // Deletes cursor element, making cursor undefined. Pre: length() > 0, index >= 0
		if (isEmpty() || index() < 0)
		{
			throw new RuntimeException("List Error: delete() called on empty List");
		} 
		else if (cursor == front && cursor == back)
		{
			cursor = front = back = null;
		} 
		else if (cursor == front)
		{
			deleteFront();
		} 
		else if (cursor == back)
		{
			deleteBack();		
		} 
		else if (cursor.left == null && cursor.right == null) 
		{
			front = back = null;
			index--;
			numitems--;
		} 
		else
		{
			cursor.left.right = cursor.right;
			cursor.right.left = cursor.left;
			cursor = null;
			index--;
			numitems--;
		}
	}
	

	public String toString()
	{  // Overrides Object's toString method. Returns a String representation of this List consisting of a space separated sequence of integers, with front on left.
		StringBuffer sb = new StringBuffer();
      	Node n = front;
     	while (n != null )
     	{
     		sb.append(n.data);
     		if (n.right != null)
     		{
				sb.append(" ");
     		}	
        	n = n.right;
     	}
    	return new String(sb);
	}

    List copy()
    {	// Returns a new List representing the same integer sequence as this List. The cursor in the new list is undefined, regardless of the state of the cursor in this List. This List is unchanged.
		List l = new List();

		if (!isEmpty())
		{
			for (Node n = front; n!=null; n = n.right)
			{
				l.append(n.data);
			}
		}
		return l;
	}
	
	List concat(List L)
	{ 	// Returns a new List which is the concatenation of this list followed by L. 
		List C = null;
		List D = null;

		if (L.isEmpty())
		{
			return this;
		}
		else if (this.isEmpty())
		{
			return L;
		}			
		else if (L.isEmpty() && this.isEmpty())
		{
			throw new RuntimeException("List Error: concat() called on empty List");
		}
		else
		{
			C = this.copy();
			D = L.copy();
			System.out.println(C);
			C.back.right = D.front;
			D.front.left = C.back;
		}		
		C.numitems = C.numitems + L.numitems;
		return C;
	}
}
