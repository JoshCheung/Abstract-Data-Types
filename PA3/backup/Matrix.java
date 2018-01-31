//-----------------------------------------------------------------------------
//	Joshua Cheung
//  jotcheun
//  PA3
//  Matrix.java
//-----------------------------------------------------------------------------

public class Matrix
{
	private class Entry
	{
		public int column;
		public double value;

		public Entry(int column, double value)
		{
			this.column = column;
			this.value = value;
		}

		public String toString()
		{
			return ("(" + column + ", " + value + ")");
		}

		public boolean equals(Object x)
		{
			Entry entry = (Entry)x;

			if (this.column == entry.column && this.value == entry.value)
			{
				return true;
			}
			return false;
		}
	}

	public List [] row;
	public int non_zero;
	public int size;

	// Constructor
	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n)
	{
		row = new List [n + 1];

		if (n == 0 )
		{
			throw new RuntimeException("List Error: Matrix() called on empty Matrix");
		}
		else
		{
			for (int i = 1; i <= n; i++)
			{
				row[i] = new List();
			}
		}
		non_zero = 0;
		size = n;
	}

	// Access functions
	// Returns n, the number of rows and columns of this Matrix
	int getSize()
	{   
		return size;
	} 

	// Returns the number of non-zero entries in this Matrix
	int getNNZ()
	{	
		non_zero = 0;
		for (int i = 1; i <= size; i++)
		{
			if (!row[i].isEmpty())
			{
				non_zero += row[i].length();
			}
		}	
		return non_zero;
	}

	
	// overrides Object's equals() method
	public boolean equals(Matrix x)
	{
		boolean equal = true;

		if (x.getSize() !=  getSize())
		{
			equal = false;
		}
		else if (x.getNNZ() != getNNZ())
		{
			equal = false;
		}
		else
		{
			for (int i = 1; i <= size; i++)
			{
				equal = (row[i].equals(x.row[i]));	
				if (!equal)
				{
					return false;
				}
			}
		}
		return equal;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------
//														Manipulation procedures
//---------------------------------------------------------------------------------------------------------------------------------------
	
	// sets this Matrix to the zero state 
	void makeZero()
	{
		for (int i = 1; i <= size; i++)
		{
			row[i].clear();
		}
		non_zero = 0;
	}
	// returns a new Matrix having the same entries as this Matrix
	Matrix copy()
	{
		Matrix copy = new Matrix(size);
		Entry current;

		for (int i = 1; i <= size; i++)
		{
			if( !row[i].isEmpty())
			{
				row[i].moveFront();

				while (row[i].index() != -1)
				{
					current = ((Entry)row[i].get());
					copy.changeEntry(i, current.column, current.value);
					row[i].moveNext();
				}
			}	
		}
		return copy;
	}

	// changes ith row, jth column of this Matrix to x
 	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x)
	{
		Entry entry = new Entry(j,x);

		if (getSize() == 0)
		{
			throw new RuntimeException("List Error: changeEntry()) called on empty Matrix");
		}
		else
		{
			
			if (row[i].isEmpty() && x != 0)
			{
				row[i].append(entry);
				return;
			}
			else if (row[i].isEmpty() && x == 0)
			{
				return;
			}
			Entry current;
			Entry front = ((Entry)row[i].front());
			Entry back = ((Entry)row[i].back());
			row[i].moveFront();

			if (x == 0)
			{
				while (row[i].index() != -1)
				{
					current = ((Entry)row[i].get());

					if (current.column == j)
					{
						row[i].delete();
					}
					else 
					{
						row[i].moveNext();
					}
				}
				return;
			}
			else
			{
				if (j > back.column)
				{
					row[i].append(entry);
					return;
				}
				else if (j < front.column)
				{
					row[i].prepend(entry);
					return;
				}
				while (row[i].index() != -1)
				{
					current = ((Entry)row[i].get());
					if (current.column == j)
					{
						current.value = x;
						return;
					}
					if (current.column < j)
					{
						row[i].moveNext();
						current = ((Entry)row[i].get());

						if (current.column > j)
						{
							row[i].insertBefore(entry);
							return;
						}
					}
				}
				row[i].moveNext();
			}
		}
	}			
	
 	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x) 
	{
		Matrix scaled = new Matrix(size);
		Entry current;

		for (int i = 1; i <= size; i++)
		{
			if (!row[i].isEmpty())
			{
				row[i].moveFront();

				while (row[i].index() != -1)
				{
					current = ((Entry)row[i].get());
					scaled.changeEntry(i, current.column, current.value*x);
					row[i].moveNext();
				}	
			}
		}
		return scaled;
	}

	// returns a new Matrix that is the sum of this Matrix with M
 	// pre: getSize()==M.getSize()
 	
	Matrix add(Matrix M)
	{
		Matrix add = new Matrix(size);

		if (!this.equals(M))
		{
			for(int i = 1; i <= size; i++)
			{
				Entry current;
				Entry m_current;

				if (!row[i].isEmpty() && M.row[i].isEmpty())
				{
					row[i].moveFront();

					while (row[i].index() !=-1)
					{
						current = ((Entry)row[i].get());
						add.changeEntry(i, current.column, current.value);
						row[i].moveNext();
					}
				}
				else if (row[i].isEmpty() && !M.row[i].isEmpty())
				{
					M.row[i].moveFront();

					while (M.row[i].index() !=-1)
					{
						m_current = ((Entry)M.row[i].get());
						add.changeEntry(i, m_current.column, m_current.value);
						M.row[i].moveNext();
					}
				}
				else if (!row[i].isEmpty() && !M.row[i].isEmpty())
				{	
					row[i].moveFront();
					M.row[i].moveFront();

					while (row[i].index() != -1 && M.row[i].index() != -1)
					{
						current = ((Entry)row[i].get());
						m_current = ((Entry)M.row[i].get());

						if (current.column == m_current.column)
						{
							add.changeEntry(i, current.column, current.value + m_current.value);
							row[i].moveNext();
							M.row[i].moveNext();
						}	
						else if (current.column < m_current.column)
						{
							add.changeEntry(i, current.column, current.value);
							row[i].moveNext();
						}
		 				else if (current.column > m_current.column) 
		 				{
		 					add.changeEntry(i, m_current.column, m_current.value);
		 					M.row[i].moveNext();
		 				}
					}
					while (row[i].index()!= -1)
					{
						current = ((Entry)row[i].get());
						add.changeEntry(i, current.column, current.value);
						row[i].moveNext();

					}
					while (M.row[i].index()!= -1)
					{
						m_current = ((Entry)M.row[i].get());
						add.changeEntry(i, m_current.column, m_current.value);
						M.row[i].moveNext();
					}
				}
			}
		}
		else 
		{
			add = this.scalarMult(2);
		}
		return add;
	}
	// returns a new Matrix that is the difference of this Matrix with M
 	// pre: getSize()==M.getSize()
 	
	Matrix sub(Matrix M)
	{
		Matrix sub = new Matrix(size);

		if (size != M.getSize()) 
		{
			throw new RuntimeException("Matrix Error: add() called on matrices with different sizes");
		}
		Entry current;
		Entry m_current;

		if (!this.equals(M))
		{
			sub = this.add(M.scalarMult(-1));
		}
		else
		{
			sub.makeZero();
		}
		return sub;
	}

	// returns a new Matrix that is the transpose of this Matrix
	Matrix transpose()
	{
		Matrix T = new Matrix(size);
		Entry current;

		for (int i = 1; i <= size; i++)
		{
			if (!row[i].isEmpty())
			{
				row[i].moveFront();

				while (row[i].index() != -1)
				{
					current = ((Entry)row[i].get());
					T.changeEntry(current.column, i, current.value);
					row[i].moveNext();
				}	
			}	
		}
		return T;
	}

	// returns a new Matrix that is the product of this Matrix with M
 	// pre: getSize()==M.getSize()

	Matrix mult(Matrix M)
	{
		if (size != M.getSize()) 
		{
			throw new RuntimeException("Matrix Error: mult() called on matrices with different sizes");
		}

		Matrix m = new Matrix(size);
		Matrix t = M.transpose();
		double sum;

		for (int i = 1; i <= size; i++)
		{
			if (row[i].length() > 0)
			{
				for (int j = 1; j <= size; j++)
				{
					if (t.row[j].length() > 0)
					{	
						sum = dot(row[i], t.row[j]);
						m.changeEntry(i, j, sum);
					}
				}
			}
		}
		return m;
	}

	private static double dot(List P, List Q)
	{
		double sum = 0;
		P.moveFront();
		Q.moveFront();	
		Entry cur_P;
		Entry cur_Q;

		while (P.index() != -1 && Q.index() != -1 )
		{
			cur_P = (Entry)P.get();
			cur_Q = (Entry)Q.get();

			if (cur_P.column == cur_Q.column)
			{
				sum += cur_P.value * cur_Q.value;
				P.moveNext();
				Q.moveNext();
			}	
			else if (cur_P.column < cur_Q.column)
			{
				P.moveNext();
			}
		 	else
		 	{
		 		Q.moveNext();
		 	}
		}
		return sum;
	}


	public String toString() 
	{
		StringBuffer sb = new StringBuffer();

      	for (int i = 1; i <= size; i++)
      	{
      		if (!row[i].isEmpty())
      		{
				sb.append(i + ": " + row[i] + "\n");
      		}	
      	}
    	return new String(sb);
	}
}
