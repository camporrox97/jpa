package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import uo.ri.model.types.AveriaStatus;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "FECHA",
		"VEHICULO_ID" }) }, name = "TAVERIAS")

public class Averia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String descripcion;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;
	@ManyToOne
	private Vehiculo vehiculo;
	@ManyToOne
	private Mecanico mecanico;
	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<>();
	@ManyToOne
	private Factura factura;

	Averia() {

	}

	public Averia(Vehiculo vehiculo) {
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Averia(Vehiculo vehiculo2, String descripcion) {
		this(vehiculo2);
		this.descripcion = descripcion;
	}

	public long getId() {
		return id;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return (Date) fecha.clone();
	}

	public double getImporte() {
		calcularImporte();
		return importe;
	}

	public AveriaStatus getStatus() {
		return status;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	void _setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setStatus(AveriaStatus status) {
		this.status = status;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<>(intervenciones);
	}

	public Factura getFactura() {
		return factura;
	}

	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
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
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		return true;
	}

	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException si - La avería no está en estado ABIERTA, o
	 *                               - La avería ya está enlazada con otro
	 *                               mecánico
	 */
	public void assignTo(Mecanico mecanico) {

		if (getStatus().equals(AveriaStatus.ASIGNADA)) {

			throw new IllegalStateException("La averia ya esta asignada");
		} else if (!getStatus().equals(AveriaStatus.ABIERTA)) {

			throw new IllegalStateException("La averia debe estar abierta");
		} else {
			Association.Asignar.link(mecanico, this);
			this.status = AveriaStatus.ASIGNADA;
		}

	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el importe
	 * y pasa a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La avería no está en estado ASIGNADA,
	 *                               o - La avería no está enlazada con un
	 *                               mecánico
	 */
	public void markAsFinished() {
		if (status != AveriaStatus.ASIGNADA) {
			throw new IllegalStateException("La avería no está asignada");
		} else if (mecanico == null) {
			throw new IllegalStateException(
					"La averia no esta asignada a un mecanico");
		}
		calcularImporte();
		Association.Asignar.unlink(mecanico, this);
		status = AveriaStatus.TERMINADA;
	}

	/**
	 * Calcula el importe de la averia
	 */
	public void calcularImporte() {
		importe = 0.0;
		for (Intervencion intervencion : intervenciones)
			importe += intervencion.getImporte();
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico (p.e., el
	 * primero no ha hecho bien la reparación), pero debe ser pasada a ABIERTA
	 * primero
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La avería no está en estado TERMINADA
	 *                               No se puede añadir a una factura una averia
	 *                               no terminada
	 */
	public void reopen() {
		if (status == AveriaStatus.TERMINADA) {
			status = AveriaStatus.ABIERTA;
		} else {
			throw new IllegalStateException();
		}

	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.removeAveria()
	 * Retrocede la averia a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado FACTURADA,
	 *                               o - La avería aún está enlazada con la
	 *                               factura
	 */
	public void markBackToFinished() {

		if (getStatus().equals(AveriaStatus.FACTURADA)) {
			status = AveriaStatus.TERMINADA;
		} else {

			throw new IllegalStateException();

		}

	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria()
	 * Marca la averia como FACTURADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado TERMINADA,
	 *                               o - La avería no está enlazada con una
	 *                               factura
	 */
	public void markAsInvoiced() {

		if (factura == null || !getStatus().equals(AveriaStatus.TERMINADA))
			throw new IllegalStateException();
		this.status = AveriaStatus.FACTURADA;

	}

	/**
	 * Desvincula la avería en estado ASIGNADA del mecánico y la pasa a ABIERTA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado ASIGNADA,
	 *                               o
	 */
	public void desassign() {

		if (getStatus().equals(AveriaStatus.ASIGNADA)) {

			Association.Asignar.unlink(mecanico, this);
			status = AveriaStatus.ABIERTA;

		} else {

			throw new IllegalStateException();

		}
	}

}
