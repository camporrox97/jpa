package uo.ri.business.impl.payroll.command;

import java.util.List;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Payroll;

public class DeleteLastGenetaredPayrolls implements Command <Integer>{
	
	private PayrollRepository repo = Factory.repository.forPayroll();
	private int total;
	


	@Override
	public Integer execute() throws BusinessException {
		
		List<Payroll> payrollsLastMonth = repo.buscarPayrollsUltimoMes();
		for(Payroll p : payrollsLastMonth) {
			
			Association.Percibir.unlink(p, p.getContract());
			repo.remove(p);
			total++;


		}
		return total;
		
	}

}
