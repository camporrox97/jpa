package uo.ri.ui.cash.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListarFacturasExamenAction implements Action{

	@Override
	public void execute() throws Exception {
		InvoiceService i = Factory.service.forInvoice();
		List<InvoiceDto> invoices = i.findAllInvoiceExam();
		
		Console.println("\nListado de facturas\n");  
		for(InvoiceDto m : invoices) {
			Printer.printInvoice(m);	
		}
	}

}
