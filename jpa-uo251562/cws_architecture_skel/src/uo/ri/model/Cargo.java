package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TCARGOS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "FACTURA_ID", "MEDIOPAGO_ID" }) })
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Factura factura;
	@ManyToOne
	private MedioPago medioPago;
	private double importe = 0.0;

	Cargo() {
	}

	public Cargo(Factura factura, MedioPago medioPago, double importe) {

		medioPago.pagar(importe);
		this.importe = importe;
		Association.Cargar.link(factura, this, medioPago);
	}

	public Long getId() {
		return id;
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago Solo se
	 * puede hacer si la factura no está abonada Decrementar el acumulado del
	 * medio de pago Desenlazar el cargo de la factura y el medio de pago
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {

		if (factura.getStatus() != FacturaStatus.ABONADA) {
			medioPago.pagar(-importe);
			Association.Cargar.unlink(this);
		} else {
			throw new IllegalStateException("factura abonada");
		}
	}

}
