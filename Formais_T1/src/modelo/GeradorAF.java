package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import modelo.util.ArvoreBinaria;
import modelo.util.Nodo;
import modelo.util.OperadorEnum;

public class GeradorAF {

	private static GeradorAF instancia;

	private GeradorAF() {
	}

	public static GeradorAF getInstance() {
		if (instancia == null)
			instancia = new GeradorAF();

		return instancia;
	}

	// FUNCOES
	
	public AutomatoFinito gerarAutomatoFinito(ExpressaoRegular expressao){
		ArvoreBinaria arvore = new ArvoreBinaria(gerarArvore(expressao.expressao));
		definirCostura(arvore.getRaiz());
		
		//completar
		
		return null;
	}
	
	private Nodo gerarArvore(String expressao){
		if(expressao != null){
			if(expressao.length() == 1){
				return new Nodo(expressao);
			}
			for(OperadorEnum operador : OperadorEnum.values()){
				String[] substring = operador.quebrarExpressao(expressao, operador);
				if(substring != null){
					Nodo[] filho = {gerarArvore(substring[0]), gerarArvore(substring[1])};
					return new Nodo(operador.identificacao, filho);
				}
			}
			
		}
		return null;
	}
	
	private void definirCostura(Nodo atual){
		if(atual.costura == null && atual.filho[1] == null){
			atual.costura = ArvoreBinaria.fi;
		}
		System.out.println(atual.chave);
		if(atual.costura !=null){
			System.out.println("    " + atual.costura.chave);
		}
		if(!atual.folha){
			Nodo filhoDaEsquerda = atual.filho[0];
			if(filhoDaEsquerda.filho[1] != null){
				Nodo netoDaEsquerdaDireita = filhoDaEsquerda.filho[1];
				while(netoDaEsquerdaDireita.filho[1] != null){
					netoDaEsquerdaDireita = netoDaEsquerdaDireita.filho[1];
				}
				netoDaEsquerdaDireita.costura = atual;			
			}
			else{
				filhoDaEsquerda.costura = atual;
			}
			
			definirCostura(filhoDaEsquerda);
			if(atual.filho[1] != null){
				definirCostura(atual.filho[1]);
			}
		}
	}

	/*
	 * GERADOR DE AUTOMATO A PARTIR DE UMA GR Interpreta as producoes da GR
	 * 'transformando-as' nas transicoes do AF
	 */
	public AutomatoFinito gerarAutomatoFinito(GramaticaRegular gramatica) {
		HashSet<String> estado = new HashSet<String>(
				gramatica.simboloNaoTerminal);
		HashSet<String> alfabeto = new HashSet<String>(
				gramatica.simboloTerminal);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();
		String estadoInicial = gramatica.simboloInicial;
		HashSet<String> estadoFinal = new HashSet<String>();

		for (String simboloNaoTerminal : gramatica.simboloNaoTerminal) {
			if (gramatica.regraDeProducao.containsKey(simboloNaoTerminal)) {
				for (Producao producao : gramatica.regraDeProducao
						.get(simboloNaoTerminal)) {
					String transicao = new Transicao(simboloNaoTerminal,
							producao.simboloTerminal).hashMap();

					if (producao.simboloNaoTerminal != null) {
						HashSet<String> conjuntoEstadoDestino = new HashSet<String>();
						conjuntoEstadoDestino.add(producao.simboloNaoTerminal);

						if (tabelaDeTransicao.containsKey(transicao)) {
							conjuntoEstadoDestino.addAll(tabelaDeTransicao
									.get(transicao));
						}
						tabelaDeTransicao.put(transicao, new ArrayList<String>(
								conjuntoEstadoDestino));
					} else {
						estadoFinal.addAll(tabelaDeTransicao.get(new Transicao(
								simboloNaoTerminal, producao.simboloTerminal)
								.hashMap()));
					}
				}
			}
		}

		return new AutomatoFinito(estado, alfabeto, tabelaDeTransicao,
				estadoInicial, estadoFinal);

	}

	/*
	 * COMPLETA UM AUTOMATO Verifica se a tabela de transicao contem uma
	 * transicao por um determinado simbolo a partir de um determinado estado.
	 * Se nao tiver, cria uma transicao por fi. Retorna um novo automato
	 * completo.
	 */
	public AutomatoFinito completar(AutomatoFinito automato) {
		for (String estado : automato.estado) {
			for (String simbolo : automato.alfabeto) {
				if (!automato.tabelaDeTransicao.containsKey(new Transicao(
						estado, simbolo).hashMap())) {
					ArrayList<String> estadoFi = new ArrayList<String>();
					estadoFi.add(AutomatoFinito.fi);
					automato.tabelaDeTransicao.put(new Transicao(estado,
							simbolo).hashMap(), estadoFi);
				}
			}
		}
		return automato;
	}
}