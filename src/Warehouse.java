/**
 * Класс, обозначающий пункт доставки
 */

public class Warehouse extends Sell {
    // кол-во складов на карте
    private static int quantity;

    public Warehouse(int x, int y) {
        super(x, y, true, "O");
        quantity++;
    }

    public static int getQuantity() {
        return quantity;
    }
}