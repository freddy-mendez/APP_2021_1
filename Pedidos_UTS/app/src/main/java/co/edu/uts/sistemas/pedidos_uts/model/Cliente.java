package co.edu.uts.sistemas.pedidos_uts.model;

public class Cliente {
    private int _id;
    private String name;
    private String direccion;
    private String email;
    private int celular;

    public Cliente(String name, String direccion, String email, int celular) {
        this.name = name;
        this.direccion = direccion;
        this.email = email;
        this.celular = celular;
    }

    public Cliente(int _id, String name, String direccion, String email, int celular) {
        this._id = _id;
        this.name = name;
        this.direccion = direccion;
        this.email = email;
        this.celular = celular;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "name='" + name + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", celular=" + celular +
                '}';
    }
}
