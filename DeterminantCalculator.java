import java.util.Random;
import java.util.Scanner;
public class DeterminantCalculator {

    public static int[][] minor(int[][] matrix, int i) {
        int[][] newMinor = new int[matrix.length - 1][matrix.length - 1];
        int row = 0;
        for (int k = 0; k < matrix.length; k++) {
            if (k == 0) {
                continue;
            }
            int col = 0;
            for (int c = 0; c < matrix.length; c++) {
                if (c == i) {
                    continue;
                }
                newMinor[row][col] = matrix[k][c];
                col++;     
            }
            row++;
        } 
        return newMinor;
    }

    public static long determinant(int[][] matrix) {
        long det = 0;
        if (matrix.length == 1) {
            det = matrix[0][0];
            return det;
        }
        if (matrix.length == 2) {
            det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            return det;
        }
        int j = 1;
        for (int i = 0; i < matrix.length; i++) {
            det += j * matrix[0][i] * determinant(minor(matrix, i));
            j = j * -1;
        }
        return det;
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
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")){
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
                            if (a >= -9 && a <= 9){
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

        System.out.println("Ваша матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n; j++) {
                System.out.printf("%3d", matrixArray[i][j]);
            }
            System.out.println();
        }

        System.out.println("Определитель: " + determinant(matrixArray));

    }
}
