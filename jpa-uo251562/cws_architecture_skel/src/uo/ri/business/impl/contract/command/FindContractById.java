package uo.ri.business.impl.contract.command;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FindContractById implements Command <ContractDto> {
	
	
	private ContractRepository repo = Factory.repository.forContracts();
	private Long id;
	
	public FindContractById(Long id) {
		this.id = id;
	}

	@Override
	public ContractDto execute() throws BusinessException {
		
		Contract c = repo.findById(id);
		check(c);
		return DtoAssembler.toDto(c);
	}
	
	private void check(Contract c) throws BusinessException {
		
		BusinessCheck.isNotNull(c, "El contrato no existe para dicho id");
	}

}
