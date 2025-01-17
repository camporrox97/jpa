package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TREPUESTOS")
public class Repuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String codigo;
	private String descripcion;
	private double precio;
	@OneToMany(mappedBy = "repuesto")
	private Set<Sustitucion> sustituciones = new HashSet<Sustitucion>();

	public Repuesto() {

	}

	public Repuesto(String codigo) {
		this.codigo = codigo;
	}

	public Repuesto(String codigo, String descripcion, double precio) {

		this(codigo);
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public Set<Sustitucion> getSustituciones() {
		return new HashSet<>(sustituciones);
	}

	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Repuesto other = (Repuesto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Repuesto [codigo=" + codigo + ", descripcion=" + descripcion
				+ ", precio=" + precio + "]";
	}

}
