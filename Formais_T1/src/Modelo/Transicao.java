package Modelo;

public class Transicao {
	public String estado;
	public String simbolo;
	
	public Transicao(String _estado, String _simbolo){
		estado = _estado;
		simbolo = _simbolo;
	}
	
	public String hashMap() {
		String hash = estado + simbolo;
		return hash;
	}
}
