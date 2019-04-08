package uo.ri.business.impl.contracttype.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class DeleteContractType implements Command<Void> {

	private Long idTypeContract;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public DeleteContractType(Long idTypeContract) {
		this.idTypeContract = idTypeContract;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType c = repo.findById( idTypeContract );
		BusinessCheck.isNotNull(c, "No existe el tipo de contrato");
		checkCanBeDelate( c );
		repo.remove( c );
		return null;
	}

	private void checkCanBeDelate(ContractType c) throws BusinessException {

		BusinessCheck.isTrue(c.getContracts().isEmpty(),
				"no se puede borrar el tipo de contrato dado, "
				+ "tiene contratos asociados");

	}

}
