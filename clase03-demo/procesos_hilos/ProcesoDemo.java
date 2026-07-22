public class ProcesoDemo {
    public static void main(String[] args) throws InterruptedException {
        long pid = ProcessHandle.current().pid();
        System.out.println("Este proceso tiene PID: " + pid);
        System.out.println("Presioná Ctrl+C para terminarlo.");

        while (true) {
            Thread.sleep(1000);
        }
    }
}
