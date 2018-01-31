//-----------------------------------------------------------------------------
//	Joshua Cheung
//  jotcheun
//  PA3
//  Sparse.java
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;

public class Sparse
{
	public static void main(String[] args) throws IOException
	{
		if (args.length != 2)
		{
			System.err.println("Usage: Simulation input_file output_file");
            System.exit(1);
		}
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));

		int n = in.nextInt();
		int a = in.nextInt();
		int b = in.nextInt();
		int row, column;
		Double value;

		Matrix A = new Matrix(n);
		Matrix B = new Matrix(n);

		for (int i = 0; i < a; i++) 
		{
			row = in.nextInt();
			column = in.nextInt();
			value = in.nextDouble();
			A.changeEntry(row, column, value);
		}

		for (int i = 0; i < b; i++) 
		{
			row = in.nextInt();
			column = in.nextInt();
			value = in.nextDouble();
			B.changeEntry(row, column, value);
		}

		//Printing A
		out.println("A has " + A.getNNZ() + " non-zero entries:");
		out.println(A);

		//Printing B
		out.println("B has " + B.getNNZ() + " non-zero entries:");
		out.println(B);
		
		//Scalar A(1.5)
		out.println("(1.5)*A = ");
		out.println(A.scalarMult(1.5));

		// A+B
		out.println("A+B = ");
		out.println(A.add(B));

		// A+A
		out.println("A+A = ");
		out.println(A.add(A));
		
		// B-A
		out.println("B-A = ");
		out.println(B.sub(A));

		// A-A
		out.println("A-A = ");
		out.println(A.sub(A));
	
		//Transpose A
		out.println("Transpose(A) = ");
		out.println(A.transpose());

		// A*B
		out.println("A*B = ");
		out.println(A.mult(B));

		// B*B
		out.println("B*B = ");
		out.println(B.mult(B));

        out.close();
	}
}