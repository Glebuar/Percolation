import java.util.Arrays;

public class Union {

    private int[] node;

    Union(int n){
        node = new int[n];
        for(int i=0; i<n; i++){
            node[i] = i;
        }
    }

    private int root(int a) {
        while (node[a] != a) {
            a = node[a];
        }
        return a;
    }

    boolean isConnected(int a, int b){

        return root(a) == root(b);
    }

    void connect(int a, int b){

        node[root(b)] = root(a);
    }

    void print(){
        System.out.println(Arrays.toString(node));
    }

    public static void main (String[] args) {
       // Union test = new Union(10);
       // test.connect(5,7);
       // test.connect(5,6);
       // test.print();
       // System.out.println(test.isConnected(6,7));
    }
}
