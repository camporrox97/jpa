package uo.ri.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import alb.util.date.Dates;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "DATE",
		"CONTRATO_ID" }) }, name = "TPAYROLL")
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private double baseSalary;
	private double extraSalary;
	private double productivity;
	private double trienium;
	private double irpf;
	private double socialSecurity;
	@ManyToOne
	private Contract contrato;

	Payroll() {

	}

	public Payroll(Date date, Contract contrato) {

		Date d = (Dates.subMonths(date, 1));

		this.date = Dates.lastDayOfMonth(d);

		if (Dates.isBefore(getDate(), contrato.getStartDate())) {

			throw new IllegalArgumentException();
		}

		Association.Percibir.link(this, contrato);
		this.productivity = 0;
		this.baseSalary = calcularSalarioBase();
		this.extraSalary = calcularPagaExtra();
		this.trienium = calcularTrienios();
		this.irpf = getContract().getIrpfPercent() * getGrossTotal();
		this.socialSecurity = ((getContract().getBaseSalaryPerYear() / 12)
				* (0.05));

	}

	public Payroll(Contract contract, Date payrollDate,
			double totalOfInterventions) {
		this(payrollDate, contract);

		this.productivity = totalOfInterventions
				* contrato.getContractCategory().getProductivyPlus();
		this.irpf = getContract().getIrpfPercent() * getGrossTotal();
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return (Date) date.clone();
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public double getExtraSalary() {

		return extraSalary;
	}

	public double getProductivity() {
		return productivity;
	}

	public double getTriennium() {
		return trienium;
	}

	public double getIrpf() {
		return irpf;
	}

	public double getSocialSecurity() {
		return socialSecurity;
	}

	public Contract getContract() {
		return contrato;
	}

	public void _setContrato(Contract contrato) {
		this.contrato = contrato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Payroll other = (Payroll) obj;
		if (contrato == null) {
			if (other.contrato != null)
				return false;
		} else if (!contrato.equals(other.contrato))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", baseSalary=" + baseSalary + ""
				+ ", extraSalary=" + extraSalary + ", productivity="
				+ productivity + ", trienium=" + trienium + ", irpf=" + irpf
				+ ", socialSecurity=" + socialSecurity + ", contrato="
				+ contrato + "]";
	}

	private double calcularSalarioBase() {

		return getContract().getBaseSalaryPerYear() / 14;
	}

	private double calcularPagaExtra() {

		if (Dates.month(getDate()).equals(6)
				|| Dates.month(getDate()).equals(12)) {

			return getContract().getBaseSalaryPerYear() / 14;

		}
		return 0;

	}

	private double calcularTrienios() {

		int trienios = Dates.diffDays(getDate(), getContract().getStartDate())
				/ (365 * 3);

		return getContract().getContractCategory().getTrieniumSalary()
				* trienios;
	}

	public double getGrossTotal() {

		return getBaseSalary() + getExtraSalary() + getProductivity()
				+ getTriennium();

	}

	public double getDiscountTotal() {

		return getIrpf() + getSocialSecurity();

	}

	public double getNetTotal() {
		return getGrossTotal() - getDiscountTotal();

	}

}
