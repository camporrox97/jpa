package uo.ri.business.repository;

import java.util.Date;
import java.util.List;

import uo.ri.model.Mecanico;

public interface MecanicoRepository extends Repository<Mecanico> {

	/**
	 * @param dni
	 * @return the mechanic identified by the dni or null if none 
	 */
	Mecanico findByDni(String dni);

	

	/**
	 * 
	 * @return the mechanics  with active contracts 
	 */
	List<Mecanico> findAllActives();



	List<Mecanico> findAllMechanicsWithRepuestos(Date date);



	List<Mecanico> findAllMechanicsWithIntervencionesLastYear(Date date);
	
}
