package pe.edu.pucp.iweb.trabajo.Beans;

public class BCarritoCliente {


    private String producto;
    private String codigo;
    private String stock;
    private int cantidad;
    private double precio;

    public BCarritoCliente(String producto, String codigo, String stock, int cantidad, double precio) {
        this.producto = producto;
        this.codigo = codigo;
        this.stock = stock;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


}
