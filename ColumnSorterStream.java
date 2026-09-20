import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ColumnSorterStream {

    public static int columnCharacteristic(int[][] matrix, int column) {
        return Arrays.stream(matrix).mapToInt(row -> Math.abs(row[column])).sum();
    }

    public static void printMatrix(int[][] matrix) {
        Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .mapToObj(value -> String.format("%4d", value))
                        .collect(Collectors.joining()))
                .forEach(System.out::println);
    }

    public static void printCharacteristics(int[][] matrix) {
        System.out.println(IntStream.range(0, matrix.length)
                .mapToObj(column -> String.format("%4d", columnCharacteristic(matrix, column)))
                .collect(Collectors.joining()));
    }

    public static int[][] sortColumns(int[][] matrix) {
        int[] order = IntStream.range(0, matrix.length)
                .boxed()
                .sorted(Comparator.comparingInt((Integer column) -> columnCharacteristic(matrix, column)).reversed())
                .mapToInt(Integer::intValue)
                .toArray();

        return Arrays.stream(matrix)
                .map(row -> IntStream.of(order).map(column -> row[column]).toArray())
                .toArray(int[][]::new);
    }

    public static void main(String[] args) {
        Scanner myData = new Scanner(System.in);

        int n;

        while (true) {
            System.out.println("Введите размерность матрицы:");
            if (!myData.hasNextInt()) {
                System.out.println("Надо ввести натуральное число!");
                myData.nextLine();
                continue;
            }
            n = myData.nextInt();
            myData.nextLine();
            if (n > 0) {
                break;
            } else {
                System.out.println("Число должно быть больше 0!");
            }
        }

        int[][] matrixArray = new int[n][n];

        String answer;

        while (true) {
            System.out.println("Желаете ли вы ввести элементы матрицы сами?(y/n)");
            answer = myData.nextLine().trim();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")) {
                break;
            }
            System.out.println("Введите 'y' или 'n'!");
        }

        if (answer.equalsIgnoreCase("y")) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int a;
                    while (true) {
                        System.out.println("Введите целое число от -9 до 9:");
                        if (myData.hasNextInt()) {
                            a = myData.nextInt();
                            if (a >= -9 && a <= 9) {
                                break;
                            } else {
                                System.out.println("Введённое вами число не соответсвует диапазону!");
                            }
                        }
                        myData.nextLine();
                    }
                    matrixArray[i][j] = a;
                }
            }
        } else {
            Random numGenerator = new Random();
            final int k = n;
            matrixArray = Stream.generate(() -> numGenerator.ints(k, -9, 10).toArray()).limit(n).toArray(int[][]::new);
        }

        myData.close();

        System.out.println("Исходная матрица:");
        printMatrix(matrixArray);
        System.out.println("Характеристики её столбцов:");
        printCharacteristics(matrixArray);

        int[][] sortedMatrix = sortColumns(matrixArray);

        System.out.println("Матрица после перестановки столбцов:");
        printMatrix(sortedMatrix);
        System.out.println("Характеристики её столбцов:");
        printCharacteristics(sortedMatrix);
    }
}
