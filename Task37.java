import java.util.Scanner;
public class Task37 {
    public static void main(String[] args) {
        Scanner myData = new Scanner(System.in);

        System.out.println("Введите последовательность натуральных чисел через пробел >");
        String subsequence = myData.nextLine().trim();

        String[] numbers = subsequence.split("\\s+");

        for (String num : numbers) {
            if (num.matches("\\d+")) {
                String[] number = num.split("");
                int sum = 0;
                int product = 1;
                for (String digitChar : number) {
                    int digit = Integer.parseInt(digitChar);
                    sum += digit;
                    product *= digit;
                }
                System.out.printf("Сумма цифр числа %s: %d%nПроизведение цифр числа %s: %d%n", num, sum, num, product);
            }
        }

        myData.close();  
    }
}
