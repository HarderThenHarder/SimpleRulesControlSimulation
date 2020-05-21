

public class MyTools {

    public static double get_distance(Vector2 A, Vector2 B){
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }

    public static void easy_sleep(double time) {
        try {
            Thread.sleep((long) (time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
