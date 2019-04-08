package uo.ri.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TTARJETASCREDITO")

public class TarjetaCredito extends MedioPago {

	@Column(unique = true)
	protected String numero;
	protected String tipo;
	@Temporal(TemporalType.TIMESTAMP)
	protected Date validez;

	TarjetaCredito() {

	}

	public TarjetaCredito(String numero) {
		this.numero = numero;
	}

	public TarjetaCredito(String numero, String tipo, Date fecha) {
		this(numero);
		this.tipo = tipo;
		this.validez = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getValidez() {

		return validez;

	}

	public void setValidez(Date validez) {
		this.validez = validez;
	}

	public String getNumero() {
		return numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo
				+ ", validez=" + validez + "]";
	}

	@Override
	public void pagar(double acumulado) {
		if (isValid())
			this.acumulado += acumulado;
		else
			throw new IllegalStateException("La tarjeta de credito expiró");
	}

	public boolean isValid() {
		if (new Date().getTime() > validez.getTime()) {
			return false;
		}
		return true;
	}

}
