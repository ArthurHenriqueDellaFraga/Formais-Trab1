package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SingletonAF {
	
	private static SingletonAF instancia;
	
	private SingletonAF(){
	}
	
	public static SingletonAF getInstance() {
		if (instancia == null)
			instancia = new SingletonAF();

		return instancia;
	}
	
	//FUNÇÕES
	
	public AutomatoFinito minimizar(AutomatoFinito automato){
		AutomatoFinito automatoAlcancavel = excluirEstadosInalcancaveis(new AutomatoFinito(automato));
		AutomatoFinito automatoVivo = excluirEstadosMortos(new AutomatoFinito(automatoAlcancavel));
		AutomatoFinito automatoSimples = simplificarAutomato(new AutomatoFinito(automatoVivo));
		
		return automatoSimples;
	}
	
	private AutomatoFinito simplificarAutomato(AutomatoFinito automato) {
		ArrayList<ArrayList<String>> listaDeClasseDeEquivalencia = determinarClassesDeEquivalencia(automato);
		ArrayList<String> estado = new ArrayList<String>();
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();
		String estadoInicial = "";
		Set<String> estadoFinal = new HashSet<String>();
		for (int i = 0; i < listaDeClasseDeEquivalencia.size(); i++) {
			ArrayList<String> classeDeEquivalencia = listaDeClasseDeEquivalencia
					.get(i);
			String nomeEstadoAtual = AutomatoFinito.nomePadraoEstado + i;
			estado.add(nomeEstadoAtual);
			if (automato.estadoFinal.contains(classeDeEquivalencia.get(0))) {
				estadoFinal.add(nomeEstadoAtual);
			}
			if (classeDeEquivalencia.contains(automato.estadoInicial)) {
				estadoInicial = nomeEstadoAtual;
			}
		}
		for (ArrayList<String> classeDeEquivalencia1 : listaDeClasseDeEquivalencia) {
			String estadoAntigo = classeDeEquivalencia1.get(0);
			String estadoNovo = estado.get(listaDeClasseDeEquivalencia
					.indexOf(classeDeEquivalencia1));
			for (String simbolo : automato.alfabeto) {
				String estadoAntigoDestino = automato.tabelaDeTransicao.get(
						new Transicao(estadoAntigo, simbolo).hashMap()).get(0);
				ArrayList<String> listaEstadoNovoDestino = new ArrayList<String>();
				if (!estadoAntigoDestino.equals(AutomatoFinito.fi)) {
					for (ArrayList<String> classeDeEquivalencia2 : listaDeClasseDeEquivalencia) {
						if (classeDeEquivalencia2.contains(estadoAntigoDestino)) {
							listaEstadoNovoDestino.add(estado
									.get(listaDeClasseDeEquivalencia
											.indexOf(classeDeEquivalencia2)));
						}
					}
				} else {
					listaEstadoNovoDestino.add(AutomatoFinito.fi);
				}
				tabelaDeTransicao.put(
						new Transicao(estadoNovo, simbolo).hashMap(),
						listaEstadoNovoDestino);
			}
		}
		return new AutomatoFinito(new HashSet<String>(estado),
				automato.alfabeto, tabelaDeTransicao, estadoInicial,
				estadoFinal);
	}

	private ArrayList<ArrayList<String>> determinarClassesDeEquivalencia(
			AutomatoFinito automato) {
		ArrayList<ArrayList<String>> listaDeClasseDeEquivalencia = new ArrayList<ArrayList<String>>();
		listaDeClasseDeEquivalencia.add(0, new ArrayList<String>());
		listaDeClasseDeEquivalencia.get(0).addAll(automato.estado);
		listaDeClasseDeEquivalencia.get(0).removeAll(automato.estadoFinal);
		listaDeClasseDeEquivalencia.add(1, new ArrayList<String>());
		listaDeClasseDeEquivalencia.get(1).addAll(automato.estadoFinal);
		boolean iterarNovamente = true;
		while (iterarNovamente) {
			iterarNovamente = false;
			ArrayList<ArrayList<String>> _listaDeClasseDeEquivalencia = new ArrayList<ArrayList<String>>(
					listaDeClasseDeEquivalencia);
			for (ArrayList<String> classeDeEquivalencia1 : _listaDeClasseDeEquivalencia) {
				ArrayList<String> _classeDeEquivalencia1 = new ArrayList<String>(
						classeDeEquivalencia1);
				for (String estado1 : _classeDeEquivalencia1) {
					boolean estadosEquivalentes = false;
					for (ArrayList<String> classeDeEquivalencia2 : listaDeClasseDeEquivalencia) {
						for (String estado2 : classeDeEquivalencia2) {
							if (!estado1.equals(estado2)) {
								if ((automato.estadoFinal.contains(estado1) && automato.estadoFinal
										.contains(estado2))
										|| (!automato.estadoFinal
												.contains(estado1) && !automato.estadoFinal
												.contains(estado2))) {
									estadosEquivalentes = true;
									for (String simbolo : automato.alfabeto) {
										String estado1Destino = automato.tabelaDeTransicao
												.get(new Transicao(estado1,
														simbolo).hashMap())
												.get(0);
										String estado2Destino = automato.tabelaDeTransicao
												.get(new Transicao(estado2,
														simbolo).hashMap())
												.get(0);
										estadosEquivalentes = determinarEquivalencia(
												estado1Destino, estado2Destino,
												_listaDeClasseDeEquivalencia);
										if (!estadosEquivalentes) {
											classeDeEquivalencia2
													.remove(estado1);
											break;
										}
									}
									if (!estadosEquivalentes) {
										break;
									}
								} else {
									break;
								}
							} else if (classeDeEquivalencia2.size() == 1) {
								estadosEquivalentes = true;
								break;
							}
						}
						if (estadosEquivalentes) {
							if (!classeDeEquivalencia2.contains(estado1)) {
								classeDeEquivalencia2.add(estado1);
							}
							break;
						}
					}
					if (!estadosEquivalentes) {
						ArrayList<String> novaClasseDeEquivalencia = new ArrayList<String>();
						novaClasseDeEquivalencia.add(estado1);
						listaDeClasseDeEquivalencia
								.add(novaClasseDeEquivalencia);
						iterarNovamente = true;
					}
				}
			}
		}
		return listaDeClasseDeEquivalencia;
	}

	private boolean determinarEquivalencia(String estado1, String estado2,
			ArrayList<ArrayList<String>> listaDeClasseDeEquivalencia) {
		if (!estado1.equals(AutomatoFinito.fi)
				&& !estado2.equals(AutomatoFinito.fi)) {
			for (ArrayList<String> classeDeEquivalencia : listaDeClasseDeEquivalencia) {
				if ((classeDeEquivalencia.contains(estado1) || classeDeEquivalencia
						.contains(estado2))) {
					if (classeDeEquivalencia.contains(estado1)
							&& classeDeEquivalencia.contains(estado2)) {
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	private AutomatoFinito excluirEstadosInalcancaveis(AutomatoFinito automato){
		Set<String> estadoInalcancavel = new HashSet<String>(determinarEstadosInalcancaveis(automato));
		Iterator<String> estadoIterator = automato.estado.iterator();
		
		
		while(estadoIterator.hasNext()){
			String atual = estadoIterator.next();
			
			if(estadoInalcancavel.contains(atual)){
				Iterator<String> alfabetoIterator = automato.alfabeto.iterator();
				while(alfabetoIterator.hasNext()){
					automato.tabelaDeTransicao.remove(new Transicao(atual, alfabetoIterator.next()).hashMap());									
				}
			}	
		}
		
		automato.estado.removeAll(estadoInalcancavel);
		return automato;
	}

		private Set<String> determinarEstadosInalcancaveis(AutomatoFinito automato){
			Set<String> estadoAlcancado = new HashSet<String>();
			estadoAlcancado.add(automato.estadoInicial);
			Set<String> estadoVerificado = new HashSet<String>();
			Set<String> estadoParaVerificar = new HashSet<String>();
			boolean alcancadoNovosEstados = true;
			
			while(alcancadoNovosEstados){
				int quantidadeEstadosAlcancados = estadoAlcancado.size();
				
				estadoParaVerificar.addAll(estadoAlcancado);
				estadoParaVerificar.removeAll(estadoVerificado);
				
				Iterator<String> estadoParaVerificarIterator = estadoParaVerificar.iterator();
				while(estadoParaVerificarIterator.hasNext()){
					String atual = estadoParaVerificarIterator.next();
					estadoAlcancado.addAll(automato.determinarEstadosDescendentes(atual));
					estadoVerificado.add(atual);
				}
				if(estadoAlcancado.size() == quantidadeEstadosAlcancados){
					alcancadoNovosEstados = false;
				}
			}
			
			Set<String> estadoInalcancavel = new HashSet<String>(automato.estado);
			estadoInalcancavel.removeAll(estadoAlcancado);
			estadoInalcancavel.remove(AutomatoFinito.fi);
	
			return estadoInalcancavel;
	}
	
	private AutomatoFinito excluirEstadosMortos(AutomatoFinito automato){
		Set<String> estadoMorto = new HashSet<String>(determinarEstadosMortos(automato));
		Iterator<String> estadoIterator = automato.estado.iterator();
		
		while(estadoIterator.hasNext()){
			String atual = estadoIterator.next();
			
			if(estadoMorto.contains(atual)){
				Iterator<String> alfabetoIterator = automato.alfabeto.iterator();
				while(alfabetoIterator.hasNext()){
					automato.tabelaDeTransicao.remove(new Transicao(atual, alfabetoIterator.next()).hashMap());									
				}
			}
			else{
				Iterator<String> alfabetoIterator = automato.alfabeto.iterator();
				while(alfabetoIterator.hasNext()){
					Transicao transicao = new Transicao(atual, alfabetoIterator.next());
					if(estadoMorto.containsAll(automato.tabelaDeTransicao.get(transicao.hashMap()))){
						excluirTransicao(automato.tabelaDeTransicao, transicao);
					}
				}
			}
		}
		
		automato.estado.removeAll(estadoMorto);
		return automato;
	}
	
	private Set<String> determinarEstadosMortos(AutomatoFinito automato){
		Set<String> estadoVivo = new HashSet<String>();
		estadoVivo.addAll(automato.estadoFinal);
		Set<String> estadoVerificado = new HashSet<String>();
		Set<String> estadoParaVerificar = new HashSet<String>();
		boolean alcancadoNovosEstados = true;
		
		while(alcancadoNovosEstados){
			int quantidadeEstadosAlcancados = estadoVivo.size();
			
			estadoParaVerificar.addAll(estadoVivo);
			estadoParaVerificar.removeAll(estadoVerificado);
			
			Iterator<String> estadoParaVerificarIterator = estadoParaVerificar.iterator();
			while(estadoParaVerificarIterator.hasNext()){
				String atual = estadoParaVerificarIterator.next();
				estadoVivo.addAll(automato.determinarEstadosAscendentes(atual));
				estadoVerificado.add(atual);
			}
			if(estadoVivo.size() == quantidadeEstadosAlcancados){
				alcancadoNovosEstados = false;
			}
		}
		Set<String> estadoMorto = new HashSet<String>(automato.estado);
		estadoMorto.removeAll(estadoVivo);
		estadoMorto.remove(AutomatoFinito.fi);
		
		return estadoMorto;
	}
	
		private void excluirTransicao(HashMap<String, ArrayList<String>> tabelaDeTransicao, Transicao transicao){
			tabelaDeTransicao.remove(transicao);
			ArrayList<String> destino = new ArrayList<String>();
			destino.add("@");
			tabelaDeTransicao.put(transicao.hashMap(), destino);
		}
		
}
