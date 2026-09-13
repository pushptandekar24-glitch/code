import java.util.LinkedList;
import java.util.Scanner;
import java.util.Collections;
public class LinkedList1 {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of elements you want to add in the LinkedList");
        int n = scanner.nextInt();
        System.out.println("Enter " + n + " elements to add to the LinkedList:");
        for (int i = 1; i <= n; i++) {
            list.add(scanner.nextInt());
        }
        System.out.println("Linked List: " + list);
        Collections.sort(list);
        System.out.println("Sorted Linked List: " + list);
        Collections.reverse(list);
        System.out.println("Reversed Linked List: " + list);
        System.out.println("Minimum element in the Linked List: " + Collections.min(list));
        System.out.println("Maximum element in the Linked List: " + Collections.max(list));
        //FInding largest and the smallest element using the manually way
        int min = list.get(0);
        int max = list.get(0);
        for(int num : list){
            if(num < min){
                min = num;
            }
            if(num > max){
                max = num;
            }
        }
        System.out.println("Minimum element in the Linked List : " + min);
        System.out.println("Maximum element in the Linked List : " + max);
    }
}
