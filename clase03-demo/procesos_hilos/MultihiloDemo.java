public class MultihiloDemo {
    public static void main(String[] args) throws InterruptedException {

        Runnable tarea = () -> {
            String nombre = Thread.currentThread().getName();
            for (int i = 1; i <= 5; i++) {
                System.out.println(nombre + " - paso " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread hiloA = new Thread(tarea, "HiloA");
        Thread hiloB = new Thread(tarea, "HiloB");

        hiloA.start();
        hiloB.start();

        hiloA.join();
        hiloB.join();

        System.out.println("Ambos hilos terminaron.");
    }
}