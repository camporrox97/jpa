package uo.ri.business.impl.paymentMean;

import java.util.List;

import uo.ri.business.PaymentMeanService;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.paymentMean.command.FindPaymentMeansByClientId;
import uo.ri.conf.Factory;

public class PaymentMeanServiceImpl implements PaymentMeanService{

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<PaymentMeanDto> findPaymentMeansByClientId(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return executor.execute(new FindPaymentMeansByClientId(id));
	}


}
