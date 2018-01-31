public class Test
{
     public static void main(String[] args)
     {
          Matrix A = new Matrix(10);
          Matrix B = new Matrix(10);
          Matrix C;
          A.changeEntry(1, 1, -4);
          A.changeEntry(1, 2, -2);
          A.changeEntry(1, 3, 0);
          A.changeEntry(2, 5, 4);
          A.changeEntry(2, 1, -2);
          A.changeEntry(3, 1, 2);
          A.changeEntry(2, 2, -2);
          A.changeEntry(3, 3, 0);
          System.out.println("A: \n" + A);
          C = A.sub(A);
          System.out.println("C: \n" + C);
          System.out.println("C: NNZ: " + C.getNNZ());
          if (C.getNNZ() != 0 || A.getNNZ() != 6) return; //part 1 of test
          System.out.println("=================PART 2==================");
          B.changeEntry(1, 1, -4);
          B.changeEntry(1, 2, 0);
          B.changeEntry(2, 1, 0);
          B.changeEntry(2, 2, -2);
          B.changeEntry(2, 4, 2);
          B.changeEntry(3, 1, 2);
          B.changeEntry(3, 2, 2);
          B.changeEntry(7, 8, 5);
          System.out.println("B: \n" + B);
          System.out.println(A.equals(B));
          C = A.sub(B);
          System.out.println("C: \n" + C);
          System.out.println("C: NNZ: " + C.getNNZ());
          System.out.println(C.getNNZ() == 6);
     }
}