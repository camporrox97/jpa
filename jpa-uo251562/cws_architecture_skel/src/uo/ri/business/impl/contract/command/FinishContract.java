package uo.ri.business.impl.contract.command;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;

public class FinishContract implements Command <Void>{
	/**
	 * Un  contrato  finalizará  por  indicación  expresa  del  Administrador  de  la  empresa. 
	 * Aunque  haya  contratos  de  duración  temporal,  con  fecha  de finalización  prevista,  
	 * el  sistema  no extingue  el  contrato  en  esa  fecha  (podría  haber  prórrogas).  
	 * Se  registra  simplemente  para información  del  Administrador.  
	 * El  sistema  ofrecerá  una  opción  de  extinción  de  contrato  que establecerá la fecha de extinción 
	 * al último día del mes de la fecha que especifique el usuario y le calculará el importe de la liquidación
	 */
	

	@Override
	public Void execute() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
