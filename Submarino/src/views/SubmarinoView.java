package views;

public class SubmarinoView {

    private double x;
    private double y;
    private int vida;

    public SubmarinoView() {}

    public SubmarinoView(double x, double y, int vida) {
        this.x = x;
        this.y = y;
        this.vida = vida;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    @Override
    public String toString() {
        return "Submarino [x=" + x + ", y=" + y + ", vida=" + vida + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SubmarinoView otro = (SubmarinoView) obj;
        return Double.compare(x, otro.x) == 0
            && Double.compare(y, otro.y) == 0
            && vida == otro.vida;
    }
}