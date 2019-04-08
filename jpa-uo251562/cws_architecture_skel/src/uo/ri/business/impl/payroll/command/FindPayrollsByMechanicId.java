package uo.ri.business.impl.payroll.command;

import java.util.ArrayList;
import java.util.List;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class FindPayrollsByMechanicId implements Command <List<PayrollDto>> {
	
	PayrollRepository repo = Factory.repository.forPayroll();
	private MecanicoRepository repoMecanico = Factory.repository.forMechanic();
	private Long id;

	public FindPayrollsByMechanicId(Long id) {
		this.id = id;
	}

	@Override
	public List<PayrollDto> execute() throws BusinessException {
		
		Mecanico m = repoMecanico.findById(id);
		if(m == null) {
			
			return new ArrayList<PayrollDto>() ;

		}
			
		List<Payroll> p =  repo.findPayrollById( id );
		return DtoAssembler.toPayrolltoList( p );
	}
	
	

}
