package uo.ri.business.repository;

import java.util.Date;
import java.util.List;

import uo.ri.model.Contract;

public interface ContractRepository extends Repository<Contract> {

	List<Contract> buscarContratos(Date fechaNominas);

	List<Contract> findContractById(Long id);

}
