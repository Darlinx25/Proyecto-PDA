/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mark
 */

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "padre_nombre")
    private Categoria padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> subCategorias;

    @OneToMany(mappedBy = "tipoPropuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Propuesta> propuestas;

    public Categoria() {}

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.subCategorias = new ArrayList<>();
        this.propuestas = new ArrayList<>();
    }

    // getters y setters...
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getPadre() { return padre; }
    public void setPadre(Categoria padre) { this.padre = padre; }

    public List<Categoria> getSubCategorias() { return subCategorias; }
    public void setSubCategorias(List<Categoria> subCategorias) { this.subCategorias = subCategorias; }

    public void addSubcategoria(Categoria cat) {
        cat.setPadre(this);  // 💡 importante: setear la FK
        this.subCategorias.add(cat);
    }
}
