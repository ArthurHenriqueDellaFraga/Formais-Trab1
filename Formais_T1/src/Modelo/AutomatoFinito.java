package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AutomatoFinito {
	
	static String fi = "@";
	static String nomePadraoEstado = "q";
	
	protected Set<String> estado;
	protected Set<String> alfabeto;
	protected HashMap<String, ArrayList<String>> tabelaDeTransicao;
	protected String estadoInicial;
	protected Set<String> estadoFinal;

	public AutomatoFinito(Set<String> _estado, Set<String> _alfabeto, HashMap<String, ArrayList<String>> _tabelaDeTransicao, String _estadoInicial, Set<String> _estadoFinal){
		estado = _estado;
		alfabeto = _alfabeto;
		tabelaDeTransicao = _tabelaDeTransicao;
		estadoInicial = _estadoInicial;
		estadoFinal = _estadoFinal;
	}
	
	public AutomatoFinito(AutomatoFinito automato){
		estado = automato.estado;
		alfabeto = automato.alfabeto;
		tabelaDeTransicao = automato.tabelaDeTransicao;
		estadoInicial = automato.estadoInicial;
		estadoFinal = automato.estadoFinal;
	}
	
	//FUNÇÕES
	
	//Retorna os estados alcançaveis por uma única transição a partir do estado argumento
	public HashSet<String> determinarEstadosDescendentes(String estado){
		HashSet<String> estadoDescendente = new HashSet<>();
		Iterator<String> alfabetoIterator = alfabeto.iterator();
		
		while(alfabetoIterator.hasNext()){
			String simbolo = alfabetoIterator.next();
			estadoDescendente.addAll(tabelaDeTransicao.get(new Transicao(estado, simbolo).hashMap()));
		}
		
		return estadoDescendente;
	}
	
	//Retorna os estados que alcançam por uma única transição o estado argumento
	public HashSet<String> determinarEstadosAscendentes(String _estado){
		HashSet<String> estadoAscendente = new HashSet<String>();
		
		Iterator<String> estadoIterator = estado.iterator();
		while(estadoIterator.hasNext()){
			String atual = estadoIterator.next();
			if(!atual.equalsIgnoreCase(_estado)){
				Iterator<String> alfabetoIterator = alfabeto.iterator();
				while(alfabetoIterator.hasNext()){
					String simbolo = alfabetoIterator.next();
					if(tabelaDeTransicao.get(new Transicao(atual, simbolo).hashMap()).contains(_estado)){
						estadoAscendente.add(atual);
					}
				}	
			}
		}
		return estadoAscendente;
	}

	public String toString() {
		return "T(M) = (K, Σ, δ, S, F)" +
				"\nK = " + estado +
				"\nΣ = " + alfabeto +
				"\nδ = " + tabelaDeTransicao + 
				"\nS = [" + estadoInicial + "]" +
				"\nF = " + estadoFinal;
	}

}
