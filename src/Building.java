/**
 * Класс, обозначающий здание
 */

public class Building extends Sell {
    // кол-во здания на карте
    private static int quantity;

    public Building(int x, int y) {
        super(x, y, false, "#");
        quantity++;
    }

    public static int getQuantity() {
        return quantity;
    }
}
