import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

class Pencil {

    static void draw_line(GraphicsContext gc, ArrayList<Vector2> line_pos_list, double line_width, Color color) {
        gc.save();
        for (int i = 0; i < line_pos_list.size() - 1; i++) {
            gc.setStroke(color);
            gc.setLineWidth(line_width);
            gc.strokeLine(line_pos_list.get(i).x, line_pos_list.get(i).y, line_pos_list.get(i + 1).x, line_pos_list.get(i + 1).y);
        }
        gc.restore();
    }

    static void draw_dotted_line(Group root, Vector2 startPos, Vector2 tarPos, double line_width,  Color color){
        Line dotted_line = new Line(startPos.x, startPos.y, tarPos.x, tarPos.y);
        dotted_line.setStrokeWidth(line_width);
        dotted_line.setStroke(color);
        dotted_line.getStrokeDashArray().addAll(10d);
        root.getChildren().add(dotted_line);
    }

    static void draw_fill_Rect(GraphicsContext gc, double x, double y, double width, double height, Color color){
        gc.save();
        gc.setFill(color);
        gc.fillRect(x - width / 2, y - height / 2, width, height);
        gc.restore();
    }

    static void write_text(Group root, double x, double y, String content, Font font, Color color){
        Text text = new Text(x, y, content);
        text.setFont(font);
        text.setFill(color);
        root.getChildren().add(text);
    }

    static void draw_fill_circle(GraphicsContext gc, double x, double y, double r, Color color){
        gc.save();
        gc.setFill(color);
        gc.fillOval(x - r / 2, y - r / 2, r, r);
        gc.restore();
    }

    static void draw_stroke_circle(GraphicsContext gc, double x, double y, double r, Color color){
        gc.save();
        gc.setStroke(color);
        gc.strokeOval(x - r, y - r, r * 2, r * 2);
        gc.restore();
    }

    static void draw_fill_round_rect(GraphicsContext gc, double x, double y, double width, double height, double arc_wide, double arc_height, Color color){
        gc.save();
        gc.setFill(color);
        gc.fillRoundRect(x - width / 2, y - height / 2, width, height, arc_wide, arc_height);
        gc.restore();
    }

    static void draw_stroke_rect(GraphicsContext gc, double x, double y, double width, double height, Color color){
        gc.save();
        gc.setStroke(color);
        gc.strokeRect(x - width / 2, y - height / 2, width, height);
        gc.restore();
    }
}
