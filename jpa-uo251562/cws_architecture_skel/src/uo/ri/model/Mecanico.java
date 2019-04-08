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

import uo.ri.model.types.ContractStatus;

@Entity
@Table(name = "TMECANICOS")
public class Mecanico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String dni;
	private String apellidos;
	private String nombre;
	@OneToMany(mappedBy = "mecanico")
	private Set<Averia> averias = new HashSet<>();
	@OneToMany(mappedBy = "mecanico")
	private Set<Intervencion> intervenciones = new HashSet<Intervencion>();
	@OneToMany(mappedBy = "mecanico")
	private Set<Contract> contracts = new HashSet<Contract>();

	Mecanico() {

	}

	public Mecanico(String dni) {
		this.dni = dni;
	}

	public Mecanico(String dni, String apellidos, String nombre) {
		this(dni);
		this.apellidos = apellidos;
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public String getDni() {
		return dni;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<Averia> getAsignadas() {
		return new HashSet<>(averias);
	}

	Set<Averia> _getAsignadas() {
		return averias;
	}

	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	public Set<Intervencion> getIntervenciones() {
		return new HashSet<>(intervenciones);
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mecanico [dni=" + dni + ", apellidos=" + apellidos + ","
				+ " nombre=" + nombre + "]";
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * recorros todos los contratos y devuelvo el q este activo y si no null
	 * 
	 * @return
	 */
	public Contract getActiveContract() {

		for (Contract c : contracts) {

			if (c.getStatus().equals(ContractStatus.ACTIVE))
				return c;

		}
		return null;

	}

}
