import java.io.File;
import java.util.Scanner;

public class Main {
    static void replace(Scanner in)
    {
        int linesNumber = 0;
        char[] a;
        while (in.hasNext())
        {
            String x = in.nextLine();
            a = x.toCharArray();
            linesNumber++;
            linesNumber %= a.length;
            char[] b = new char[a.length];
            for (int i = a.length-1; i >= 0; i--) {
                if(i+linesNumber >= a.length){
                    b[i+linesNumber-a.length] = a[i];
                }
                else{
                    b[i+linesNumber] = a[i];
                }
            }
            System.out.println(b);
        }
    }

    public static void main(String[] args) throws java.io.FileNotFoundException{
        Scanner input = new Scanner(new File("efgabcd.txt")) ;
        replace(input);
    }
}
