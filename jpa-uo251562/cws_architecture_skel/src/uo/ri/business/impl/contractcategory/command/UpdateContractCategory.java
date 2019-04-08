package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class UpdateContractCategory implements Command<Void> {

	private ContractCategoryDto dto;
	private ContractCategoryRepository repo = Factory
			.repository.forContractCategory();

	public UpdateContractCategory(ContractCategoryDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {

		ContractCategory c = repo.findById(dto.id);
		BusinessCheck.isNotNull(c, " la categoria no existe");
		checkCanBeUpdate(c);
		c.setTrieniumSalary(dto.trieniumSalary);
		c.setProductivyPlus(dto.productivityPlus);

		return null;
	}

	private void checkCanBeUpdate(ContractCategory m) throws BusinessException {

		BusinessCheck.isTrue(dto.trieniumSalary > 0, 
				"el trienium " + "no puede ser negativo");

		BusinessCheck.isTrue(dto.productivityPlus > 0, "el productivyPlus " 
		+ "no puede ser negativo");

	}

}
