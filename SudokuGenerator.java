package Sudoku;
/* Java program for Sudoku generator  */
import java.lang.*;

/**
 * @author Susobhan Akhuli, Alejandro Crespo Perez (added show matrix and methods and parse method)
 */
public class SudokuGenerator
{
    private int[][] mat;
    private boolean[][] show;
    private int N; // number of columns/rows.
    private int SRN; // square root of N
    private int K; // No. Of missing digits

    // Constructor
    SudokuGenerator(int N, int K)
    {
        this.N = N;
        this.K = K;

        // Compute square root of N
        double SRNd = Math.sqrt(N);
        SRN = (int) SRNd;

        mat = new int[N][N];
        show = new boolean[N][N];

        fillValues();
    }

    // Sudoku Generator
    public void fillValues()
    {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);

        // Remove Randomly K digits to make game
        removeKDigits();
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void fillDiagonal()
    {

        for (int i = 0; i<N; i=i+SRN)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
                show[row+i][col+j] = true;
            }
        }
    }

    // Random generator
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j)
    {
        //  System.out.println(i+" "+j);
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                show[i][j] = true;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    // complete game
    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;

            // System.out.println(cellId);
            // extract coordinates i  and j
            int i = (cellId/N);
            int j = cellId%N;

            // System.out.println(i+" "+j);
            if (show[i][j])
            {
                count--;
                show[i][j] = false;
                // mat[i][j] = 0;
            }
        }
    }

    // Print sudoku
    public void printSudoku()
    {
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
    public void printSudokuShow(){
        int contador = 0;
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++) {
                System.out.print((show[i][j] ? "T" : "F") + " ");
                if (!show[i][j]) contador++;
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(contador);
    }

    public String parse(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.valueOf(mat[i][j])).append(show[i][j] ? "T" : "F");
            }
        }
        return s.toString();
    }

    // Driver code
    public static void main(String[] args)
    {
        int N = 9, K = 20;
        SudokuGenerator sudoku = new SudokuGenerator(N, K);
        sudoku.printSudoku();
        sudoku.printSudokuShow();
        System.out.println(sudoku.parse());
    }
}
// This code is modified by Susobhan Akhuli and Alejandro Crespo
