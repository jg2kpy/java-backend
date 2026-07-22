public class Ejercicio1 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("No es la cantidad sufiente de argumentos");
            return;
        }

        int n = Integer.parseInt(args[0]);
        String cadena = args[1];
        int i = 0;
        while (i < n) {
            System.out.println(cadena);
            i++;
        };
    }
}
