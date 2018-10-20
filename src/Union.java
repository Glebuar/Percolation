import java.util.Arrays;

public class Union {

    private final int[] node;

    public Union(int n) {
        node = new int[n];
        for (int i = 0; i < n; i++) {
            node[i] = i;
        }
    }

    private int root(int a) {
        while (node[a] != a) {
            a = node[a];
        }
        return a;
    }

    public boolean isConnected(int a, int b) {

        return root(a) == root(b);
    }

    public void connect(int a, int b) {

        node[root(b)] = root(a);
    }

    public void print() {

        System.out.println(Arrays.toString(node));
    }
}
