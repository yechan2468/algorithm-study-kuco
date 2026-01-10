import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ28707 {
    private static int[] numbers;
    private static Move[] moves;
    private static Map<Integer, Integer> distances;
    private static int targetHash;

    public static void main(String[] args) throws IOException {
        initialize();

        boolean result = dijkstra();

        if (!result) System.out.println(-1);
    }

    private static boolean dijkstra() {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        pq.add(new State(numbers, 0));
        distances.put(Arrays.hashCode(numbers), 0);
        
        while (!pq.isEmpty()) {
            State curr = pq.poll();
            int hash = Arrays.hashCode(curr.numbers);
            if (curr.cost > distances.getOrDefault(hash, Integer.MAX_VALUE)) {
                continue;
            }
            if (hash == targetHash) {
                System.out.println(curr.cost);
                return true;
            }

            for (Move move : moves) {
                int[] next = curr.numbers.clone();

                swap(next, move);
                int nextCost = curr.cost + move.cost;
                int nextHash = Arrays.hashCode(next);

                if (nextCost < distances.getOrDefault(nextHash, Integer.MAX_VALUE)) {
                    distances.put(nextHash, nextCost);
                    pq.add(new State(next, nextCost));
                }
            }
        }

        return false;
    }

    private static void swap(int[] newNumbers, Move move) {
        int tmp = newNumbers[move.to];
        newNumbers[move.to] = newNumbers[move.from];
        newNumbers[move.from] = tmp;
    }

    private static class Move {
        int from, to, cost;

        public Move(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    private static class State {
        int[] numbers;
        int cost;

        public State(int[] numbers, int cost) {
            this.numbers = numbers;
            this.cost = cost;
        }
    }

    private static void initialize() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        numbers = new int[n];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(tokenizer.nextToken()) - 1;
        }
        int numMoves = Integer.parseInt(reader.readLine());
        moves = new Move[numMoves];
        for (int i = 0; i < numMoves; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken()) - 1;
            int to = Integer.parseInt(tokenizer.nextToken()) - 1;
            int cost = Integer.parseInt(tokenizer.nextToken());
            moves[i] = new Move(from, to, cost);
        }
        Arrays.sort(moves, Comparator.comparingInt(c -> c.cost));
        distances = new HashMap<>();
        int[] targetNumbers = numbers.clone();
        Arrays.sort(targetNumbers);
        targetHash = Arrays.hashCode(targetNumbers);
    }
}
