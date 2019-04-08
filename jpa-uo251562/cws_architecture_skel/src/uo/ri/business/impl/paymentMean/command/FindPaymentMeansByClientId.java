package uo.ri.business.impl.paymentMean.command;

import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.conf.Factory;
import uo.ri.model.MedioPago;

public class FindPaymentMeansByClientId implements Command <List<PaymentMeanDto>> {
	
	private Long id;
	
	public FindPaymentMeansByClientId (Long id) {
		this.id = id;
	}
	

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		
		BusinessCheck.isNotNull(id, "El id esta vacio");

		List<MedioPago> mps = Factory.repository.forMedioPago()
				.findByClientId(id);

		return DtoAssembler.toPaymentMeanDtoList(mps);
	}



}
