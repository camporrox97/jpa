package uo.ri.business.impl.payroll.command;


import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class DeleteLastPayrollForMechanicId implements Command <Void> {
	
	private Long idMecanico;
	private PayrollRepository repo = Factory.repository.forPayroll();
	private MecanicoRepository repoMecanico = Factory.repository.forMechanic();


	public DeleteLastPayrollForMechanicId(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico m = repoMecanico.findById(idMecanico);
		BusinessCheck.isNotNull(m,"No existen mecanico para ese id");

		Contract c = m.getActiveContract();
		BusinessCheck.isNotNull(c, "No existen contrato para ese id");
		
		Payroll p = c.getLastPayroll();
		BusinessCheck.isNotNull(p, "No existen contrato para ese id");
		
		Association.Percibir.unlink(p, p.getContract());
		repo.remove(p);
		
		return null;
	}

}
