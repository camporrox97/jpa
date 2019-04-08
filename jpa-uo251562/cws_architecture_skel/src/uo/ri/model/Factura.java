package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.date.Dates;
import alb.util.math.Round;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TFACTURAS")
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private Long numero;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe;
	private double iva;
	@Enumerated(EnumType.STRING)
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;
	@OneToMany(mappedBy = "factura")
	private Set<Averia> averias = new HashSet<Averia>();
	@OneToMany(mappedBy = "factura")
	private Set<Cargo> cargos = new HashSet<Cargo>();

	Factura() {

	}

	public Factura(Long numero) {
		this.numero = numero;
		this.fecha = Dates.now();
	}

	public Factura(Long numero, Date fecha) {
		this(numero);
		this.fecha = (Date) fecha.clone();
	}

	public Factura(Long numero, List<Averia> averias)
			throws IllegalArgumentException {
		this(numero);
		for (Averia averia : averias)
			addAveria(averia);
	}

	public Factura(long numero, Date date, List<Averia> averias) {
		this(numero, averias);
		this.fecha = (Date) date.clone();
	}

	public long getId() {
		return id;
	}

	public Long getNumero() {
		return numero;
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	public void setFecha(Date fecha) {
		this.fecha = (Date) fecha.clone();

	}

	public double getImporte() {
		calcularImporte();
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public FacturaStatus getStatus() {
		return status;
	}

	public void setStatus(FacturaStatus status) {
		this.status = status;
	}

	public void setAverias(Set<Averia> averias) {
		this.averias = averias;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Set<Averia> getAverias() {
		return new HashSet<Averia>(averias);
	}

	Set<Averia> _getAverias() {
		return averias;
	}

	public Set<Cargo> getCargos() {
		return new HashSet<Cargo>(cargos);
	}

	Set<Cargo> _getCargos() {
		return cargos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe="
				+ importe + ", iva=" + iva + ", status=" + status + "]";
	}

	/**
	 * Añade la averia a la factura y actualiza el importe e iva de la factura
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void addAveria(Averia averia) {

		if (this.status != FacturaStatus.SIN_ABONAR) {
			throw new IllegalStateException();
		} else if (averia.getStatus() != AveriaStatus.TERMINADA) {
			throw new IllegalStateException();
		} else {
			Association.Facturar.link(this, averia);
			averia.markAsInvoiced();
			calcularImporte();
		}

	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de
	 * factura /** Si la factura es anterior al 1/7/2012 el IVA es el 18%, el
	 * importe es 250€ + IVA 18%
	 */

	void calcularImporte() {
		this.importe = 0.0;
		for (Averia averia : averias)
			importe += averia.getImporte();
		this.iva = Dates.isBefore(Dates.fromDdMmYyyy(1, 7, 2012), fecha) ? 1.21
				: 1.18;
		this.importe = Round.twoCents(importe * iva);
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula el
	 * importe
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void removeAveria(Averia averia) {

		if (status == FacturaStatus.ABONADA) {
			throw new IllegalStateException("La factura ya está abonada");
		}
		Association.Facturar.unlink(this, averia);
		averia.markBackToFinished();
		calcularImporte();

	}

	/**
	 * Marks the invoice as ABONADA, but
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException if - Is already settled, or - the amounts
	 *                               paid with charges to payment means does not
	 *                               cover the total of the invoice
	 */
	public void settle() {

		if (averias.isEmpty())
			throw new IllegalStateException(
					"No se puede liquidar una factura sin averias");
		else if (getCargosTotal() > getImporte() + 0.01)
			throw new IllegalStateException("Los cargos no igualan el importe");
		else if (getCargosTotal() < getImporte() - 0.01)
			throw new IllegalStateException("Los cargos no igualan el importe");

		setStatus(FacturaStatus.ABONADA);

	}

	private double getCargosTotal() {
		double i = 0.0;
		for (Cargo c : cargos) {
			i += c.getImporte();
		}
		return i;
	}
	
	/**
	 * Método examen PAGO_DUDOSO
	 */
	public void cambioAPagoDudoso() {
		if(getStatus().equals(FacturaStatus.SIN_ABONAR)) {
		setStatus(FacturaStatus.PAGO_DUDOSO);
		}else {
			throw new IllegalStateException("el estado tiene q ser sin abonar");

		}
	}

}
