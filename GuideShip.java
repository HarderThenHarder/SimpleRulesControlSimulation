import java.util.ArrayList;

public class GuideShip {
    Vector2 origin_pos;
    Vector2 target_pos;
    Vector2 current_pos;
    double step;
    double guide_area = 50;
    double obs_R = 80;
    boolean be_detected = false;
    boolean guide_flag = false;

    public GuideShip(Vector2 origin_pos, Vector2 target_pos, double step){
        this.origin_pos = origin_pos;
        this.target_pos = target_pos;
        this.current_pos = origin_pos;
        this.step = step;
    }

    public void start_stupid_method(){
        double radians = Math.atan2(target_pos.y - origin_pos.y, target_pos.x - origin_pos.x);

        while (MyTools.get_distance(target_pos, current_pos) > guide_area){
            current_pos.x += step * Math.cos(radians);
            current_pos.y += step * Math.sin(radians);
            if (be_detected)
                break;
            MyTools.easy_sleep(100);
        }

        if (MyTools.get_distance(target_pos, current_pos) <= guide_area)
            guide_flag = true;
    }

    public void start_clever_method(ArrayList <Ship> ship_list){
        double radians = Math.atan2(target_pos.y - origin_pos.y, target_pos.x - origin_pos.x);
        boolean go_flag;

        while (MyTools.get_distance(target_pos, current_pos) > guide_area){
            go_flag = true;

            for (Ship ship: ship_list)
                if (MyTools.get_distance(current_pos, ship.real_position) <= this.obs_R)
                    go_flag = false;

            if (go_flag) {
                current_pos.x += step * Math.cos(radians);
                current_pos.y += step * Math.sin(radians);
                }

            if (be_detected)
                break;
            MyTools.easy_sleep(100);
        }

        if (MyTools.get_distance(target_pos, current_pos) <= guide_area)
            guide_flag = true;
    }
}
