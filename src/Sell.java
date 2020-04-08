import java.util.List;

/**
 * Класс описывающий один объект на поле
 */

public class Sell {
    // координаты объекта
    private int x, y;
    // проходимость объекта (false - для преград, которыми являются стены и здания; true - для всего остального)
    private boolean isAvailable;
    // отрисовка объекта (как будет выглядеть на карте)
    private String image;
    // яв-ся ли клетка начальной
    private boolean isStart;
    // яв-ся ли клетка конечной
    private boolean isFinish;
    // соседние ДОСТУПНЫЕ клетки
    private List<Sell> neighbours;
    // родительская клетка (чтобы знать, с какой клетки попали в текущую)
    private Sell parent;
    // стоимость перехода в текущую клетку из родительской клетки
    private int G;
    // стоимость попадания из этой клетки в пункт назначения, рассчитанная при помощи манхетенского расстояния
    private int H;
    // финальная стоимость, равная сумме G и H
    private int F;

    public Sell(int x, int y, boolean isAvailable, String image, boolean isStart, boolean isFinish) {
        this.x = x;
        this.y = y;
        this.isAvailable = isAvailable;
        this.image = image;
        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNeighbours(List<Sell> neighbours) {
        this.neighbours = neighbours;
    }

    public void setParent(Sell parent) {
        this.parent = parent;
    }

    public void setG(int g) {
        G = g;
    }

    public void setH(int h) {
        H = h;
    }

    public void setF(int f) {
        F = f;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getImage() {
        return image;
    }

    public List<Sell> getNeighbours() {
        return neighbours;
    }

    public Sell getParent() {
        return parent;
    }

    public int getG() {
        return G;
    }

    public int getH() {
        return H;
    }

    public int getF() {
        return F;
    }
}
