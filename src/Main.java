public class Main {
    public static void main(String[] args) {
        RenegotiationScreen a = null;
        try {
            a = new RenegotiationScreen();
            a.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

