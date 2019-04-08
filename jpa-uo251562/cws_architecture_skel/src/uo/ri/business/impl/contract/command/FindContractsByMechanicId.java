package uo.ri.business.impl.contract.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class FindContractsByMechanicId implements Command <List<ContractDto>>{
	
	ContractRepository repo = Factory.repository.forContracts();
	private MecanicoRepository repoMecanico = Factory.repository.forMechanic();
	private Long id;

	public FindContractsByMechanicId(Long id) {
		this.id = id;
	}

	@Override
	public List<ContractDto> execute() throws BusinessException {

		Mecanico m = repoMecanico.findById(id);
		if(m == null) {
			
			return new ArrayList<ContractDto>() ;

		}
			
		List<Contract> p =  repo.findContractById( id );
		return DtoAssembler.toContracttoList( p );	}
	
	

}
