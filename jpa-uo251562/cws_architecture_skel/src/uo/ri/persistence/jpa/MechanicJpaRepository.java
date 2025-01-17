package uo.ri.persistence.jpa;


import java.util.Date;
import java.util.List;

import uo.ri.business.repository.MecanicoRepository;
import uo.ri.model.Mecanico;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class MechanicJpaRepository 
			extends BaseRepository<Mecanico>
			implements MecanicoRepository {

	
	@Override
	public Mecanico findByDni(String dni) {

		
			return Jpa.getManager()
					.createNamedQuery("Mecanico.findByDni",Mecanico.class)
					.setParameter(1, dni)
					.getResultList().stream()
					.findFirst()
					.orElse(null);
					
	}

	@Override
	public List<Mecanico> findAllActives() {
		
		return Jpa.getManager()
				.createNamedQuery("Mecanico.findAllActives", Mecanico.class)
				.getResultList();
		
	}

	@Override
	public List<Mecanico> findAllMechanicsWithRepuestos(Date date) {
		return Jpa.getManager()
				.createNamedQuery("Mecanico.findAllMechanicsWithRepuestos",Mecanico.class)
				.setParameter(1, date)
				.getResultList();
	}

	@Override
	public List<Mecanico> findAllMechanicsWithIntervencionesLastYear(Date date) {
		return Jpa.getManager()
				.createNamedQuery("Mecanico.findAllMechanicsWithIntervencionesLastYear"
						,Mecanico.class)
				.setParameter(1, date)
				.getResultList();
	}
	

}
