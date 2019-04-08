package uo.ri.business;

public interface ServiceFactory {

	MechanicCrudService forMechanicCrudService();
	InvoiceService forInvoice();
	VehicleReceptionService forVehicleReception();
	CloseBreakdownService forClosingBreakdown();
	ContractTypeCrudService forContractTypeCrud();
	PayrollService forPayroll();
	ContractCrudService forContractCrud();
	ContractCategoryCrudService forContractCategoryCrud();
	PaymentMeanService forCash();
}
