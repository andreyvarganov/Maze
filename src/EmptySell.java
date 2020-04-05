/**
 * Класс, обозначающий пустую клетку (дорогу)
 */

public class EmptySell extends Sell {
    public EmptySell(int x, int y) {
        super(x, y, true, ".");
    }
}
