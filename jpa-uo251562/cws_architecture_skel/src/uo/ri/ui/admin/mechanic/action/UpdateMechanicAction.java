package uo.ri.ui.admin.mechanic.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		Long id = Console.readLong("Id del mecánico"); 
		String nombre = Console.readString("Nombre"); 
		String apellidos = Console.readString("Apellidos");
		
		MechanicCrudService as = Factory.service.forMechanicCrudService();

		MechanicDto m = as.findMechanicById(id);
		if ( m == null) {
			throw new BusinessException("No existe el mecánico");
		}
		m.name = nombre;
		m.surname = apellidos;  
		
		as.updateMechanic( m );
		
		Console.println("Mecánico actualizado");
	}

}
