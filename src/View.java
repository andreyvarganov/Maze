import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Canvas canvas;

    public View(Canvas canvas) {
        this.canvas = canvas;
        setTitle("Maze");
        setSize(2160, 1440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(50, 50);

        int size = canvas.getMatrix().length;

        for (int i = 0; i < canvas.getMatrix().length; i++) {
            for (int j = 0; j < canvas.getMatrix()[i].length; j++) {
                Color color;
                switch (canvas.getMatrix()[j][i].getImage()) {
                    case "#":
                        color = Color.BLACK;
                        break;
                    case "8":
                        color = Color.YELLOW;
                        break;
                    case "O":
                        color = Color.RED;
                        break;
                    case ".":
                        color = Color.WHITE;
                        break;
                    default:
                        color = Color.GRAY;
                        break;
                }
                g.setColor(color);
                g.fillRect(25 * i, 25 * j, 25, 25);
                g.setColor(Color.BLACK);
                g.drawRect(25 * i, 25 * j, 25, 25);
            }
        }
        repaint(5000, 25, 25, 25, 25);
    }
}
