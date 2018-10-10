class Union {

    private int [] node = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

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
}
