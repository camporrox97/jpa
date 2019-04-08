package uo.ri.business.impl.invoice.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class PagoDudoso implements Command <Void> {
	
	FacturaRepository repo = Factory.repository.forFactura();
	private Date date;

	public PagoDudoso() {
		
		this.date = Dates.subYears(Dates.today(), 1);

	}


	@Override
	public Void execute() throws BusinessException {
		
		List<Factura> facturas =  repo.findAllSinAbonarByFecha(date);
		BusinessCheck.isNotNull(facturas,"factura no existe");
		for (Factura f : facturas) {
			f.cambioAPagoDudoso();
		}
		return null;
		

	}

}
