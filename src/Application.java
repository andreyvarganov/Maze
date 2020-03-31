public class Application {
    // переменная для работы с приложением
    public static Application app;
    // размерность поля
    private static int size;
    // массив объектов на карте
    private static Canvas canvas;

    public Application(int size) {
        Application.size = size;
        canvas = new Canvas(size, size);
    }

    public static void main(String[] args) {

    }

}
