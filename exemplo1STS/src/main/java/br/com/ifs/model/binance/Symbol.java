package br.com.ifs.model.binance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Symbol {
	private String symbol;
	private String baseAsset;
	private String quoteAsset;
}
