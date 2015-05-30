package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GramaticaRegular {
	protected Set<String> simboloNaoTerminal;
	protected Set<String> simboloTerminal;
	protected HashMap<String, ArrayList<Producao>> regraDeProducao;
	protected String simboloInicial;

	public GramaticaRegular(Set<String> _simboloNaoTerminal, Set<String> _simboloTerminal, HashMap<String, ArrayList<Producao>> _regraDeProducao, String _simboloInicial){
		simboloNaoTerminal = _simboloNaoTerminal;
		simboloTerminal = _simboloTerminal;
		regraDeProducao = _regraDeProducao;
		simboloInicial = _simboloInicial;
	}
	
}
