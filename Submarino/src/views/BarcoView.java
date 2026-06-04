package views;

import java.util.List;
import java.util.ArrayList;

public class BarcoView {

    private double x;
    private double y;
    private List<CargaView> cargas;

    public BarcoView() {
        this.cargas = new ArrayList<>();
    }

    public BarcoView(double x, double y, List<CargaView> cargas) {
        this.x = x;
        this.y = y;
        this.cargas = cargas;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public List<CargaView> getCargas() { return cargas; }
    public void setCargas(List<CargaView> cargas) { this.cargas = cargas; }
}