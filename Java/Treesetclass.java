import java.util.Scanner;
import java.util.TreeSet;
public class Treesetclass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TreeSet<Integer> set = new TreeSet<>();
        // 1. Add elements to the TreeSet
        System.out.println("Enter the number of elements you want to add in the TreeSet");
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.println("Enter element " + i + " :");
            set.add(sc.nextInt());
        }
        // 2. Display the elements of the TreeSet
        System.out.println("The elements in the TreeSet are: ");
        for (Integer element : set) {
            System.out.println(element);
        }
        // 3.Remove an element from the TreeSet
        System.out.println("Enter the element you want to remove from the TreeSet");
        int elementToRemove = sc.nextInt();
        if(set.contains(elementToRemove))
            set.remove(elementToRemove);
        else
            System.out.println("Element not found in the TreeSet");

        System.out.println("The updated TreeSet is: ");
        for (Integer element : set) {
            System.out.println(element);
        }
        //4.First record of the treeset
        System.out.println("The first record of the TreeSet is: " + set.first());
        //5.Last record of the treeset
        System.out.println("The last record of the TreeSet is: " + set.last());
    }
}

// Treeset doesnit allow to store the dyplicates and also it automatically arranges the elements in ascending order. 
// It is implemented using a Red-Black tree data structure, which ensures that the elements are always sorted and allows for efficient insertion, deletion, and search operations.
//that is the difference between the treeset and the arraylist
