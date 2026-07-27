public class Rectangulo {

    private int ancho;
    private int alto;

    public Rectangulo(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
    }

    public int area(){
        return ancho * alto;
    }

    public int perimetro(){
        return 2 * (ancho + alto);
    }

    public static void main(String[] args) {

        int ancho = Integer.parseInt(args[0]);
        int alto = Integer.parseInt(args[1]);

        Rectangulo rec1 = new Rectangulo(ancho, alto);
        System.out.println("Area: " + rec1.area());
        System.out.println("Perimetro: " + rec1.perimetro());
    }
}
