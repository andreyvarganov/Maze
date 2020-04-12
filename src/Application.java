import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Application {
    // переменная для работы с приложением
    public static Application app;
    // размерность поля
    private static int size;
    // массив объектов на карте
    private static Canvas canvas;
    // список зданий
    private static List<Sell> buildings;
    // список складов
    private static List<Sell> warehouses;
    // список отправных точек
    private static List<Sell> cars;
    // маршрут к каждому складу
    private static List<Sell> road;
    // полный маршрут
    private static List<List<Sell>> route;

    public Application(int size) {
        Application.size = size;
        canvas = new Canvas(size, size);
        buildings = new ArrayList<>();
        warehouses = new ArrayList<>();
        cars = new ArrayList<>();
        route = new ArrayList<>();
    }

    /**
     * Рисуем карту
     */

    public void draw() {
        drawBorders();
        drawObjects();
    }

    /**
     * Рисуем объекты
     */

    private void drawObjects() {
        // 1. Не трогая границы (которые уже отрисованы), все остальные клетки заполняем пустыми объектами
        for (int i = 1; i < size - 1; i++) {
            for (int j = 1; j < size - 1; j++) {
                canvas.setPoint(new Sell(i, j, true, ".", false, false));
            }
        }
        // 2. Расставляем здания
        // кол-во объектов
        int quantity;
        // требуемое кол-во зданий
        quantity = (int)(size + Math.random() * (0.45 * (size - 1) * (size - 1)));

        while (buildings.size() < quantity) {
            func(1, quantity);
        }

        // требуемое кол-во зданий
        quantity = (int)(2 + Math.random() * 4);

        while (warehouses.size() < quantity) {
            func(2, quantity);
        }
        // 3. Устанавливаем пункт отправления
        func(3, 1);
    }

    private void func(int number, int quantity) {
        // номер объекта (1 - здание, 2 - склад, 3 - машина)
        int x, y;
        do {
            x = (int) (1 + (Math.random() * (size - 2)));
            y = (int) (1 + (Math.random() * (size - 2)));
            if (canvas.getMatrix()[y][x].isAvailable() && !canvas.getMatrix()[y][x].isFinish() || !canvas.getMatrix()[y][x].isStart()) break;
        } while (true);

        Sell sell = null;

        switch (number) {
            case 1:
                sell = new Sell(x, y, false, "#", false, false);
                buildings.add(sell);
                break;
            case 2:
                sell = new Sell(x, y, true, "O", false, true);
                warehouses.add(sell);
                break;
            case 3:
                sell = new Sell(x, y, true, "8", true, false);
                cars.add(sell);
                break;
        }

        canvas.setPoint(sell);

    }

    /**
     * Рисуем границы карты
     */

    private void drawBorders() {
        for (int i = 0; i < size; i++) {
            canvas.setPoint(new Sell(i, 0, false, "#", false, false));
            canvas.setPoint(new Sell(i, size - 1, false, "#", false, false));
        }

        for (int i = 0; i < size; i++) {
            canvas.setPoint(new Sell(0, i, false, "#", false, false));
            canvas.setPoint(new Sell(size - 1, i, false, "#", false, false));
        }
    }

    public void search(int inc) {

        for (int i = 0; i < warehouses.size(); i++) {
            road = Algorithm.searching(canvas, cars.get(0), warehouses.get(i));
            Collections.reverse(road);
            route.add(road);
        }

        // сортируем
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < route.size(); i++) {
            if (min > route.get(i).size()) {
                min = route.get(i).size();
                road = route.get(i);
            }
        }

        route = new ArrayList<>();

        for (int i = 0; i < road.size() - 1; i++) {
            road.get(i).setImage("" + inc);
        }

        cars.remove(0);
        warehouses.remove(road.get(road.size() - 1));
        cars.add(road.get(road.size() - 1));
    }

    public void run() {
        // расставляем объекты на карте
        draw();
        // отрисовка мира в приложении
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View(canvas);
                view.setVisible(true);
            }
        });
        // выводим на консоль начальное состояние (лог)
        canvas.print();
        // поиск маршрутов
        int i = 0;
        while (!warehouses.isEmpty()) {
            search(++i);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas.print();
        }
    }

    public static void main(String[] args) {
        // создаем карту размером NхN
        int size = 0;
        System.out.print("Введите N: ");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            size = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        app = new Application(size);
        app.run();
        System.out.println("\nКол-во зданий на карте: " + buildings.size());
        System.out.println("Кол-во складов на карте: " + warehouses.size());
    }

}
