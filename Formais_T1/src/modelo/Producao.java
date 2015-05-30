package modelo;

public class Producao {
	public String simboloTerminal;
	public String simboloNaoTerminal;
	
	public Producao(String _simboloTerminal, String _simboloNaoTerminal){
		simboloTerminal = _simboloTerminal;
		simboloNaoTerminal = _simboloNaoTerminal;
	}
	
	public Producao(String _simboloTerminal){
		simboloTerminal = _simboloTerminal;
	}
	
	public String hashMap() {
		String hash = simboloTerminal + simboloNaoTerminal;
		return hash;
	}
}
