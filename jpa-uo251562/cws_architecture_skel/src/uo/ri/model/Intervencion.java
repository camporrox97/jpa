package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TINTERVENCIONES")
public class Intervencion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Averia averia;
	@ManyToOne
	private Mecanico mecanico;
	private int minutos;
	@OneToMany(mappedBy = "intervencion")
	private Set<Sustitucion> sustituciones = new HashSet<Sustitucion>();

	Intervencion() {
	}

	public Intervencion(Mecanico mecanico, Averia averia) {
		Association.Intervenir.link(averia, this, mecanico);
	}

	public long getId() {
		return id;
	}

	public void _setAveria(Averia averia) {
		this.averia = averia;

	}

	public void _setMecanico(Mecanico mecanico) {

		this.mecanico = mecanico;

	}

	public Averia getAveria() {
		return averia;
	}

	Averia _getAveria() {
		return averia;
	}

	public void setAveria(Averia averia) {
		this.averia = averia;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	Mecanico _getMecanico() {
		return mecanico;
	}

	public void setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averia == null) ? 0 : averia.hashCode());
		result = prime * result
				+ ((mecanico == null) ? 0 : mecanico.hashCode());
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
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals(other.averia))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		return true;
	}

	public Set<Sustitucion> getSustituciones() {
		return new HashSet<>(sustituciones);
	}

	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	public double getImporte() {
		double importe = minutos
				* (averia.getVehiculo().getTipo().getPrecioHora() / 60.0);
		for (Sustitucion sustitucion : sustituciones)
			importe += sustitucion.getImporte();
		return importe;
	}

}
