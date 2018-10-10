public class Union {

    int [] node = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public int root(int a) {

        while (node[a] != a) {
            a = node[a];
        }
        return a;
    }
    public boolean isConnected (int a, int b){
        if (root(a)==root(b)){
            return true;
            }
        else {
            return false;
            }
        }

    public void connect (int a, int b){
        node[a]= root(b);
    }
}
