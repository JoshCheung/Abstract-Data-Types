public class ListTester{
	public static void main(String[] args){
		List A = new List();
        A = new List();
       	A.prepend(34);
       	A.prepend(4);
       	A.prepend(354);
      	A.prepend(3674);
      	A.moveBack();
      	A.insertBefore(435);
      	System.out.println(A);
      	System.out.println(A.index());
      	if (A.index() != 4) return; // didn't pass part 1 of test
      	A.prepend(324);
      	A.prepend(33464);
     	A.prepend(3498);
      	A.moveFront();
      	A.insertBefore(67);
      	System.out.println(A.index());

    }
}
