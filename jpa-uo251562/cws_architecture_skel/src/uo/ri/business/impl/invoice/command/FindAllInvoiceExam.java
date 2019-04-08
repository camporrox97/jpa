package uo.ri.business.impl.invoice.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class FindAllInvoiceExam implements Command <List<InvoiceDto>> {
	
	FacturaRepository repo = Factory.repository.forFactura();
	private Date date;

	public FindAllInvoiceExam() {
		
		this.date = Dates.subYears(Dates.today(), 1);

	}
	
	
	@Override
	public List<InvoiceDto> execute() throws BusinessException {
		List<Factura> facturas =  repo.findAllInvoiceExam(date);
		return DtoAssembler.toInvoiceDtoList(facturas);
	}

}
