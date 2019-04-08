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

import alb.util.date.Dates;
import uo.ri.model.types.ContractStatus;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "STARTDATE",
		"MECANICO_ID" }) }, name = "TCONTRACT")
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endData;
	private double baseSalaryPerYear;
	private double compensation;
	@Enumerated(EnumType.STRING)
	private ContractStatus status;
	@ManyToOne
	private Mecanico mecanico;
	@ManyToOne
	private ContractType contractType;
	@ManyToOne
	private ContractCategory contractCategory;
	@OneToMany(mappedBy = "contrato")
	private Set<Payroll> payrolls = new HashSet<Payroll>();

	Contract() {

	}

	public Contract(Date startDate, Mecanico mecanico) {

		this.startDate = Dates.firstDayOfMonth(startDate);
		this.mecanico = mecanico;

		Association.Contratar.link(this, mecanico);
		this.status = ContractStatus.ACTIVE;

	}

	public Contract(Mecanico mechanic, Date start, int baseSalaryPerYear) {

		this(start, mechanic);
		this.baseSalaryPerYear = baseSalaryPerYear;

	}

	public Contract(Mecanico mechanic, Date startDate, Date endDate,
			double baseSalary) {

		this(startDate, mechanic);
		if (endDate == null) {
			this.endData = null;
		} else {
			this.endData = Dates.lastDayOfMonth(endDate);
		}
		this.baseSalaryPerYear = baseSalary;
	}

	public Contract(Mecanico mechanic, Date today, double baseSalary) {

		this(today, mechanic);
		if (baseSalary < 0) {
			throw new IllegalArgumentException();
		}

		this.baseSalaryPerYear = baseSalary;

	}

	public long getId() {
		return id;
	}

	public Date getStartDate() {
		return (Date) startDate.clone();
	}

	public Date getEndDate() {
		if (endData != null) {
			return (Date) endData.clone();
		}

		return null;
	}

	public double getBaseSalaryPerYear() {
		return baseSalaryPerYear;
	}

	public double getCompensation() {
		return compensation;
	}

	public ContractStatus getStatus() {
		return status;
	}

	public Mecanico getMechanic() {
		return mecanico;
	}

	void _setMecanico(Mecanico m) {
		this.mecanico = m;
	}

	public ContractType getContractType() {
		return contractType;
	}

	void _setContractType(ContractType ct) {
		this.contractType = ct;
	}

	public ContractCategory getContractCategory() {
		return contractCategory;
	}

	void _setContractCategory(ContractCategory cc) {
		this.contractCategory = cc;
	}

	Set<Payroll> _getPayRolls() {
		return payrolls;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((mecanico == null) ? 0 : mecanico.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		Contract other = (Contract) obj;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contract [startDate=" + startDate + ", mecanico=" + mecanico
				+ "]";
	}

	public void markAsFinished(Date day) {

		if (status != ContractStatus.ACTIVE) {
			throw new IllegalStateException("El contrato no está activa");

		} else {

			Date end = Dates.lastDayOfMonth(day);
			if (!Dates.isAfter(end, startDate)) {
				throw new IllegalArgumentException();
			}
			this.endData = (Date) end.clone();

			status = ContractStatus.FINISHED;
			int diasAcompensar = (Dates.diffDays(endData, startDate) + 1) / 365;
			this.compensation = diasAcompensar
					* getContractType().getCompensationDays()
					* getBaseSalaryPerYear() / 365;

		}

	}

	public boolean isFinished() {
		return getStatus().equals(ContractStatus.FINISHED);
	}

	public Payroll getLastPayroll() {

		Payroll payroll = payrolls.stream()
				.sorted((p1, p2) -> p2.getDate().compareTo(p1.getDate()))
				.findFirst().orElse(null);

		return payroll;
	}

	public double getIrpfPercent() {

		double irpf = 0;

		if (getBaseSalaryPerYear() < 12000) {

			irpf = 0.0;
		} else if (getBaseSalaryPerYear() < 30000
				&& getBaseSalaryPerYear() >= 12000) {
			irpf = 0.10;
		} else if (getBaseSalaryPerYear() < 40000
				&& getBaseSalaryPerYear() >= 30000) {
			irpf = 0.15;

		} else if (getBaseSalaryPerYear() < 50000
				&& getBaseSalaryPerYear() >= 40000) {
			irpf = 0.2;

		} else if (getBaseSalaryPerYear() < 60000
				&& getBaseSalaryPerYear() >= 50000) {
			irpf = 0.3;

		} else {

			irpf = 0.4;
		}

		return irpf;

	}

	public void setEndDate(Date end) {

		this.endData = (Date) end.clone();

	}

	public Payroll generarNomina(Date fechaNominas, double contador) {

		Payroll ultimaNomina = getLastPayroll();
		if (ultimaNomina == null
				|| ultimaNomina.getDate().compareTo(fechaNominas) != 0) {
			return new Payroll(this, Dates.today(), contador);

		} else {
			return null;
		}
	}

}
