package br.com.caelum.argentum.indicadores;

import br.com.caelum.argentum.modelo.SerieTemporal;


public class IndicadorAbertura implements Indicador {

	@Override
	public double calcula(int posicao, SerieTemporal serie) {
		System.out.println("IndicadorAbertura - Calcula "+posicao);
		return serie.getCandle(posicao).getAbertura();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("IndicadorAbertura - String");		
		return "Abertura";
	}
	
}



/*
public class IndicadorAbertura implements Indicador {
	@Override
	public double calcula(int posicao, SerieTemporal serie) {
		return serie.getCandle(posicao).getAbertura();
	}
	
	public String toString() {
		return "MMS de Fechamento";
		}
}
*/