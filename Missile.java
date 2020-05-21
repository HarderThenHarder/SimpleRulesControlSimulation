public class Missile {
    Vector2 position;
    double step = 1;
    boolean start_flag = false;
    boolean destroy_flag = false;

    public Missile(Vector2 position){
        this.position = position;
    }

    void launch(Vector2 target){
        double radians = Math.atan2(target.y - position.y, target.x - position.x);
        MyTools.easy_sleep(1000);

        while (MyTools.get_distance(target, position) > 8){
            position.x += step * Math.cos(radians);
            position.y += step * Math.sin(radians);
            MyTools.easy_sleep(300);
        }
        destroy_flag = true;
    }
}
