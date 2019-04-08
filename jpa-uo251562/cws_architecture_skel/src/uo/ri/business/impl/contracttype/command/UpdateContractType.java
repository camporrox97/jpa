package uo.ri.business.impl.contracttype.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto dto;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public UpdateContractType(ContractTypeDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {

		ContractType c = repo.findById(dto.id);
		BusinessCheck.isNotNull(c, "el tipo de contrato  no existe");
		checkCanBeUpdate(c);
		c.setCompensationDaysPerYear(dto.compensationDays);

		return null;

	}

	private void checkCanBeUpdate(ContractType c) throws BusinessException {

		BusinessCheck.isTrue(dto.compensationDays > 0, "los dias compensacion "
		+ "no pueden ser negativos");
	}

}
