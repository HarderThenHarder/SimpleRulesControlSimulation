public class MissileShip {
    Vector2 position;
    boolean fire_flag = false;

    public MissileShip(Vector2 position){
        this.position = position;
    }

    void ready_to_fire(GuideShip guideShip){
        if (guideShip.guide_flag){
            MyTools.easy_sleep(5000);
            fire_flag = true;
        }
    }

}
