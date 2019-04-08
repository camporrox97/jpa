package uo.ri.persistence.jpa;


import java.util.List;

import uo.ri.business.repository.PayrollRepository;
import uo.ri.model.Payroll;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseRepository<Payroll>
implements PayrollRepository  {

	@Override
	public List<Payroll> buscarPayrollsUltimoMes() {

		return Jpa.getManager()
				.createNamedQuery("Payroll.findLastPayrolls",Payroll.class)
				.getResultList();
	}

	@Override
	public List<Payroll> findPayrollById(Long id) {
		
		return Jpa.getManager()
				.createNamedQuery("Payroll.findPayrollById",Payroll.class)
				.setParameter(1, id)
				.getResultList();
				
				
	}




}
