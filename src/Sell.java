import java.util.List;

/**
 * Класс описывающий один объект на поле. Является базовым для всех видов объектов.
 */

public abstract class Sell {
    // координаты объекта
    protected int x, y;
    // проходимость объекта (false - для преград, которыми являются стены и здания; true - для всего остального)
    protected boolean isAvailable;
    // отрисовка объекта (как будет выглядеть на карте)
    protected String image;
    // соседние ДОСТУПНЫЕ клетки
    protected List<Sell> neighbours;
    // родительская клетка (чтобы знать, с какой клетки попали в текущую)
    protected Sell parent;
    // стоимость перехода в текущую клетку из родительской клетки
    protected int G;
    // стоимость попадания из этой клетки в пункт назначения, рассчитанная при помощи манхетенского расстояния
    protected int H;
    // финальная стоимость, равная сумме G и H
    protected int F;

    public Sell(int x, int y, boolean isAvailable, String image) {
        this.x = x;
        this.y = y;
        this.isAvailable = isAvailable;
        this.image = image;
    }
}
