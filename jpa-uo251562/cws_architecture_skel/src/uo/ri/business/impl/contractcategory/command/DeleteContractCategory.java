package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class DeleteContractCategory implements Command<Void> {

	private Long idCategoryContract;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public DeleteContractCategory(Long idCategoryContract) {
		this.idCategoryContract = idCategoryContract;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory c = repo.findById( idCategoryContract );
		BusinessCheck.isNotNull(c, "No existe");
		checkCanBeDelate( c );
		repo.remove( c );
		return null;
	}

	private void checkCanBeDelate(ContractCategory c) throws BusinessException {

		BusinessCheck.isTrue(c.getContracts().isEmpty(),
				"no se puede borrar no tiene contratos asociados");
				

	}

}
