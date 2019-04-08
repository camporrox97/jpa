package uo.ri.ui.admin.payroll.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.business.dto.PayrollDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ViewPayrollDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		
		PayrollService ps = Factory.service.forPayroll();
		List<PayrollDto> payrolls = ps.findAllPayrolls();

		Console.println("\nListado de payrolls\n");  
		for(PayrollDto p : payrolls) {
			Printer.printPayrollDetail( p );
		}
	
		Long id = Console.readLong("Id de la nómina");

		PayrollDto payroll = ps.findPayrollById(id);

		if (payroll != null) {
			Printer.printPayrollDetail(payroll);
		} else {
			Console.println("No existe la nómina");
		}
	}

}
