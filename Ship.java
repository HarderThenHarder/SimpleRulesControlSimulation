

public class Ship {
    double R;
    double omega;
    double fi;
    static double obs_R = 30;
    boolean clockwise;
    Vector2 real_position = new Vector2(0,0);
    Vector2 origin_vector_position;
    Vector2 o_of_the_circle_path;

    public Ship(double R, double omega, double fi, Vector2 origin_vector_position, Vector2 o_of_the_circle_path, boolean clockwise){
        this.R = R;
        this.omega = omega;
        this.origin_vector_position = origin_vector_position;
        this.o_of_the_circle_path = o_of_the_circle_path;
        this.clockwise = clockwise;
        this.fi = fi;
    }

    public static void easy_sleep(double time) {
        try {
            Thread.sleep((long) (time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start_patrol(GuideShip guideShip) {
        while(true) {
            double theta = fi;
            double rand;
            if(this.clockwise)
                while(true) {
                    rand = Math.random() * omega;
                    real_position.x = o_of_the_circle_path.x + Math.cos(Math.toRadians(theta)) * R;
                    real_position.y = o_of_the_circle_path.y + Math.sin(Math.toRadians(theta)) * R;
                    theta += (omega + rand);

                    if (MyTools.get_distance(guideShip.current_pos, real_position) <= obs_R) {
                        guideShip.be_detected = true;
                        break;
                    }
                    easy_sleep(100);
                }
            else
                while(true) {
                    rand = Math.random() * omega;
                    real_position.x = o_of_the_circle_path.x + Math.cos(Math.toRadians(theta)) * R;
                    real_position.y = o_of_the_circle_path.y + Math.sin(Math.toRadians(theta)) * R;
                    theta -= (omega + rand);

                    if (MyTools.get_distance(guideShip.current_pos, real_position) <= obs_R) {
                        guideShip.be_detected = true;
                        break;
                    }
                    easy_sleep(100);
                }
        }
    }

    public static void main(String args[]) {
//        Ship ship = new Ship(10, 45, new Vector2(1, 1), new Vector2(0,0));
//
//        Thread thread = new Thread(()-> ship.start_patrol());
//        thread.start();
//
//        Thread thread2 = new Thread(()->{
//            Vector2 tmp = new Vector2(0,0);
//            while(true) {
//                if (ship.real_position.x != tmp.x && ship.real_position.y != tmp.y) {
//                    System.out.println("x2:" + ship.real_position.x);
//                    System.out.println("y2:" + ship.real_position.y);
//                    System.out.println("------------------------------------");
//                }
//                tmp = ship.real_position;
//                ship.easy_sleep(100);
//            }
//        });
//        thread2.start();
    }
}
