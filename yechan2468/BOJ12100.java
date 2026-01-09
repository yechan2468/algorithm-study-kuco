import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ12100 {
    private static int n;
    private static int[][] board;
    private static int answer;

    public static void main(String[] args) throws IOException {
        initialize();

        for (int i = 0; i < 4; i++) {
            dfs(i, 0, deepCopy(board));
        }

        System.out.println(answer);
    }

    private static void dfs(int direction, int count, int[][] board) {

        if (count >= 5) return;

        switch (direction) {
            case 0: tiltUp(board); break;
            case 1: tiltLeft(board); break;
            case 2: tiltRight(board); break;
            case 3: tiltDown(board);
        }

        answer = Math.max(answer, findMax(board));

        for (int i = 0; i < 4; i++) {
            dfs(i, count + 1, deepCopy(board));
        }
    }

    private static void tiltUp(int[][] board) {
        for (int i = 0; i < n; i++) {
            processColumn(i, 0, n, 1, board);
        }
    }

    private static void tiltDown(int[][] board) {
        for (int i = 0; i < n; i++) {
            processColumn(i, n - 1, -1, -1, board);
        }
    }

    private static void tiltLeft(int[][] board) {
        for (int i = 0; i < n; i++) {
            processRow(i, 0, n, 1, board);
        }
    }

    private static void tiltRight(int[][] board) {
        for (int i = 0; i < n; i++) {
            processRow(i, n - 1, -1, -1, board);
        }
    }

    private static void processRow(int index, int start, int end, int step, int[][] board) {
        List<Integer> targets = new ArrayList<>();
        for (int i = start; (step > 0 ? i < end : i > end); i += step) {
            if (board[index][i] != 0) {
                targets.add(board[index][i]);
            }
        }

        List<Integer> processed = process(targets);

        for (int i = 0; i < n; i++) board[index][i] = 0;
        int i = start;
        for (int val : processed) {
            board[index][i] = val;
            i += step;
        }
    }

    private static void processColumn(int index, int start, int end, int step, int[][] board) {
        List<Integer> targets = new ArrayList<>();
        for (int i = start; (step > 0 ? i < end : i > end); i += step) {
            if (board[i][index] != 0) {
                targets.add(board[i][index]);
            }
        }

        List<Integer> processed = process(targets);

        for (int i = 0; i < n; i++) board[i][index] = 0;
        int i = start;
        for (int val : processed) {
            board[i][index] = val;
            i += step;
        }
    }

    private static List<Integer> process(List<Integer> line) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < line.size(); i++) {
            if (i == line.size() - 1) {
                result.add(line.get(i));
                break;
            }

            int curr = line.get(i), next = line.get(i + 1);
            if (curr == next) {
                result.add(curr * 2);
                i++;
            } else {
                result.add(curr);
            }
        }

        return result;
    }

    private static int findMax(int[][] board) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result = Math.max(result, board[i][j]);
            }
        }
        return result;
    }

    private static int[][] deepCopy(int[][] board) {
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            result[i] = board[i].clone();
        }
        return result;
    }

    private static void initialize() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
        answer = findMax(board);
    }
}
