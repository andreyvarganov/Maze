/**
 * Класс-холст для расположения объектов и их отрисовки
 */

public class Canvas {
    // ширина и высота
    private int width;
    private int height;
    // матрица объектов
    private Sell[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Sell[height][width];
    }
}
