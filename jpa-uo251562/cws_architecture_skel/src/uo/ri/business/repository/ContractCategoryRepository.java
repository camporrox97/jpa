package uo.ri.business.repository;

import uo.ri.model.ContractCategory;

public interface ContractCategoryRepository
		extends Repository<ContractCategory> {

	/**
	 * @param name
	 * @return the contractCategory identified by the name or null if none
	 */
	ContractCategory findByName(String name);

}
