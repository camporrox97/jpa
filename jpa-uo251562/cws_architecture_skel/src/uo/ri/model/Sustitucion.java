package uo.ri.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TSUSTITUCIONES", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "REPUESTO_ID", "INTERVENCION_ID" }) })

public class Sustitucion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Repuesto repuesto;
	@ManyToOne
	private Intervencion intervencion;
	private int cantidad;

	Sustitucion() {
	}

	public Sustitucion(Repuesto repuesto, Intervencion intervencion, int c) {
		this.repuesto = repuesto;
		this.intervencion = intervencion;
		this.cantidad = c;
		if (cantidad < 1) {
			throw new IllegalArgumentException("La cantidad es 0");
		}
		Association.Sustituir.link(repuesto, this, intervencion);
	}

	public Long getId() {
		return id;
	}

	public Repuesto getRepuesto() {
		return repuesto;
	}

	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	public Intervencion getIntervencion() {
		return intervencion;
	}

	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime * result
				+ ((repuesto == null) ? 0 : repuesto.hashCode());
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
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sustitucion [repuesto=" + repuesto + ", intervencion="
				+ intervencion + ", cantidad=" + cantidad + "]";
	}

	public double getImporte() {
		return cantidad * repuesto.getPrecio();
	}

}
