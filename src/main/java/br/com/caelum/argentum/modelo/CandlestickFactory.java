package br.com.caelum.argentum.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CandlestickFactory {

	public Candle constroiCandleParaData(Calendar data,
			List<Negociacao> negociacoes) {

		double maximo = 0;
		double minimo = Double.MAX_VALUE;
				
		double volume = 0;		
	
		for (Negociacao negociacao : negociacoes) {						
			volume += negociacao.getVolume();

			double preco = negociacao.getPreco();
			if (preco > maximo) {
				maximo = preco;
			} else if (preco < minimo) {
				minimo = preco;
			}
		}

		double abertura = negociacoes.isEmpty() ? 0 : negociacoes.get(0).getPreco();
		double fechamento = negociacoes.isEmpty() ? 0 :
			negociacoes.get(negociacoes.size() - 1).getPreco();
		
//soh para checar o registro
//		String strdate2 = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//		if (data != null) {strdate2 = sdf.format(data.getTime());}
//		
//			System.out.println("CandlestickFactory - constroiCandleParaData: "+strdate2+" "+ minimo + " " + maximo + " ");	
//
		return new Candle(abertura, fechamento, minimo, maximo, volume,
				data);

	}

	public List<Candle> constroiCandles(List<Negociacao> todasNegociacoes) {
		
		Collections.sort(todasNegociacoes);
		
		List<Candle> candles = new ArrayList<Candle>();
		List<Negociacao> negociacoesDoDia = new ArrayList<Negociacao>();

		Calendar dataAtual = todasNegociacoes.get(0).getData();

		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		for (Negociacao negociacao : todasNegociacoes) {
			
			if (negociacao.getData().before(dataAtual)){
				throw new IllegalStateException("negociacao em ordem errada");
			}
			
			if (!negociacao.isMesmoDia(dataAtual)) {
				// soh para checar o registro
//				if (negociacao.getData() != null) {strdate = sdf.format(negociacao.getData().getTime());}			
//				System.out.println(">>>CandlestickFactory - constroiCandles >>> " + strdate + " " + negociacao.getPreco());
				// a cada quebra constroi o candle		
				
				
				criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);
				// zera a matriz
				negociacoesDoDia = new ArrayList<Negociacao>();
				dataAtual = negociacao.getData();
			}

			// soh para checar o registro
//			if (negociacao.getData() != null) {strdate = sdf.format(negociacao.getData().getTime());}			
//			System.out.println(">>>CandlestickFactory - constroiCandles "+strdate + " " + negociacao.getPreco());
			//
			negociacoesDoDia.add(negociacao);
		}
		
		criaEGuardaCandle(candles, negociacoesDoDia, dataAtual);

		System.out.println(">>>CandlestickFactory - constroiCandles - Fim");
		
		return candles;
	}

	private void criaEGuardaCandle(List<Candle> candles,
			List<Negociacao> negociacoesDoDia, Calendar dataAtual) {		
		Candle candleDoDia = constroiCandleParaData(dataAtual,negociacoesDoDia);
		candles.add(candleDoDia);
	}

}
