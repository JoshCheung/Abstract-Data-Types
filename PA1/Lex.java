//Joshua Cheung
//jotcheun
//Lex.java

import java.io.*;
import java.util.Scanner;
public class Lex
{
    public static void main(String [] args) throws IOException
    {
        if (args.length != 2) 
        {
            System.err.println("Usage: Simulation input_file output_file");
            System.exit(1);
        }
        Scanner in_file = new Scanner(new File(args[0]));
        PrintWriter out_file = new PrintWriter(new FileWriter(args[1]));
        String s = "";
        while (in_file.hasNextLine())
        {
            s+= in_file.nextLine() + "\n";
        }
        String [] words = s.split("\n");
        List sort_indices = compare(words); 
       
        for (sort_indices.moveFront(); sort_indices.index() >= 0; sort_indices.moveNext())
        {
            out_file.println(words[sort_indices.get()]);
        }
        out_file.close();
    }

    public static List compare(String [] words)
    {
        List indices = new List();
        indices.append(0);
        String front;
        String back;
        String current;
        
        for (int i = 1; i < words.length; i++)
        {
            current = words[i];
            front = words[indices.front()];
            back = words[indices.back()];
            indices.moveFront();
            if (current.compareTo(back) > 0)
            { // checks whether current goes after back
                indices.append(i);
                continue;                   // after appending go move to the front and check elements in while loop
            }
            else if (current.compareTo(front) < 0) 
            { //checks to see if current goes before front
                indices.prepend(i);         // after prepending check elements in list
                continue;                   // go through loop again
            }
            indices.moveFront();
            while (indices.index() != -1)
            {
                if(current.compareTo(words[indices.get()]) < 0) 
                {
                    indices.insertBefore(i); //after inserting break out of loop for next element
                    break;
                }
                else 
                {
                    indices.moveNext(); //if not bigger move to the next node and check again
                }
            }
        }
        return indices;
    }
}