package uo.ri.business.impl.mechanic.command;


import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class DeleteMechanic  implements Command <Void>{

	private Long idMecanico;
	private MecanicoRepository repo = Factory.repository.forMechanic();


	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	
	public Void execute() throws BusinessException {
		

		
		Mecanico m = repo.findById(idMecanico);
		BusinessCheck.isNotNull(m,"No existe");
		checkCanBeDelate( m );
		repo.remove(m);

		
		return null;
	}


	private void checkCanBeDelate(Mecanico m) throws BusinessException{
		
		BusinessCheck.isTrue(m.getIntervenciones().
				isEmpty(),"no se puede borrar el mecanico dado,"
						+ "tiene intervenciones");
		BusinessCheck.isTrue(m.getAsignadas().
				isEmpty(),"no se puede borrar el mecanico dado,"
						+ "tiene intervenciones");
		BusinessCheck.isTrue(m.getContracts().
				isEmpty(),"no se puede borrar el mecanico dado,"
						+ "tiene contratos");
		
		

	}
}
