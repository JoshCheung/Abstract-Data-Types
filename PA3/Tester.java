//-----------------------------------------------------------------------------
//  Joshua Cheung
//  jotcheun (1485979)
//  PA3
//  MatrixClient.java
//  A test client for the Matrix ADT
//-----------------------------------------------------------------------------

public class Tester{
    public static void main(String[] args){
        int i, j, n=3;
        Matrix A = new Matrix(n);
        Matrix B = new Matrix(n);

        A.changeEntry(1,1,1); B.changeEntry(1,1,1);
        A.changeEntry(1,2,2); B.changeEntry(1,2,0);
        A.changeEntry(1,3,3); B.changeEntry(1,3,1);
        A.changeEntry(2,1,4); B.changeEntry(2,1,0);
        A.changeEntry(2,2,5); B.changeEntry(2,2,1);
        A.changeEntry(2,3,6); B.changeEntry(2,3,0);
        A.changeEntry(3,1,7); B.changeEntry(3,1,1);
        A.changeEntry(3,2,8); B.changeEntry(3,2,1);
        A.changeEntry(3,3,9); B.changeEntry(3,3,1);

        System.out.println("A");
        System.out.println("=================================================");
        System.out.println(A);

        System.out.println("B");
        System.out.println("=================================================");
        System.out.println(B);

        System.out.println("NON-ZEROES: A");
        System.out.println("=================================================");
        System.out.println(A.getNNZ());

        System.out.println("NON-ZEROES: B");    
        System.out.println("=================================================");
        System.out.println(B.getNNZ());
       
        System.out.println("TRANSPOSE");
        System.out.println("=================================================");
        Matrix K = A.transpose();
        System.out.println(K);

        System.out.println("MULT");
        System.out.println("=================================================");
        Matrix G = A.mult(B);
        System.out.println(G);

        System.out.println("MULT");
        System.out.println("=================================================");
        Matrix F = A.mult(A);
        System.out.println(F);

        System.out.println("SCALARMULT");
        System.out.println("=================================================");
        Matrix H = A.scalarMult(3);
        System.out.println(H);

        System.out.println("COPY");
        System.out.println("=================================================");
        System.out.println("A: ");
        System.out.println(A);
        Matrix I = A.copy();
        System.out.println("=================================================");
        System.out.println(I);

        System.out.println("EQUALS");
        System.out.println("=================================================");
        boolean equal = A.equals(I);
        System.out.println("A & I :" + equal);
        equal = A.equals(B);
        System.out.println("A & B: " + equal +"\n");

        System.out.println("ADD");
        System.out.println("=================================================");
        Matrix D = A.add(B);
        System.out.println(D);

        System.out.println("ADD");
        System.out.println("=================================================");
        Matrix Z = A.add(A);
        System.out.println("Z: ");
        System.out.println(Z);

        System.out.println("SUB");
        System.out.println("=================================================");
        Matrix C = A.sub(B);
        System.out.println(C);

        System.out.println("SUB");
        System.out.println("=================================================");
        Matrix Y = A.sub(A);
        System.out.println(Y);

        System.out.println("CLEAR");
        System.out.println("=================================================");
        A.makeZero();
        System.out.println(A);
    }
}