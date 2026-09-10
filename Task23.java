import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Task23 {
    public static void main(String[] args) {
        Scanner myData = new Scanner(System.in);

        System.out.println("Введите последовательность натуральных чисел через пробел >");
        String subsequence = myData.nextLine().trim();

        String[] numbers = subsequence.split("\\s+");

        List<Integer> numberList = new ArrayList<>();

        for (String num : numbers) {
            if (num.matches("[1-9]\\d{4}")) {
                int number = Integer.parseInt(num);
                int firstDigit = number / 10000;
                int middleDigit = (number / 100) % 10;
                int lastDigit = number % 10;
                if (middleDigit == firstDigit + lastDigit) numberList.add(number);
            }
        }

        if (numberList.isEmpty()) {
            System.out.println("Ничего не найдено :(");
        } else {
            System.out.println("Подходящие числа:");
            for (int number : numberList) {
                System.out.println(number);
            }
            System.out.println("Их количество: " + numberList.size());
        }

        myData.close();  
    }
}
