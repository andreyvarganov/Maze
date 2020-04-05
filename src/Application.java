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
    private static List<Building> buildings;
    // список складов
    private static List<Warehouse> warehouses;
    // список отправных точек
    private static List<Car> cars;
    // маршрут к каждому складу
    private static List<Sell> road;
    // полный маршрут
    private static Map<List<Sell>, Integer> route;

    public Application(int size) {
        Application.size = size;
        canvas = new Canvas(size, size);
        buildings = new ArrayList<>();
        warehouses = new ArrayList<>();
        cars = new ArrayList<>();
        route = new HashMap<>();
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
                canvas.setPoint(new EmptySell(i, j));
            }
        }
        // 2. Расставляем здания
        // кол-во объектов
        int quantity;
        // требуемое кол-во зданий
        quantity = (int)(size + Math.random() * (0.75 * (size - 1) * (size - 1)));

        Building building = new Building(0, 0);

        while (Building.getQuantity() < quantity) {
            func(building, quantity);
        }

        // требуемое кол-во зданий
        quantity = (int)(2 + Math.random() * 5);

        Warehouse warehouse = new Warehouse(0, 0);

        while (Warehouse.getQuantity() < quantity) {
            func(warehouse, quantity);
        }
        // 3. Устанавливаем пункт отправления
        Car car = new Car(0, 0);
        func(car, 1);
    }

    private void func(Sell object, int quantity) {
        // номер объекта (1 - здание, 2 - склад, 3 - машина)
        int number = 0;
        int x, y;
        do {
            x = (int) (1 + (Math.random() * (size - 1)));
            y = (int) (1 + (Math.random() * (size - 1)));
            // если объект относится к зданию
            if (object.getClass().getName().equals("Building")) {
                object = new Building(x, y);
                number = 1;
            } else if (object.getClass().getName().equals("Warehouse")) {
                object = new Warehouse(x, y);
                number = 2;
            } else {
                object = new Car(x, y);
                number = 3;
            }
            if (canvas.getMatrix()[y][x].isAvailable) break;
        } while (true);
        canvas.setPoint(object);
        switch (number) {
            case 1: buildings.add((Building) object);
            break;
            case 2: warehouses.add((Warehouse) object);
            break;
            case 3: cars.add((Car) object);
            break;
        }
    }

    /**
     * Рисуем границы карты
     */

    private void drawBorders() {
        for (int i = 0; i < size; i++) {
            canvas.setPoint(new Boarder(i, 0));
            canvas.setPoint(new Boarder(i, size - 1));
        }

        for (int i = 0; i < size; i++) {
            canvas.setPoint(new Boarder(0, i));
            canvas.setPoint(new Boarder(size - 1, i));
        }
    }

    public void run() {
        // расставляем объекты на карте
        draw();
        // выводим на экран
        canvas.print();
        // находим маршрут к каждому объекту
        for (int i = 0; i < warehouses.size(); i++) {
            road = Algorithm.searching(canvas, cars.get(0), warehouses.get(i));
            Collections.reverse(road);
            for (int j = 0; j < road.size() - 1; j++) {
                road.get(j).setImage("" + (i + 1));
            }
            route.put(road, road.size());
            // выводим на экран
            canvas.print();
        }
        if (route.isEmpty()) {
            System.out.println("Маршрут не существует!");
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
