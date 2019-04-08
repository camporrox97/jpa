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
@Table(name = "TCONTRACTCATEGORY")
public class ContractCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String name;
	private double trieniumSalary;
	private double productivyPlus;
	@OneToMany(mappedBy = "contractCategory")
	private Set<Contract> contracts = new HashSet<Contract>();

	ContractCategory() {

	}

	public ContractCategory(String name) {

		this.name = name;
	}

	public ContractCategory(String name, double trienniumSalary,
			double productivityPlus) {

		this(name);
		this.trieniumSalary = trienniumSalary;
		this.productivyPlus = productivityPlus;

	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getTrieniumSalary() {
		return trieniumSalary;
	}

	public double getProductivyPlus() {
		return productivyPlus;
	}

	Set<Contract> _getContracts() {
		return contracts;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}

	public void setTrieniumSalary(double trieniumSalary) {
		this.trieniumSalary = trieniumSalary;
	}

	public void setProductivyPlus(double productivyPlus) {
		this.productivyPlus = productivyPlus;
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
		ContractCategory other = (ContractCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractCategory [name=" + name + "]";
	}

}
