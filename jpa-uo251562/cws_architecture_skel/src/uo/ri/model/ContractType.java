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
@Table(name = "TCONTRACTTYPE")
public class ContractType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String name;
	private int compensationDaysPerYear;
	@OneToMany(mappedBy = "contractType")
	private Set<Contract> contracts = new HashSet<Contract>();

	ContractType() {

	}

	public ContractType(String name, int compensationDays) {
		this.name = name;
		this.compensationDaysPerYear = compensationDays;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCompensationDays() {
		return compensationDaysPerYear;
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	public void setCompensationDaysPerYear(int compensationDaysPerYear) {
		this.compensationDaysPerYear = compensationDaysPerYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ContractType other = (ContractType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDaysPerYear="
				+ compensationDaysPerYear + "]";
	}

}
