public class ClosestPair {
    public static void main(String[] args) {
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};

        int[] result = closestPair(x_coords, y_coords);
        System.out.println("Closest pair of points indices: [" + result[0] + ", " + result[1] + "]");
    }

    public static int[] closestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int[] result = new int[2];
        int min_distance = Integer.MAX_VALUE; // Initialize to a large value

        // Check all pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate Manhattan distance between point (i) and (j)
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // If found a smaller distance, or lexicographically smaller pair
                if (distance < min_distance || (distance == min_distance && i < result[0] || (i == result[0] && j < result[1]))) {
                    min_distance = distance;
                    result[0] = i; // store the index i
                    result[1] = j; // store the index j
                }
            }
        }
        return result;
    }
}


