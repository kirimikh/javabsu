import java.util.Random;
import java.util.Scanner;

public class ColumnSorter {

    public static int columnCharacteristic(int[][] matrix, int column) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += Math.abs(matrix[i][column]);
        }
        return sum;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void printCharacteristics(int[][] matrix) {
        for (int j = 0; j < matrix.length; j++) {
            System.out.printf("%4d", columnCharacteristic(matrix, j));
        }
        System.out.println();
    }

    public static int[][] sortColumns(int[][] matrix) {
        int n = matrix.length;

        int[] order = new int[n];
        for (int j = 0; j < n; j++) {
            order[j] = j;
        }

        for (int i = 1; i < n; i++) {
            int current = order[i];
            int currentValue = columnCharacteristic(matrix, current);
            int j = i - 1;
            while (j >= 0 && columnCharacteristic(matrix, order[j]) < currentValue) {
                order[j + 1] = order[j];
                j--;
            }
            order[j + 1] = current;
        }

        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = matrix[i][order[j]];
            }
        }
        return result;
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
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrixArray[i][j] = numGenerator.nextInt(-9, 10);
                }
            }
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
