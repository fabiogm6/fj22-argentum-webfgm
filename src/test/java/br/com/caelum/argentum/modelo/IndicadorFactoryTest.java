package br.com.caelum.argentum.modelo;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.argentum.indicadores.Indicador;

public class IndicadorFactoryTest {

	@Test
	public void testMontaAbertura() {
		String nomeIndicador = "IndicadorAbertura";
		IndicadorFactory indicadorFactory = new IndicadorFactory(nomeIndicador, null);
		
		Indicador indicador = indicadorFactory.getIndicador();
		
		Assert.assertTrue(indicador instanceof IndicadorAbertura);
	}
	
	@Test
	public void testMontaAberturaComMediaMovelSimples(){
		String nomeIndicador = "IndicadorAbertura";
		String nomeMedia = "MediaMovelSimples";
		
		IndicadorFactory indicadorFactory = new IndicadorFactory(nomeIndicador, nomeMedia);
		
		Indicador indicador = indicadorFactory.getIndicador();
		
		Assert.assertTrue(indicador instanceof MediaMovelSimples);
		
	}
}
