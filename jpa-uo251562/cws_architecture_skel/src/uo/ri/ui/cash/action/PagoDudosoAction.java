package uo.ri.ui.cash.action;


import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.conf.Factory;

public class PagoDudosoAction implements Action {
	


	@Override
	public void execute() throws Exception {
		
		InvoiceService is = Factory.service.forInvoice();
		is.pagoDudoso();
		

		
	}

}
