public class Main {
    public static void main(String[] args) {
        FirstRenegotiation a = null;
        try {
            a = new FirstRenegotiation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        a.go();
    }
}

