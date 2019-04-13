package uo.ri.business.impl.contract;

import java.util.Date;
import java.util.List;

import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.contract.command.FindContractById;
import uo.ri.business.impl.contract.command.FindContractsByMechanicId;
import uo.ri.business.impl.payroll.command.FindPayrollsByMechanicId;
import uo.ri.conf.Factory;

public class ContractCrudServiceImpl implements ContractCrudService {
	
	private CommandExecutor executor = Factory.executor.forExecutor();


	@Override
	public void addContract(ContractDto c) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateContract(ContractDto dto) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContract(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishContract(Long id, Date endDate) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContractDto findContractById(Long id) throws BusinessException {
		return executor.execute(new FindContractById(id));

	}

	@Override
	public List<ContractDto> findContractsByMechanicId(Long id) throws BusinessException {
		return executor.execute(new FindContractsByMechanicId(id));

	}

}
