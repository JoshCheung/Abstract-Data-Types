//-----------------------------------------------------------------------------
//	Joshua Cheung
//  jotcheun
//  PA3
//  List.java
//-----------------------------------------------------------------------------

public class List
{
	private class Node
	{
		public Object data;
		public Node prev;
		public Node next;

		public Node(Object data)
		{
			this.data = data;
			prev = null;
			next = null;
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
		cursor = null;
		numitems = 0;
		index = 0;
	}

	public int length()
	{
		return numitems;
	}

	// If cursor is defined, returns the index of the cursor element, 
	// otherwise returns -1.
	int index()
	{  
		if (cursor != null)
		{
			return index; 
		}
		return -1;
	}

	// Returns front element. Pre: length()>0
	Object front()
	{ 
		if (isEmpty())
		{
			throw new RuntimeException("List Error: Front() called on empty List");
		}
		return front.data;
	}

	// Returns back element. Pre: length()>0
	Object back() 
	{ 
		if (isEmpty())
		{
			throw new RuntimeException("List Error: back() called on empty List");
		}
		return back.data;
	}

	// Returns cursor element. Pre: length()>0, index()>=0
	Object get()
	{ 
		if (isEmpty() || index() < 0)
		{
			throw new RuntimeException("List Error: get() called on empty List");
		}
		return cursor.data;
	}

	// Returns true if this List and L are the same integer 
	// sequence. The cursor is ignored in both lists.
	
	boolean equals(List l)
	{   
		boolean equal = false; 
		Node n, m;
		n = this.front;
		m = l.front;
		equal = (length() == l.length());
		while(equal && n!= null)
		{
			equal = (n.data.equals(m.data));
			m = m.next;
			n = n.next;
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

	// Resets this List to its original empty state.
	void clear()
	{	 
		front = back = null;
		numitems = 0;
		cursor = null;
		index = 0;
	}
	
	// If List is non-empty, places the cursor under the front element, otherwise does nothing
	void moveFront()
	{   
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

	// If List is non-empty, places the cursor under the back element, otherwise does nothing
	void moveBack()
	{    
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

	// If cursor is defined and not at front, moves cursor one step toward front of this List, if cursor is defined and at front,
	// cursor becomes undefined, if cursor is undefined does nothing.
	void movePrev()
	{     
		if (cursor == front)
		{
			index = -1;
			cursor = null;
		}
		else if (cursor != null)
		{
			cursor = cursor.prev;
			index--;
		}
	}

	// If cursor is defined and not at front, moves cursor one step toward back of this List, if cursor is defined and at back, 
	// cursor becomes undefined, if cursor is undefined does nothing.
	void moveNext()
	{  
		if (cursor == back)
		{
			index = -1;
			cursor = null;
		}
		else 
		{
			cursor = cursor.next;
			index++;
		}
	}

	// Insert new element into this List. If List is non-empty, insertion takes place before front element.
	void prepend(Object data)
	{		
		Node n = new Node(data);
		if (isEmpty())
		{
			front = back = n;
			index++;
			numitems++;
		}
		else
		{
			n.next = front;
			front.prev = n;
			front = n;
			index++;
			numitems++;
		}
	}

	// Insert new element into this List. If List is non-empty, insertion takes place after back element.
	void append(Object data) 
	{		
		Node n = new Node(data);
		if (isEmpty())
		{
			front = back = n;
			numitems++;
		}
		else
		{
			back.next = n;
			n.prev = back;
			back = n;
			numitems++;
		}
	}

	// Insert new element before cursor. Pre: length() > 0, index() >= 0
	void insertBefore(Object data)
	{	
		Node n = new Node(data);
		if (isEmpty() || index() < 0)
		{
			front = back = n;
			index++;
			numitems++;
		} 
		else 
		{
			if (cursor.prev == null)
			{
				prepend(data);
			} 
			else 
			{
				Node temp = cursor.prev;
				temp.next = n;
				n.next = cursor;
				cursor.prev = n;
				n.prev = temp;
				numitems++;
				index++;
			}
		}
	}

	// Insert new element after cursor. Pre: length() > 0, index() >= 0
	void insertAfter(Object data)
	{		
		Node n = new Node(data);
		
		if (isEmpty() || index() < 0)
		{
			front = back = n;
			numitems++;
		}
		else
		{
			if (cursor.next == null)
			{
				append(data);
			}
			else
			{
				Node temp = cursor.next;
				cursor.next = n;
				temp.prev = n;
				n.next = temp;
				n.prev = cursor;
				numitems++;
			}
		}	
	}

	// Deletes the front element. Pre: length()>0
	void deleteFront()
	{     
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
			front = front.next;
			front.prev = null;
		}
		numitems--;
		index--;
	}

	// Deletes the back element. Pre: length()>0
	void deleteBack()
	{		
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
			back = back.prev;
			back.next = null;
		}
		numitems--;
	}

	// Deletes cursor element, making cursor undefined. Pre: length() > 0, index >= 0
	void delete()
	{      
		if (isEmpty() || index() < 0)
		{
			throw new RuntimeException("List Error: delete() called on empty List");
		} 
		else if (cursor == front && cursor == back)
		{
			cursor = front = back = null;
			numitems--;
		} 
		else if (cursor == front)
		{
			deleteFront();
		} 
		else if (cursor == back)
		{
			deleteBack();		
		} 
		else if (cursor.prev == null && cursor.next == null) 
		{
			front = back = null;
			index--;
			numitems--;
		} 
		else
		{
			cursor.prev.next = cursor.next;
			cursor.next.prev = cursor.prev;
			cursor = null;
			index--;
			numitems--;
		}
	}
	
	// Overrides Object's toString method. Returns a String representation of this List
	// consisting of a space separated sequence of integers, with front on prev.
	public String toString()
	{   
		StringBuffer sb = new StringBuffer();
      	Node n = front;
     	while (n != null )
     	{
     		sb.append(n.data);
     		if (n.next != null)
     		{
				sb.append(" ");
     		}	
        	n = n.next;
     	}
    	return new String(sb);
	}

	 List copy()
	 {
		List l = new List();

		if (!isEmpty())
		{
			for (Node n = front; n!=null; n = n.next)
			{
				l.append(n.data);
			}
		}
		return l;
	}
	
	List concat(List L)
	{ 
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
			C.back.next = D.front;
			D.front.prev = C.back;
		}		
		C.numitems = C.numitems + L.numitems;
		return C;
	}
}
