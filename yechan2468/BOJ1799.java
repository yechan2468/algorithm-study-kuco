import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1799 {
    static int n, numCells;
    static List<Cell> blackCells, whiteCells;
    static boolean[] occupied1, occupied2;
    static int blackMax, whiteMax;

    public static void main(String[] args) throws IOException {
        initialize();

        dfs(blackCells, 0, 0, true);
        dfs(whiteCells, 0, 0, false);

        System.out.println(blackMax + whiteMax);
    }

    private static void dfs(List<Cell> cells, int index, int count, boolean isBlack) {
        if (!isPromising(cells, index, count, isBlack)) return;
        if (index == cells.size()) {
            if (isBlack) {
                blackMax = Math.max(blackMax, count);
            } else {
                whiteMax = Math.max(whiteMax, count);
            }
            return;
        }

        Cell cell = cells.get(index);
        int row = cell.row;
        int col = cell.col;

        if (isValid(row, col)) {
            occupy(row, col);
            dfs(cells, index + 1, count + 1, isBlack);
            deoccupy(row, col);
        }

        dfs(cells, index + 1, count, isBlack);
    }

    private static void occupy(int row, int column) {
        occupied1[row + column] = true;
        occupied2[row - column + n] = true;
    }

    private static void deoccupy(int row, int column) {
        occupied1[row + column] = false;
        occupied2[row - column + n] = false;
    }

    private static boolean isValid(int row, int column) {
        return !occupied1[row + column] && !occupied2[row - column + n];
    }

    private static boolean isPromising(List<Cell> cells, int index, int count, boolean isBlack) {
        return count + (cells.size() - index) > (isBlack ? blackMax : whiteMax);
    }

    private static void initialize() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        blackCells = new ArrayList<>();
        whiteCells = new ArrayList<>();
        numCells = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < n; j++) {
                String s = tokenizer.nextToken();
                if (s.equals("1")) {
                    if ((i + j) % 2 == 0) {
                        whiteCells.add(new Cell(i, j));
                    } else {
                        blackCells.add(new Cell(i, j));
                    }
                    numCells++;
                }
            }
        }
        occupied1 = new boolean[20];
        occupied2 = new boolean[20];
        blackMax = 0;
        whiteMax = 0;
    }

    private static class Cell {
        int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
