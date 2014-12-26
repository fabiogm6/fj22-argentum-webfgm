package br.com.caelum.argentum.indicadores;

import br.com.caelum.argentum.modelo.SerieTemporal;

public class IndicadorFechamento implements Indicador {

	@Override
	public double calcula(int posicao, SerieTemporal serie) {
		return serie.getCandle(posicao).getFechamento();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Fechamento";
	}
	
}


/*
public class IndicadorFechamento implements Indicador {
	public double calcula(int posicao, SerieTemporal serie) {
		return serie.getCandle(posicao).getFechamento();
	}
	
	public String toString() {
		return "MMS de Fechamento";
		}
	
}
*/