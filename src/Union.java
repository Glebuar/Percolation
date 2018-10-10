public class Union {

    private int[] node;

    Union(int n){
        for(int i=0; i<n; i++){
            node = new int[i];
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

        node[a]= root(b);
    }

    public static void main (String[] args) {
        Union test = new Union(10);
        test.connect(5,7);
        test.connect(5,6);
        System.out.println(test.isConnected(6,7));
    }
}
