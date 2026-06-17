package views;

public class CargaView {

    private double x;
    private double y;
    private boolean explotada;
    private boolean explotando;
    private int tiempoExplosion;

    public CargaView() {}

    public CargaView(double x, double y, boolean explotada,
                     boolean explotando, int tiempoExplosion) {
        this.x = x;
        this.y = y;
        this.explotada = explotada;
        this.explotando = explotando;
        this.tiempoExplosion = tiempoExplosion;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public boolean isExplotada() { return explotada; }
    public void setExplotada(boolean explotada) { this.explotada = explotada; }

    public boolean isExplotando() { return explotando; }
    public void setExplotando(boolean explotando) { this.explotando = explotando; }

    public int getTiempoExplosion() { return tiempoExplosion; }
    public void setTiempoExplosion(int tiempoExplosion) { this.tiempoExplosion = tiempoExplosion; }

    @Override
    public String toString() {
        return "Carga [x=" + x + ", y=" + y + ", explotada=" + explotada
             + ", explotando=" + explotando + ", tiempoExplosion=" + tiempoExplosion + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CargaView otro = (CargaView) obj;
        return Double.compare(x, otro.x) == 0
            && Double.compare(y, otro.y) == 0
            && explotada == otro.explotada
            && explotando == otro.explotando
            && tiempoExplosion == otro.tiempoExplosion;
    }
}