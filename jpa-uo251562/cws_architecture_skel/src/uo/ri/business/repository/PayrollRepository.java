package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Payroll;

public interface PayrollRepository extends Repository<Payroll>{

	List<Payroll> buscarPayrollsUltimoMes();

	List<Payroll> findPayrollById(Long id);


}
