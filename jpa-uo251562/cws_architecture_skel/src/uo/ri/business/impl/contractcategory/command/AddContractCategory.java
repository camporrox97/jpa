package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class AddContractCategory implements Command<Void> {

	private ContractCategoryDto dto;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public AddContractCategory(ContractCategoryDto contractCategory) {
		this.dto = contractCategory;
	}

	public Void execute() throws BusinessException {
		ContractCategory m = EntityAssembler.toEntity(dto);
		checkCanBeAdd( m );
		repo.add( m );
		return null;
	}

	private void checkCanBeAdd(ContractCategory m) throws BusinessException {

		BusinessCheck.isNull(repo.findByName(dto.name), "nombre ya existente");

		BusinessCheck.isTrue(dto.trieniumSalary > 0,
				"el trienium " + "no puede ser negativo");

		BusinessCheck.isTrue(dto.productivityPlus > 0,
				"el productivyPlus " + "no puede ser negativo");

	}
}
