/* Which .class file has changed
 * A: Just the greeting class file
 */

import java.util.Scanner;

public class TestGreeting
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        System.out.print("What do you want to say to the world? ");
        String input = in.nextLine();

        Greeting hello = new Greeting(input);
        hello.greet("World");
    }
}