import java.util.Arrays;

public class Union {

    private final int[] node;

    // O(n)
    public Union(int n) {
        node = new int[n];
        for (int i = 0; i < n; i++) {
            node[i] = i;
        }
    }

    // O(n)
    private int root(int a) {
        while (node[a] != a) {
            a = node[a];
        }
        return a;
    }

    //O(n)
    public boolean isConnected(int a, int b) {

        return root(a) == root(b);
    }

    //O(n)
    public void connect(int a, int b) {

        node[root(b)] = root(a);
    }

    //O(n)
    public void print() {

        System.out.println(Arrays.toString(node));
    }
}
