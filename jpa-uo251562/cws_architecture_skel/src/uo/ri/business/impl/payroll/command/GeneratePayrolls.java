package uo.ri.business.impl.payroll.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.IntervencionRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Intervencion;
import uo.ri.model.Payroll;

public class GeneratePayrolls implements Command <Integer>{
	
	private ContractRepository repoContract = Factory.repository.forContracts();
	private PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private IntervencionRepository repoIntervencion =  Factory.repository
			.forIntervencion();
	private int nominasGeneradas;



	public Integer execute() {

		Date fechaNominas = Dates.today();
		fechaNominas = Dates.subMonths(fechaNominas, 1);
		fechaNominas = Dates.lastDayOfMonth(fechaNominas);
		Date primeradia = Dates.firstDayOfMonth(fechaNominas);
		List<Contract> contracts = repoContract.buscarContratos(fechaNominas);
		for (Contract c : contracts) {
			
			List<Intervencion> intervenciones = repoIntervencion
					.findByMechanicIdBetweenDates(c.getId(),
							primeradia, fechaNominas);
			double contador = 0.0;
			for (Intervencion i : intervenciones) {

				contador += i.getImporte();
			}
			
			Payroll p = c.generarNomina(fechaNominas, contador);
			if (p != null) {
				repoPayrolls.add(p);
				nominasGeneradas++;
			}

		}
		return nominasGeneradas;
	}
	
}
