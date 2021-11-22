package pe.edu.pucp.iweb.trabajo.Beans;

public class BPedido {
    private int numeroOrden;
    private String fechaRecojo;
    private String estado;
    private String usuarioDNI;

    public BPedido(int numeroOrden, String fechaRecojo, String estado, String usuarioDNI) {
        this.numeroOrden = numeroOrden;
        this.fechaRecojo = fechaRecojo;
        this.estado = estado;
        this.usuarioDNI = usuarioDNI;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getFechaRecojo() {
        return fechaRecojo;
    }

    public void setFechaRecojo(String fechaRecojo) {
        this.fechaRecojo = fechaRecojo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioDNI() {
        return usuarioDNI;
    }

    public void setUsuarioDNI(String usuarioDNI) {
        this.usuarioDNI = usuarioDNI;
    }

}
