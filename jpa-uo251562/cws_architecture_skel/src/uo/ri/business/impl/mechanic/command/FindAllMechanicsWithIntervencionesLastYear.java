package uo.ri.business.impl.mechanic.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class FindAllMechanicsWithIntervencionesLastYear implements Command<List<MechanicDto>> {
	private MecanicoRepository repo = Factory.repository.forMechanic();
	private Date date;
	
	public FindAllMechanicsWithIntervencionesLastYear() {
		
		this.date = Dates.subYears(Dates.today(), 1);
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		

		List<Mecanico> mecanicos =  repo.findAllMechanicsWithIntervencionesLastYear(date);
		return DtoAssembler.toMechanicDtoList( mecanicos );
	}




}
