package uo.ri.ui.cash.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListarMediosDePagoAction implements Action {

	@Override
	public void execute() throws Exception {
		
		Long idCliente = Console.readLong("Id cliente ");

		List<PaymentMeanDto> mediosPago = Factory.service.forCash()
				.findPaymentMeansByClientId(idCliente);
		Printer.printPaymentMeans(mediosPago);

		
	}
	


}
