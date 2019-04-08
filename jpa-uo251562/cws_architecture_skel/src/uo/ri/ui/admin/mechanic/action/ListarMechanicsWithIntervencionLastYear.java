package uo.ri.ui.admin.mechanic.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListarMechanicsWithIntervencionLastYear implements Action {

	@Override
	public void execute() throws Exception {
		
		MechanicCrudService as = Factory.service.forMechanicCrudService();
		List<MechanicDto> mechanics = as.findAllMechanicsWithIntervencionesLastYear();
		
		Console.println("\nListado de mecánicos intervenciones último año\n");  
		for(MechanicDto m : mechanics) {
			Printer.printMechanic( m );
		}
		
		
		
	}

}
