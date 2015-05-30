package modelo.util;

public class Nodo {

	public String chave;
	public Nodo[] filho = new Nodo[2];
	public Nodo costura;
	public boolean folha;

	
	public Nodo(String _chave) {
		chave = _chave;
		costura = null;
		folha = true;
	}
	
	public Nodo(String _chave, Nodo[] _filho){
		chave = _chave;
		filho = _filho;
		costura = null;
		folha = false;
	}

}
