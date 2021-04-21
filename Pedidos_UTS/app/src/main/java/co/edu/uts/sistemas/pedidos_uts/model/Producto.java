package co.edu.uts.sistemas.pedidos_uts.model;

public class Producto {
    private int _id;
    private String codigo;
    private String nombre;
    private int precio;
    private String imagen="http://i.imgur.com/DvpvklR.png";

    public Producto(String codigo, String nombre, int precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(int _id, String codigo, String nombre, int precio, String imagen) {
        this._id = _id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public int get_id() {
        return _id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
