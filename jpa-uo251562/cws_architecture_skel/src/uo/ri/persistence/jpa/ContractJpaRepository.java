package uo.ri.persistence.jpa;

import java.util.Date;
import java.util.List;

import uo.ri.business.repository.ContractRepository;
import uo.ri.model.Contract;
import uo.ri.model.Payroll;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractJpaRepository   
extends BaseRepository<Contract>
implements ContractRepository {

	@Override
	public List<Contract> buscarContratos(Date fechaNominas) {
		
		return Jpa.getManager()
				.createNamedQuery("Contract.findByDate",Contract.class)
				.setParameter(1, fechaNominas)
				.getResultList();
		
	}

	@Override
	public List<Contract> findContractById(Long id) {
		
		return Jpa.getManager()
				.createNamedQuery("Contract.findContractById",Contract.class)
				.setParameter(1, id)
				.getResultList();
		
	}

}
