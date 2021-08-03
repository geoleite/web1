package br.com.ifs.model.binance;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjBinance {

	private long serverTime;
	private List<Symbol> symbols;
}
