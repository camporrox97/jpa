package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class UpdateMechanic  implements Command <Void> {

	private MechanicDto dto;
	private MecanicoRepository repo = Factory.repository.forMechanic();


	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {

		Mecanico m = repo.findById(dto.id);
		BusinessCheck.isNotNull(m,"mecanico no existe");
		m.setNombre( dto.name );
		m.setApellidos( dto.surname );
		return null;
		
	}

}
