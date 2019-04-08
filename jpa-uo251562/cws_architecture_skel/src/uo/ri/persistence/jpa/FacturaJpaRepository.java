package uo.ri.persistence.jpa;

import java.util.Date;
import java.util.List;

import uo.ri.business.repository.FacturaRepository;
import uo.ri.model.Factura;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class FacturaJpaRepository 
		extends BaseRepository<Factura>
		implements FacturaRepository {

	@Override
	public Factura findByNumber(Long numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		return Jpa.getManager()
				.createNamedQuery("Factura.getNextInvoiceNumber", Number.class)
				.getResultList().stream()
				.findFirst().get().longValue();
	}

	@Override
	public List<Factura> findAllInvoiceExam(Date date) {

		return Jpa.getManager()
				.createNamedQuery("Factura.findAllInvoiceExam",Factura.class)
				.setParameter(1, date)
				.getResultList();
				
	}

	@Override
	public List<Factura> findAllSinAbonarByFecha(Date date) {
		return Jpa.getManager()
				.createNamedQuery("Factura.findAllSinAbonarByFecha",Factura.class)
				.setParameter(1, date)
				.getResultList();
	}

//	@Override
//	public List<Factura> findAllInvoiceExam() {
//		return Jpa.getManager()
//				.createNamedQuery("Factura.findAllInvoiceExam", Factura.class)
//				.getResultList();
//	}

}
