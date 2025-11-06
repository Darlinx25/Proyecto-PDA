package culturarte.logica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private float montoPago;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forma_pago_id")
    private FormaPago formaPago;
    
    private String metodoPago;
    
    private LocalDateTime fechaPago;

    public Pago() {}
    
    public Pago(float montoPago, FormaPago formaPago, LocalDateTime fechaPago,String metodoPago) {
        this.metodoPago = metodoPago;
        this.montoPago = montoPago;
        this.formaPago = formaPago;
        this.fechaPago = fechaPago;
    }

    public float getMontoPago() {
        return montoPago;
    }

    public void setMonto(float monto) {
        this.montoPago = monto;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public void setMetodoPago(String metodo){
        this.metodoPago = metodo;
    }
    
    public String getMetodoPago(){
        return this.metodoPago;
    }
    
    
}
