package uo.ri.business.repository;

import uo.ri.model.ContractType;

public interface ContractTypeRepository extends Repository<ContractType> {

	
	/**
	 * @param name
	 * @return the contractType identified by the name or null if none 
	 */
	ContractType findByName(String name);
}
