package uo.ri.business.impl.contract.command;

import java.util.List;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

//Un contrato se podrá eliminar si  el mecánico 
//no ha realizado ninguna actividad(reparación) durante su vigencia.

public class DeleteContract implements Command <Void> {
	
	//busco contrato por id para eliminarlo
	//compruebo con un metodo check:
		//Si existe el contrato
		//Si el mecánico no ha realizado ninguna reparacion durante su vigencia
	
	private ContractRepository repo = Factory.repository.forContracts();
	private Long id;

	public DeleteContract(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		return null;
		
	}
	
	
	private void checkContrato(Contract c)throws BusinessException {
		
		BusinessCheck.isNotNull(c, "El contrato ni existe");
		//if(c.getMechanic().getIntervenciones().size())
		
	}

	


}
