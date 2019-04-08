package uo.ri.business.impl.contracttype.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class FindContractTypeById implements Command <ContractTypeDto>{

	private Long id;
	private ContractTypeRepository repo = Factory.repository.forContractType();


	public FindContractTypeById(Long id) {
		this.id = id;
	}

	public ContractTypeDto execute() throws BusinessException {

		ContractType m = repo.findById( id );
		BusinessCheck.isNotNull(m,"No existe");
		return DtoAssembler.toDto( m );
		
	}


}
