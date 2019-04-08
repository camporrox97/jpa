package uo.ri.business.impl.contracttype;

import java.util.List;

import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.contracttype.command.AddContractType;
import uo.ri.business.impl.contracttype.command.DeleteContractType;
import uo.ri.business.impl.contracttype.command.FindContractTypeById;
import uo.ri.business.impl.contracttype.command.UpdateContractType;
import uo.ri.conf.Factory;

public class ContractTypeCrudServiceImpl implements ContractTypeCrudService {
	
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void addContractType(ContractTypeDto dto) throws BusinessException {
		executor.execute(new AddContractType(dto));
		
	}

	@Override
	public void deleteContractType(Long id) throws BusinessException {
		executor.execute(new DeleteContractType(id));
		
	}

	@Override
	public void updateContractType(ContractTypeDto dto) 
			throws BusinessException {
		executor.execute(new UpdateContractType(dto));
		
	}

	@Override
	public ContractTypeDto findContractTypeById(Long id) 
			throws BusinessException {
		return executor.execute(new FindContractTypeById(id));

	}

	/**
	 * No implementado dado que no necesitamos hacerlo. Esta tachado en el 
	 * enunciado de la ampliación
	 */
	@Override
	public List<ContractTypeDto> findAllContractTypes() 
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}


}
