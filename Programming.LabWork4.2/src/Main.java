import java.io.File;
import java.util.Scanner;

public class Main {
    static void replace(Scanner in)
    {
        int linesNumber = 0;
        char[] line;
        while (in.hasNext())
        {
            String x = in.nextLine();
            line = x.toCharArray();
            linesNumber++;
            linesNumber %= line.length;
            char[] changedLine = new char[line.length];
            for (int i = line.length-1; i >= 0; i--) {
                if(i+linesNumber >= line.length){
                    changedLine[i+linesNumber - line.length] = line[i];
                }
                else{
                    changedLine[i+linesNumber] = line[i];
                }
            }
            System.out.println(changedLine);
        }
    }

    public static void main(String[] args) throws java.io.FileNotFoundException{
        Scanner input = new Scanner(new File("efgabcd.txt")); // замени на полное местоположение файла
        replace(input);
    }
}
