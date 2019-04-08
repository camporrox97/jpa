package uo.ri.business.impl.contract.command;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;

public class AddContract implements Command<Void>{
	
	private ContractDto dto;
	private ContractRepository repo = Factory.repository
			.forContracts();
	
	public AddContract(ContractDto contractDto) {
		this.dto = contractDto;
	}

	@Override
	public Void execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
