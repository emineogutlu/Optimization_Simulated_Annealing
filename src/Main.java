import java.util.Random;
import java.util.Arrays;

public class Main {

    private static final int N = 14;
    public static final double[][] distanceMatrix = {
            {16.47, 96.10}, {16.47, 94.44}, {20.09, 92.54}, {22.39, 93.37}, {25.23, 97.24},
            {22.00, 96.05}, {20.47, 97.02}, {17.20, 96.29}, {16.30, 97.38}, {14.05, 98.12},
            {16.53, 97.38}, {21.52, 95.59}, {19.41, 97.13}, {20.09, 94.55}
    };

    // Generate random starting solution
    public static int[] Evaluate() {
        int[] path = new int[N];
        for (int i = 0; i < N; i++) path[i] = i;
        shuffleArray(path);
        return path;
    }

    // Swaps two elements in the solution
    public static int[] movement(int[] currentPath) {
        int[] newPath = currentPath.clone();
        Random rnd = new Random();
        int i = rnd.nextInt(N);
        int j = rnd.nextInt(N);
        while (i == j) j = rnd.nextInt(N);
        int temp = newPath[i];
        newPath[i] = newPath[j];
        newPath[j] = temp;
        return newPath;
    }

    // Euclidean distance between two points
    public static double distance(int city1, int city2) {
        double dx = distanceMatrix[city1][0] - distanceMatrix[city2][0];
        double dy = distanceMatrix[city1][1] - distanceMatrix[city2][1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Returns the total path length of the input solution
    public static double fitness(int[] path) {
        double total = 0;
        for (int i = 0; i < path.length - 1; i++) {
            total += distance(path[i], path[i + 1]);
        }
        total += distance(path[N - 1], path[0]); // Dönüş yolunu ekle
        return total;
    }


    // Randomly shuffle the array
    public static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] currentSolution = Evaluate();
        double currentFitness = fitness(currentSolution);

        double temperature = 1000;
        double cooling = 0.995;
        int iteration = 10000;
        Random rand = new Random();

        for (int i = 0; i < iteration; i++) {
            int[] newSolution = movement(currentSolution);
            double newFitness = fitness(newSolution);

            if (newFitness < currentFitness || rand.nextDouble() < Math.exp((currentFitness - newFitness) / temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            temperature *= cooling;

            if (i % 100 == 0) {
                System.out.println("Iteration: " + i + ", fitness: " + currentFitness);
            }
        }

        System.out.println("\nBest route found:");
        System.out.println(Arrays.toString(currentSolution));
        System.out.println("Best fitness: " + currentFitness);
    }
}
