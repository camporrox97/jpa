package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;

public interface PaymentMeanService {

	List<PaymentMeanDto> findPaymentMeansByClientId(Long id) throws BusinessException;

}
