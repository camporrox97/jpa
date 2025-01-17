package uo.ri.business.repository;

import java.util.Date;
import java.util.List;

import uo.ri.model.Factura;

public interface FacturaRepository extends Repository<Factura> {

	/**
	 * @param numero de la factura que se busca
	 * @return la factura identificada o null si no existe
	 */
	Factura findByNumber(Long numero);
	
	/**
	 * @return el siguiente número de factura a usar, es decir,
	 * 	el mayor número existente registrado + 1.
	 * 
	 * En un despliegue real esta forma de obtener el número 
	 * puede dar problemas en concurrencia, ya que dos hilos 
	 * simultáneos podrían llegar a obtener el mismo número.
	 * El código que use este método debería tener esto en cuenta. 
	 */
	Long getNextInvoiceNumber();

	//List<Factura> findAllInvoiceExam();

	List<Factura> findAllInvoiceExam(Date date);

	List<Factura> findAllSinAbonarByFecha(Date date);
}
