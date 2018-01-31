import java.util.*;
import java.io.*;

class Matrix {
	private int size;
	private int NNZ;
	List[] row;
	
	//default contructor
	private class Entry {
		//Fields
		int col;
		double val;
			
		//constructor
		Entry (int column, double value) { this.col = column; this.val = value;}

		//toString
		public String toString(){
			return ("("+ col +", "+ val +")");
                }

		//equals
		public boolean equals(Entry x){
			return (this.val == x.val);
		}
	}

	//Constructor
	Matrix(int n) {// Makes a new n x n zero Matrix. pre: n>=1
		if ( n < 1) {
			throw new RuntimeException(
					"Matrix Error: invalid size");
		}
		this.row = new List[n+1];
		for (int i = 1; i < n+1; i++) {
			row[i] = new List();
		}
		size = n;
		NNZ = 0;
	}
	
	// Access functions
	int getSize() {// Returns n, the number of rows and columns of this Matrix
		return size;
	}
	int getNNZ() {// Returns the number of non-zero entries in this Matrix
		return NNZ;
	}

	public boolean equals(Matrix x) {// overrides Object's equals() method
		boolean eq = false;
		if (x.size != this.size){return false;}
		if (x.NNZ != this.NNZ){return false;}
		if (x != null && this != null) {
			System.out.println("list are not null");	
			for (int i = 1; i <= size; i++){
			System.out.println("iterating through index "+ i);
				if ((x.row[i]).length() != 0 && (this.row[i]).length() != 0){
					this.row[i].moveFront();
					x.row[i].moveFront();
					while ((this.row[i]).get() != null && (x.row[i]).get()!= null) {
						System.out.println("comparing"+ this.row[i].get()+ " && " +x.row[i].get());
						eq = (this.row[i].equals(x.row[i]));//both col and val match
						if(!eq){
							System.out.println("entering falsey");
							return false;
						}
						System.out.println("move next");
						(this.row[i]).moveNext();//move each to next
						(x.row[i]).moveNext();
					}
				} else
				{
					eq = true;
				}
System.out.println("out of the whiel loop in equals function");
				
			}
		}
		//size = 0;
		//NNZ = 0;
		return eq;
	}

	// Manipulation procedures
	void makeZero() {// sets this Matrix to the zero state
		System.out.println(size +" is size");
		System.out.println(row[5].length());
		for (int i = 1; i <= size; i++) {
			if (row[i].length() > 0){
				//for (int i = 1; i <= size && row[i] != null; i++) {
				row[i].moveFront();
				System.out.println("helloo---------------");
				System.out.println("ghjk" + row[i].length());
				while (row[i].length() > 0) {
					//System.out.println("row: " + i + ",data: " + row[i].get()+ "is being deleted");
					row[i].deleteFront();
				}
			}
		}
		this.NNZ = 0;
	}

	// returns a new Matrix having the same entries as this Matrix
	Matrix copy() {
		Matrix mT = new Matrix(this.size);
		System.out.println("In copy: size that is being copied" + this.size);
		for (int i = 1; i <= size; i++) {
			mT.row[i] = this.row[i];//might need to do each element//or use copy function
		}
		mT.NNZ = this.NNZ;
		return mT;
	}


	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x){
		System.out.println("In row "+ i + "where it ENTERSSSSS");
		System.out.println("the j should be less than the size" + j + "<=" + getSize());
		System.out.println("i is "+i);
		if (row[i] != null && 1<=i && i<=getSize() && 1<=j && j<=getSize()){
			Entry add = new Entry(j, x);
			System.out.println("row length is " + row[i].length());
			if(row[i].length() == 0 && x != 0){
				System.out.println("In changeEntry: just append");
				row[i].append(add);
				this.NNZ++;
				return;
			}
			else if (row[i].length() == 0){
				System.out.println("In changeEntry: do nothing");
				return;
			}
			this.row[i].moveFront();
			int test = ((Entry)(this.row[i]).get()).col;
			while (test > 0){
				System.out.println("test: "+test);
				System.out.println("j: "+j);
				//found a match
				if(test == j){
					System.out.println("In changeEntry: found index "+ test);
					//if value inserted is zero
					if(x == 0){
						System.out.println("In changeEntry: delete it ");
						(this.row[i]).delete();
						this.NNZ--;
						return;
					}
					//change value of list
					else {
						System.out.println("In changeEntry: change it ");
						Entry fix = (Entry) (this.row[i]).get();
						fix.val = x;
						//this.NNZ++;
					}
					return;
				}
				//if test has surpass last element
				if(test > j){
					System.out.println("In changeEntry: found index "+ test+",pastIt");
					(this.row[i]).insertBefore(add);
					this.NNZ++;
					return;
				}
				(this.row[i]).moveNext();
				if ((this.row[i]).index() > 0) {
					test = ((Entry)(this.row[i]).get()).col;
				}
				else {
					test = -1;
				}
			}
			if (x == 0) {
				return;
			}
			row[i].append(add);
                        this.NNZ++;
                        return;
		}
		System.out.println("Does not enter if in change entry,, skips to end");
	}
	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
		for (int i = 1; i <= size; i++){
			if (row[i].length() != 0){
				(row[i]).moveFront();
				while ((this.row[i]).index() >= 0){
					//System.out.println("index: "+(this.row[i]).index() +"row: "+ (this.row[i]).length());
					Entry fix = (Entry) (this.row[i]).get();
					fix.val *= x;
					(this.row[i]).moveNext();
				}
			}
		}
		return this;
	}
	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M){
		Matrix sum = new Matrix(size);
		//if (M.equals(this)){
		//	System.out.println("M equals this");
		//	sum = M.scalarMult(2);
		//	return sum;
		//}
		if (this.getSize() == M.getSize()){
			for (int i = 1; i <= this.getSize(); i++){
				System.out.println("In add: we iterate loop " +this.getSize()+" times, row "+i);
				if (this.row[i].length() != 0 || M.row[i].length() != 0){
					if (this.row[i].length() != 0){
						(this.row[i]).moveFront();
					}
					if(M.row[i].length() != 0){
						(M.row[i]).moveFront();
					}//maybe
					if(sum.row[i].length() != 0){
						(sum.row[i]).moveFront();
					}
					while ((this.row[i]).index() >= 0 || (M.row[i]).index() >= 0){
						System.out.println("index: "+(this.row[i]).index());
						System.out.println("index: "+(M.row[i]).index());
						//nothing left in this matrix row
						if ((this.row[i]).index() < 0){
							System.out.println("nothing in this left");
							sum.changeEntry(i,((Entry)(M.row[i]).get()).col, ((Entry)(M.row[i]).get()).val);
							(M.row[i]).moveNext();
						}
						//nothing in M left
						else if ((M.row[i]).index() < 0){
							System.out.println("nothing in M left");
                                                        sum.changeEntry(i,((Entry)(this.row[i]).get()).col, ((Entry)(this.row[i]).get()).val);
							(this.row[i]).moveNext();
                                                }
						else {
							//nothing is wrong with M
							Entry fix = (Entry) (this.row[i]).get();
							Entry addIt = (Entry) (M.row[i]).get();
							//if (((M.row[i]).index()) > (this.row[i]).index()){
							if (addIt.col > fix.col){
								System.out.println("nothing is wrong with og");
								(this.row[i]).moveNext();
							}
							//something is wrong with og
							//else if ((M.row[i]).index() < (this.row[i]).index()){
							else if (addIt.col < fix.col){
								System.out.println("something is wrong with og");
                                        	        	//(sum.row[i]).append((M.row[i]).get());
								(M.row[i]).moveNext();	
							}
							else {//index are both the same
								System.out.println("First adding "+ fix.val+ " and "+ addIt.val);
								sum.changeEntry(i,fix.col + addIt.col, fix.val + addIt.val);
								(this.row[i]).moveNext();
								System.out.println("this index"+(this.row[i]).index());
								(M.row[i]).moveNext();
								System.out.println("M index"+(M.row[i]).index());
							}
						}
					}
				}
			}
		}
		return sum;
	}

	 // returns a new Matrix that is the difference of this Matrix with M
	 // pre: getSize()==M.getSize()
	Matrix sub(Matrix M){
    	Matric sub = new Matrix(size);
        //make extra case if it is equal to zero, remove the whole thing
		if (this.equals(M)){
        	sub = makezero();
        }	
      	else{
          	sub = this.add(M.scalarMult(-1);
        }
        return sub;
    }
      
	// returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix t = new Matrix(this.size);
		for(int i = 1; i <= this.size; i++){
			if ((this.row[i]).length() != 0){
				(this.row[i]).moveFront();
				while ((this.row[i]).index() >= 0){
					Entry add = (Entry) (this.row[i]).get();
					t.changeEntry(add.col, i, add.val);
					row[i].moveNext();
				}
			}
		}
		t.NNZ = this.NNZ;
		t.size = this.size;
		return t;
	}
	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix mult(Matrix M){//have not wrote yet--------------------------------------------------------------
		return this;
	}
	
	// Other functions
	// overrides Object's toString() method
	public String toString(){
		System.out.println("Here is the printed matrix");
		String full = "";
        for (int i = 1; i <= size; i++){
        	full += (i +": " + row[i] + "\n");
        }
	}	
}