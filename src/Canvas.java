/**
 * Класс-холст для расположения объектов и их отрисовки
 */

public class Canvas {
    // ширина и высота
    private int width   ;
    private int height;
    // матрица объектов
    private Sell[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Sell[height][width];
    }

    /**
     * Устанавливаем на карте объект, если он удовлетворяет условиям
     * @param object - текущий объект
     */

    public void setPoint(Sell object) {
        int x = object.getX();
        int y = object.getY();

        if (y < 0 || y >= height) return;
        if (x < 0 || x >= width) return;

        matrix[y][x] = object;
    }

    /**
     * Выводим карту на экран
     */

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(" ");
                System.out.print(matrix[i][j].getImage());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public Sell[][] getMatrix() {
        return matrix;
    }
}

