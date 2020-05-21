public class Vector2 {
    double x;
    double y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 otherVector){
        double x = this.x + otherVector.x;
        double y = this.y + otherVector.y;
        return new Vector2(x, y);
    }

    public void rotate(double theta){
        double theta_radians = Math.toRadians(theta);
        this.x = Math.cos(theta_radians) * this.x - Math.sin(theta_radians) * this.y;
        this.y = Math.sin(theta_radians) * this.x + Math.cos(theta_radians) * this.y;
    }
}
