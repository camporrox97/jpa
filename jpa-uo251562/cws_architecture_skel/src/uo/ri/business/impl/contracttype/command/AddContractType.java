package uo.ri.business.impl.contracttype.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class AddContractType implements Command <Void> {

	private ContractTypeDto dto;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public AddContractType(ContractTypeDto contracttype) {
		this.dto = contracttype;
	}

	public Void execute() throws BusinessException {
		ContractType c = EntityAssembler.toEntity( dto );
		checkCanBeAdd(c);
		repo.add( c );
		return null;
	}

	private void checkCanBeAdd(ContractType c) throws BusinessException{
		
		BusinessCheck.isNull(repo.findByName(dto.name),"nombre ya existente");
		
		BusinessCheck.isTrue(dto.compensationDays > 0, "los dias compensacion "
				+ "no pueden ser negativo");
		
		
	}
}
