import java.util.*;

public class Algorithm {
    // открытый список для ожидающих рассмотрения вершин
    private static List<Sell> openedList = new ArrayList<>();
    // закрытый список для просмотренных вершин
    private static List<Sell> closedList = new ArrayList<>();

    private static int getDistanceBetweenNeighbours(Sell from, Sell to) {
        if (Math.abs(from.x - to.x) == 1 && Math.abs(from.y - to.y) == 1) return 14;
        else return 10;
    }

    private static int getHeuristicLength(Sell from, Sell to) {
        return 10 * (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()));
    }

    private static List<Sell> findNeighbour(Canvas canvas, Sell sell) {
        List<Sell> nb = new ArrayList<>();
        // координаты точки
        int sellX = sell.getX();
        int sellY = sell.getY();
        // список координат соседей
        int[] allX = new int[] {sellX - 1, sellX - 1, sellX - 1, sellX, sellX + 1, sellX + 1, sellX + 1, sellX};
        int[] allY = new int[] {sellY - 1, sellY, sellY + 1, sellY + 1, sellY + 1, sellY, sellY - 1, sellY - 1};

        for (int i = 0; i < allX.length; i++) {
            Sell neighboursSell = canvas.getMatrix()[allY[i]][allX[i]];
            if (neighboursSell.isAvailable()) nb.add(neighboursSell);
        }
        return nb;
    }

    public static List<Sell> searching(Canvas canvas, Sell start, Sell finish) {
        // находим всех соседей
        for (int i = 1; i < canvas.getMatrix().length - 1; i++) {
            for (int j = 1; j < canvas.getMatrix()[0].length - 1; j++) {
                Sell tempSell = canvas.getMatrix()[i][j];
                if (tempSell.isAvailable())
                    tempSell.setNeighbours(findNeighbour(canvas, tempSell));
            }
        }
        // инициализируем поля стартовой точки
        start.setParent(null);
        start.setH(getHeuristicLength(start, finish));
        start.setG(0);
        start.setF(start.getG() + start.getH());
        // добавляем стартовую точку в открытый список
        openedList.add(start);
        // пока на рассмотрении есть точки
        while (!openedList.isEmpty()) {
            // находим у текущей клетки соседа с минимальным F
            Sell currentSell = minF(openedList);
            // Проверяем, не является ли эта точка конечной точкой
            if (currentSell.getX() == finish.getX() && currentSell.getY() == finish.getY()) {
                return getPath(finish);
            }
            // удаляем точку из открытого списка
            openedList.remove(currentSell);
            // добавляем в закрытый
            closedList.add(currentSell);
            // для всех соседей данной точки
            for (Sell neighbourSell : currentSell.getNeighbours()) {
                // если закрытый список уже содержит этого соседа, то переходим к следующему
                if (closedList.contains(neighbourSell)) continue;
                // не понимаю, зачем это!
                int tentativeG = currentSell.G + getDistanceBetweenNeighbours(currentSell, neighbourSell);
                // если открытый список не содерит нашего соседа
                if (!openedList.contains(neighbourSell)) {
                    // заполняем поля
                    neighbourSell.setParent(currentSell);
                    neighbourSell.setG(neighbourSell.parent.G + getDistanceBetweenNeighbours(currentSell, neighbourSell));
                    neighbourSell.setH(getHeuristicLength(neighbourSell, finish));
                    neighbourSell.setF(neighbourSell.getG() + neighbourSell.getH());
                    // и добавляем в открытый список
                    openedList.add(neighbourSell);
                }
                else {
                    // если сумма G и H нашей точки, меньше G соседа
                    if (tentativeG < neighbourSell.G) {
                        neighbourSell.setG(tentativeG);
                        neighbourSell.setH(getHeuristicLength(neighbourSell, finish));
                        neighbourSell.setF(neighbourSell.getG() + neighbourSell.getH());
                        neighbourSell.setParent(currentSell);
                    }
                }
            }
        }
        // если маршрута нет, возвращаем null
        return null;
    }

    private static List<Sell> getPath(Sell sell) {
        List<Sell> result = new ArrayList<>();

        while (sell.parent != null) {
            result.add(sell);
            sell = sell.parent;
        }

        return result;
    }

    public static Sell minF(List<Sell> sells) {
        int minF = Integer.MAX_VALUE;
        Sell result = null;
        for (int i = 0; i < sells.size(); i++) {
            int currentF = sells.get(i).getF();
            if (currentF < minF) {
                minF = currentF;
                result = sells.get(i);
            }
        }
        return result;
    }
}
