import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainUI extends Application {

    private double scale = 2;
    private final double width = 900 * scale;
    private final double height = 450 * scale;
    Vector2 guide_ship_start_pos = new Vector2(1500, 800);
    private ArrayList <Ship> ship_list = new ArrayList<>();
    private Vector2 o_of_the_circle_path = new Vector2(800,250);
    private GuideShip guideShip = new GuideShip(guide_ship_start_pos, o_of_the_circle_path, 3);
    private MissileShip missileShip = new MissileShip(new Vector2(1500, 600));
    private Missile missile = new Missile(missileShip.position);
    private Image img_guideShip = new Image("imgs/tank.png");
    private Image img_missile = new Image("imgs/missile.png");

    private void create_ship_c(ArrayList<Ship> ship_list, double R, double omega, Vector2 origin_vector_position, Vector2 o_of_the_circle_path){
        Ship new_ship = new Ship(R, omega, 0, origin_vector_position, o_of_the_circle_path, true);
        ship_list.add(new_ship);
        Thread ship_patrol = new Thread(() -> new_ship.start_patrol(guideShip));
        ship_patrol.start();
    }

    private void create_ship_ac(ArrayList<Ship> ship_list, double R, double omega, Vector2 origin_vector_position, Vector2 o_of_the_circle_path){
        Ship new_ship = new Ship(R, omega, 0, origin_vector_position, o_of_the_circle_path, false);
        ship_list.add(new_ship);
        Thread ship_patrol = new Thread(() -> new_ship.start_patrol(guideShip));
        ship_patrol.start();
    }

    private void auto_create_ship(ArrayList<Ship> ship_list, Vector2 origin_vector_position, Vector2 o_of_the_circle_path){
        Ship new_ship = new Ship(190, 1, 120, origin_vector_position, o_of_the_circle_path, false);
        ship_list.add(new_ship);
        Thread ship_patrol = new Thread(() -> new_ship.start_patrol(guideShip));
        ship_patrol.start();

        Ship new_ship1 = new Ship(150, 1, 240, origin_vector_position, o_of_the_circle_path, false);
        ship_list.add(new_ship1);
        Thread ship_patrol1 = new Thread(() -> new_ship1.start_patrol(guideShip));
        ship_patrol1.start();

        Ship new_ship2 = new Ship(220, 1, 360, origin_vector_position, o_of_the_circle_path, false);
        ship_list.add(new_ship2);
        Thread ship_patrol2 = new Thread(() -> new_ship2.start_patrol(guideShip));
        ship_patrol2.start();

    }

    private void reset(ArrayList<Ship> ship_list){
                ship_list.clear();
    }

    private void start_stupid_guide_ship(GuideShip guideShip, MissileShip missileShip){
        Thread stupid_thread = new Thread(() -> {
            guideShip.start_stupid_method();
            missileShip.ready_to_fire(guideShip);
        });
        stupid_thread.start();
    }

    private void start_clever_guide_ship(GuideShip guideShip, MissileShip missileShip, ArrayList<Ship> ship_list){
        Thread stupid_thread = new Thread(() -> {
            guideShip.start_clever_method(ship_list);
            missileShip.ready_to_fire(guideShip);
        });
        stupid_thread.start();
    }

    private void start_missile(Missile missile, Vector2 target){
        Thread missile_thread = new Thread(() -> {
            missile.launch(target);
        });
        missile_thread.start();
    }

    public void draw_grid(double width, double height, Group root) {
        ArrayList<Double> x_aixs = new ArrayList<>();
        ArrayList<Double> y_aixs = new ArrayList<>();

        for (double i = 0; i <= width; i += width / 20)
            x_aixs.add(i);
        for (double i = 0; i <= height; i += height / 20)
            y_aixs.add(i);

        for (Double x_aix : x_aixs)
            Pencil.draw_dotted_line(root, new Vector2(x_aix, 0), new Vector2(x_aix, height), 1, Color.rgb(100, 100, 100, 0.8));
        for (Double y_aix : y_aixs)
            Pencil.draw_dotted_line(root, new Vector2(0, y_aix), new Vector2(width, y_aix), 1, Color.rgb(100, 100, 100, 0.8));
    }

    private ArrayList<Vector2> create_line(Vector2 A, Vector2 B){
        ArrayList <Vector2> line_list = new ArrayList<>();
        line_list.add(A);
        line_list.add(B);
        return line_list;
    }

    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("SUBMARINE_SIM");

        // Set Background Pic
        Image image = new Image(getClass().getResourceAsStream("imgs/bg3.png"));
        ImageView im = new ImageView(image);
        im.setFitHeight(height);
        im.setFitWidth(width);
        Label bg = new Label("", im);

        // Set main_ship Pic
        Image img_main_ship = new Image(getClass().getResourceAsStream("imgs/enemy.png"));
        ImageView imv_main_ship = new ImageView(img_main_ship);
        imv_main_ship.setFitHeight(150);
        imv_main_ship.setFitWidth(100);
        imv_main_ship.setTranslateX(o_of_the_circle_path.x - 50);
        imv_main_ship.setTranslateY(o_of_the_circle_path.y - 75);
        Label lb_main_ship = new Label("", imv_main_ship);

        // Set missile_ship Pic
        Image img_missile_ship = new Image(getClass().getResourceAsStream("imgs/missile_car.png"));
        ImageView imv_missile_ship = new ImageView(img_missile_ship);
        imv_missile_ship.setFitHeight(55);
        imv_missile_ship.setFitWidth(100);
        imv_missile_ship.setTranslateX(missileShip.position.x - 50);
        imv_missile_ship.setTranslateY(missileShip.position.y - 27.5);
        Label lb_missile_ship = new Label("", imv_missile_ship);


        // Custom Ship Parameters
        Label label_prompt = new Label("- To Add The Guard Ship -");
        label_prompt.setFont(Font.font("SimHei", 18));
        label_prompt.setTextFill(Color.rgb(200,200,200));

        Label label_R = new Label(" R : ");
        label_R.setFont(Font.font("Fira Code", 16));
        label_R.setTextFill(Color.WHITE);

        Label label_omega = new Label(" ω : ");
        label_omega.setFont(Font.font("Fira Code", 16));
        label_omega.setTextFill(Color.WHITE);

        TextField textField_R = new TextField("200");
        TextField textField_omega = new TextField("1");

        VBox vb = new VBox();
        vb.setTranslateY(10);
        vb.setTranslateX(10);

        HBox row1 = new HBox();
        row1.getChildren().addAll(label_R, textField_R);
        row1.setSpacing(10);

        HBox row2 = new HBox();
        row2.getChildren().addAll(label_omega, textField_omega);
        row2.setSpacing(10);

        vb.getChildren().addAll(label_prompt, row1, row2);
        vb.setSpacing(10);

        // Add Ship Button
        Button btn_c_add_ship = new Button("ADD(S)");
        btn_c_add_ship.setStyle("-fx-font: 13 italic; -fx-base: #ee2211;");
        btn_c_add_ship.setTranslateX(280);
        btn_c_add_ship.setTranslateY(50);
        btn_c_add_ship.setOnAction(event -> create_ship_c(ship_list, Double.parseDouble(textField_R.getText()), Double.parseDouble(textField_omega.getText()), new Vector2(0, 100), o_of_the_circle_path));

        // Add Ship Button
        Button btn_ac_add_ship = new Button("ADD(N)");
        btn_ac_add_ship.setStyle("-fx-font: 13 italic; -fx-base: #ee2211;");
        btn_ac_add_ship.setTranslateX(280);
        btn_ac_add_ship.setTranslateY(80);
        btn_ac_add_ship.setOnAction(event -> create_ship_ac(ship_list, Double.parseDouble(textField_R.getText()), Double.parseDouble(textField_omega.getText()), new Vector2(0, 100), o_of_the_circle_path));

        // AUTO add Ship Button
        Button btn_auto_add = new Button("                        Auto Add                         ");
        btn_auto_add.setTranslateX(25);
        btn_auto_add.setTranslateY(130);
        btn_auto_add.setStyle("-fx-font: 16 italic; -fx-base: #ee2211;");
        btn_auto_add.setOnAction(event -> auto_create_ship(ship_list, new Vector2(0, 100), o_of_the_circle_path));

        // AUTO Reset Ship Button
        Button btn_reset = new Button("                        Reset                         ");
        btn_reset.setTranslateX(0.83 * width - 5);
        btn_reset.setTranslateY(190);
        btn_reset.setStyle("-fx-font: 16 italic; -fx-base: #00df15;");
        btn_reset.setOnAction(event -> reset(ship_list));

        // Start guideShip Button
        Button btn_start_guideShip = new Button("                   Start (Low Level)                   ");
        btn_start_guideShip.setTranslateX(25);
        btn_start_guideShip.setTranslateY(170);
        btn_start_guideShip.setStyle("-fx-font: 16 italic; -fx-base: #ee2211;");
        btn_start_guideShip.setOnAction(event -> start_stupid_guide_ship(guideShip, missileShip));

        // Start guideShip(HighLevel) Button
        Button btn_start_high_guideShip = new Button("                   Start (High Level)                  ");
        btn_start_high_guideShip.setTranslateX(25);
        btn_start_high_guideShip.setTranslateY(210);
        btn_start_high_guideShip.setStyle("-fx-font: 16 italic; -fx-base: #ee2211;");
        btn_start_high_guideShip.setOnAction(event -> start_clever_guide_ship(guideShip, missileShip, ship_list));

        // State VBox
        VBox vbox_state = new VBox();
        vbox_state.setTranslateX(20);
        vbox_state.setTranslateY(280);
        vbox_state.setSpacing(10);

        Label label_stateTitle = new Label("- STATE TABLE -");
        label_stateTitle.setFont(Font.font("SimHei", 18));
        label_stateTitle.setTextFill(Color.rgb(200,200,200));
        vbox_state.getChildren().add(label_stateTitle);

        Label label_guardShip_num = new Label(" GuardUnit(s) :   " + ship_list.size());
        label_guardShip_num.setFont(Font.font("Fira Code", 16));
        label_guardShip_num.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_guardShip_num);

        Label label_guideShip_state = new Label(" GuideCar's State :   " + "Breaking...");
        label_guideShip_state.setFont(Font.font("Fira Code", 16));
        label_guideShip_state.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_guideShip_state);

        Label label_missile_state = new Label(" MissileCar's State :   " + "Ready...");
        label_missile_state.setFont(Font.font("Fira Code", 16));
        label_missile_state.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_missile_state);

        Label label_guide_ship_position = new Label(" GuideCar's position :   ( " + guideShip.current_pos.x +" , " + guideShip.current_pos.y + " )");
        label_guide_ship_position.setFont(Font.font("Fira Code", 16));
        label_guide_ship_position.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_guide_ship_position);

        Label label_main_ship_position = new Label(" Enemy's position :   ( " + o_of_the_circle_path.x +" , " + o_of_the_circle_path.y + " )");
        label_main_ship_position.setFont(Font.font("Fira Code", 16));
        label_main_ship_position.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_main_ship_position);

        Label label_missile_ship_position = new Label(" MissileCar's position :   ( " + missileShip.position.x +" , " + missileShip.position.y + " )");
        label_missile_ship_position.setFont(Font.font("Fira Code", 16));
        label_missile_ship_position.setTextFill(Color.WHITE);
        vbox_state.getChildren().add(label_missile_ship_position);

        // Member Table
        VBox vbox_member= new VBox();
        vbox_member.setTranslateX(0.83 * width);
        vbox_member.setTranslateY(20);
        vbox_member.setSpacing(10);

        Label label_memberTitle = new Label("- MEMBER  TABLE -");
        label_memberTitle.setFont(Font.font("SimHei", 18));
        label_memberTitle.setTextFill(Color.rgb(5,150,5));
        vbox_member.getChildren().add(label_memberTitle);

        Label label_missile_num = new Label(" (ALLIANCE) Missile Car(s) :  1" );
        label_missile_num.setFont(Font.font("Fira Code", 16));
        label_missile_num.setTextFill(Color.GREEN);
        vbox_member.getChildren().add(label_missile_num);

        Label label_guideShip_num = new Label(" (ALLIANCE) Guide Car(s) :  1" );
        label_guideShip_num.setFont(Font.font("Fira Code", 16));
        label_guideShip_num.setTextFill(Color.GREEN);
        vbox_member.getChildren().add(label_guideShip_num);

        Label label_mainShip_num = new Label(" (ENEMY) Enemy Tower(s) :  1" );
        label_mainShip_num.setFont(Font.font("Fira Code", 16));
        label_mainShip_num.setTextFill(Color.RED);
        vbox_member.getChildren().add(label_mainShip_num);

        Label label_guardShips_num = new Label(" (ENEMY) Guard Unit(s) :  " + ship_list.size());
        label_guardShips_num.setFont(Font.font("Fira Code", 16));
        label_guardShips_num.setTextFill(Color.RED);
        vbox_member.getChildren().add(label_guardShips_num);

        // Game Over Label
        Label lb_gameOver = new Label("");
        lb_gameOver.setFont(Font.font("Fira Code", 40));
        lb_gameOver.setTextFill(Color.DARKRED);
        lb_gameOver.setTranslateX(width * 0.4);
        lb_gameOver.setTranslateY(30);

        ////// Shape layer //////
        Canvas canvas2 = new Canvas(width, height);
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();

        // Bg Area
        Pencil.draw_fill_round_rect(gc2, 180, 65, 350, 110, 10, 10, Color.rgb(10,10,10, 0.3));
        Pencil.draw_fill_round_rect(gc2, 180, 385, 350, 250, 10, 10, Color.rgb(10,10,10, 0.3));
        Pencil.draw_fill_round_rect(gc2, 180, 700, 350, 350, 10, 10, Color.rgb(10,10,10, 0.3));
        Pencil.draw_fill_round_rect(gc2, 0.91 * width, 95, 0.17 * width, 180, 20, 20, Color.rgb(10,10,10, 0.3));

        // Render Area
        Pencil.draw_stroke_rect(gc2, 180, 700, 300, 300, Color.WHITE);
        Pencil.draw_stroke_circle(gc2, 180, 700, 150, Color.WHITE);
        Pencil.draw_stroke_circle(gc2, 180, 700, 100, Color.rgb(200,200,200, 0.6));
        Pencil.draw_stroke_circle(gc2, 180, 700, 50, Color.rgb(200,200,200, 0.6));
        Pencil.draw_stroke_circle(gc2, 180, 700, 10, Color.rgb(200,200,200, 0.6));
        Pencil.draw_line(gc2, create_line(new Vector2(30, 700), new Vector2(330, 700)), 1, Color.rgb(200,200,200, 0.6));
        Pencil.draw_line(gc2, create_line(new Vector2(180, 550), new Vector2(180, 850)), 1, Color.rgb(200,200,200, 0.6));

        // Ships layer
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pencil.draw_fill_Rect(gc, guideShip.origin_pos.x, guideShip.origin_pos.y, 10, 10, Color.CORAL);

        Thread draw_thread = new Thread(()->{
            double theta = -90;
            double R = 150;
            Vector2 o_render = new Vector2(180,700);
            Vector2 a_render = new Vector2(180, 700);

            while(true) {
                a_render.x = o_render.x + Math.cos(Math.toRadians(theta)) * R;
                a_render.y = o_render.y + Math.sin(Math.toRadians(theta)) * R;
                theta += 1;

                gc.clearRect(0, 0, width, height);
                for (Ship ship: ship_list) {
                    Pencil.draw_fill_Rect(gc, ship.real_position.x, ship.real_position.y, 10, 10, Color.RED);
                    Pencil.draw_stroke_circle(gc, o_of_the_circle_path.x, o_of_the_circle_path.y, ship.R, Color.rgb(150,0,0,0.8));
                    Pencil.draw_stroke_circle(gc, ship.real_position.x, ship.real_position.y, ship.obs_R, Color.rgb(255,0,0, 0.5));
                }

                gc.drawImage(img_guideShip, guideShip.current_pos.x - 30, guideShip.current_pos.y - 18, 60, 36);
                Pencil.draw_stroke_circle(gc, guideShip.current_pos.x, guideShip.current_pos.y, guideShip.obs_R, Color.rgb(200,100,100, 0.8));
                if (guideShip.guide_flag)
                    Pencil.draw_line(gc, create_line(guideShip.current_pos, guideShip.target_pos), 3, Color.RED);
                if (missileShip.fire_flag){
                    Pencil.draw_line(gc, create_line(missileShip.position, guideShip.target_pos), 2, Color.DARKGREEN);
                    if (!missile.start_flag)
                        start_missile(missile, o_of_the_circle_path);
                    gc.drawImage(img_missile, missile.position.x, missile.position.y, 35, 20);
                }
                Pencil.draw_line(gc, create_line(o_render, a_render), 3, Color.rgb(200,200,200, 0.8));

                Ship.easy_sleep(100);

                Platform.runLater(()->{
                    label_guardShip_num.setText(" GuardUnit(s) :   " + ship_list.size());
                    label_guardShips_num.setText(" (ENEMY) Guard Unit(s) :  " + ship_list.size());

                    if (guideShip.guide_flag){
                        label_guideShip_state.setText(" GuideUnit's State :   " + "Guiding...");
                        label_missile_state.setText(" MissileCar's State :   " + "Preparing...");
                    }
                    else if (guideShip.be_detected){
                        label_guideShip_state.setText(" GuideCar's State :   " + "DEAD!");
                        lb_gameOver.setText("- EPISODE  OVER -");
                    }

                    if (missileShip.fire_flag){
                        label_missile_state.setText(" MissileShip's State :   " + "Fire!");
                        label_guideShip_state.setText(" GuideShip's State :   " + "Finished!");
                    }

                    if(missile.destroy_flag)
                        lb_gameOver.setText("- EPISODE  OVER -");

                    label_guide_ship_position.setText(" GuideShip's position :   ( " + new DecimalFormat("#.0").format(guideShip.current_pos.x) +" , " + new DecimalFormat("#.0").format(guideShip.current_pos.y) + " )");
                });

                if (guideShip.be_detected || missile.destroy_flag)
                    break;
            }
            });
        draw_thread.start();

        root.getChildren().add(bg);
        draw_grid(width, height, root);
        root.getChildren().add(lb_main_ship);
        root.getChildren().add(lb_missile_ship);
        root.getChildren().add(canvas);
        root.getChildren().add(canvas2);
        root.getChildren().add(btn_auto_add);
        root.getChildren().add(btn_ac_add_ship);
        root.getChildren().add(btn_c_add_ship);
        root.getChildren().add(btn_start_guideShip);
        root.getChildren().add(btn_start_high_guideShip);
        root.getChildren().add(btn_reset);
        root.getChildren().add(vb);
        root.getChildren().add(vbox_state);
        root.getChildren().add(vbox_member);
        root.getChildren().add(lb_gameOver);
        primaryStage.setScene(new Scene(root, width, height, Color.WHITE));
        primaryStage.show();
    }
}
