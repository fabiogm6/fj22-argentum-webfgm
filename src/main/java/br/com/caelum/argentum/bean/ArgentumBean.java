package br.com.caelum.argentum.bean;

import java.lang.reflect.Constructor;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.ChartModel;

import br.com.caelum.argentum.grafico.GeradorModeloGrafico;
import br.com.caelum.argentum.indicadores.Indicador;
import br.com.caelum.argentum.modelo.Candle;
import br.com.caelum.argentum.modelo.CandlestickFactory;
import br.com.caelum.argentum.modelo.IndicadorFechamento;
import br.com.caelum.argentum.modelo.MediaMovelSimples;
import br.com.caelum.argentum.modelo.Negociacao;
import br.com.caelum.argentum.modelo.SerieTemporal;
import br.com.caelum.argentum.ws.ClienteWebService;

/*
 � RequestScoped: � o escopo padr�o. A cada requisi��o um novo objeto do bean ser� criado;
 � ViewScoped: escopo da p�gina. Enquanto o usu�rio estiver na mesma p�gina, o bean � mantido. Ele
 s� � recriado quando acontece uma navega��o em s�, isto �, um bot�o abre uma p�gina diferente ou
 ainda quando acessamos novamente a p�gina atual.
 � SessionScoped: escopo de sess�o. Enquanto a sess�o com o servidor n�o expirar, o mesmo objeto do
 ArgentumBean atender� o mesmo cliente. Esse escopo � bastante usado, por exemplo, para manter o
 usu�rio logado em aplica��es.
 */

@ManagedBean
@ViewScoped
public class ArgentumBean {
	private List<Negociacao> negociacoes;
	private ChartModel modeloGrafico;
	private String nomeMedia;
	private String nomeIndicadorBase;

	public ArgentumBean() {
		System.out.println("======== 1) ArgentumBeam Construtor ========");
		// negociacoes = new ClienteWebService().getNegociacoes1();

		this.negociacoes = new ClienteWebService().getNegociacoes1();

		geraGrafico();

		System.out.println("4) ArgentumBeam");
	}

	public void geraGrafico() {
		System.out.println("PLOTANDO: " + nomeMedia + " de "
				+ nomeIndicadorBase);
		List<Candle> candles = new CandlestickFactory()
				.constroiCandles(negociacoes);
		SerieTemporal serie = new SerieTemporal(candles);

		GeradorModeloGrafico geradorGrafico = new GeradorModeloGrafico(serie,
				2, serie.getUltimaPosicao());
		// plota o indicador
		// old: geradorGrafico.plotaIndicador(new MediaMovelSimples(new
		// IndicadorFechamento()));
		// e depois no exercicio 11.8 da apostila
		geradorGrafico.plotaIndicador(defineIndicador());
		this.modeloGrafico = geradorGrafico.getModeloGrafico();
	}

	private Indicador defineIndicador() {
		try {
			// comportamento padr�o para quando o usu�rio n�o tiver escolhido
			if (nomeIndicadorBase == null || nomeMedia == null) 
				{System.out.println("Argentum Beam-defineIndicador-usu�rio n�o escolheu op��es");
				return new MediaMovelSimples(new IndicadorFechamento()); }
			System.out.println("ArgentumBeam-defineIndicador nomeIndicador: " + nomeIndicadorBase + " - nomeMedia: " + nomeMedia);
			
			String pacote = "br.com.caelum.argentum.indicadores.";
			
			Class<?> classeIndicadorBase = Class.forName(pacote	+ nomeIndicadorBase);
			Indicador indicadorBase = (Indicador) classeIndicadorBase.newInstance();
			Class<?> classeMedia = Class.forName(pacote + nomeMedia);
			Constructor<?> construtorMedia = classeMedia.getConstructor(Indicador.class);
			Indicador indicador = (Indicador) construtorMedia.newInstance(indicadorBase);
			System.out.println(">> ArgentumBeam defineIndicador "+indicador);
			return indicador;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Negociacao> getNegociacoes() {
		System.out.println("5) ArgentumBeam");
		return negociacoes;
	}

	public ChartModel getModeloGrafico() {
		return modeloGrafico;
	}

	public String getNomeMedia() {
		System.out.println("getNomeMedia");
		return nomeMedia;
	}

	public void setNomeMedia(String nomeMedia) {
		System.out.println("setNomeMedia");
		this.nomeMedia = nomeMedia;
	}

	public String getNomeIndicadorBase() {
		System.out.println("getNomeIndicadorBase");
		return nomeIndicadorBase;
	}

	public void setNomeIndicadorBase(String nomeIndicadorBase) {
		System.out.println("setNomeIndicadorBase");
		this.nomeIndicadorBase = nomeIndicadorBase;
	}
}
