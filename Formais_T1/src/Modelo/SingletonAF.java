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
	
	//FUNCOES
	
	/*DETERMINIZACAO DE UM AUTOMATO QUE NAO POSSUA TRANSICOES POR EPSILON
	 * Determiniza um automato que nao possua epsilon transicoes.
	 */
	public AutomatoFinito determinizarTransicoes(AutomatoFinito automato){
		Set<String> alfabeto = new HashSet<String>();
			alfabeto.addAll(automato.alfabeto);
			
		Set<String> estado = new HashSet<>();
		Set<String> estadoFinal = new HashSet<>();
		
		String estadoInicial = automato.estadoInicial;
		
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<>();
		
		Set<ArrayList<String>> estadoParaDeterminizar = new HashSet<ArrayList<String>>();
		
		for(String estadoAtual : automato.estado){
			for(String simboloAtual : automato.alfabeto){
				ArrayList<String> estadoDestino = automato.tabelaDeTransicao.get(new Transicao(estadoAtual, simboloAtual).hashMap());
				if(estadoDestino.size() > 1){
					estadoParaDeterminizar.add(estadoDestino);
				}
				else{
					tabelaDeTransicao.put(new Transicao(estadoAtual, simboloAtual).hashMap(), estadoDestino);
				}
			}
		}
		
		Set<ArrayList<String>> estadoJaDeterminizado = new HashSet<ArrayList<String>>();
		
		while(estadoParaDeterminizar.size() >= 1){
		
			Set<ArrayList<String>> estadoTemp = new HashSet<ArrayList<String>>();
			estadoTemp.addAll(estadoParaDeterminizar);
			estadoTemp.removeAll(estadoJaDeterminizado);
			
			for(ArrayList<String> estadoConjunto : estadoTemp){
				String estadoAtual = estadoConjunto.toString().replaceAll(",", "").replaceAll(" ", "");

				for(String simboloAtual : automato.alfabeto){
					
					Set<String> estadoDestino = new HashSet<String>();
					
					for(String estadoDestinoAtual : estadoConjunto){
						estadoDestino.addAll(automato.tabelaDeTransicao.get(new Transicao(estadoDestinoAtual, simboloAtual).hashMap()));
						if(automato.estadoFinal.contains(estadoDestinoAtual)){
							estadoFinal.add(estadoAtual);
						}
					}
					estadoDestino.remove(AutomatoFinito.fi);
					ArrayList<String> estadoDestinoLista = new ArrayList<String>();
					estadoDestinoLista.addAll(estadoDestino);
					if((!estadoParaDeterminizar.contains(estadoDestino)) && estadoDestino.size() > 1){
						estadoParaDeterminizar.add(estadoDestinoLista);
					}

					tabelaDeTransicao.put(new Transicao(estadoAtual, simboloAtual).hashMap(), estadoDestinoLista);
				}
				
				estadoParaDeterminizar.remove(estadoConjunto);
				estadoJaDeterminizado.add(estadoConjunto);
				estado.add(estadoAtual);
			}
		}
		
		return new AutomatoFinito(estado, alfabeto, tabelaDeTransicao, estadoInicial, estadoFinal);
	}
	
	/* INTERSECCAO DE DOIS AUTOMATOS
	 * 
	 */
	public AutomatoFinito interseccionar(AutomatoFinito automato1, AutomatoFinito automato2){
		
		AutomatoFinito automatoComplemento1 = complementar(automato1);
		AutomatoFinito automatoComplemento2 = complementar(automato2);
		
		AutomatoFinito automatoUniao = unir(automatoComplemento1, automatoComplemento2);
		
		return complementar(automatoUniao);
	}
	
	/* UNIAO DE DOIS AUTOMATOS
	 * Retorna um novo automato que eh o resultado da uniao de dois automatos, podendo ser AFD ou AFND.
	 */
	public AutomatoFinito unir(AutomatoFinito automato1, AutomatoFinito automato2) {
		Set<String> alfabeto = new HashSet<String>();
			alfabeto.addAll(automato1.alfabeto);
			alfabeto.addAll(automato2.alfabeto);

		String estadoInicial = "S";

		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<>();


		Set<String> estadoFinal = new HashSet<String>();
		
		ArrayList<String> estadoTemp = new ArrayList<String>();		

		//Renomeia os estados do automato1 e automato2 e os adiciona na lista do novo automato, caso seja final, adiciona na lista de estadoFinal.
		int numeroEstados = 0;
		ArrayList<String> estado1 = new ArrayList<String>();
		
		for(String atual : automato1.estado){
			estado1.add(atual);
			String estadoAtual = AutomatoFinito.nomePadraoEstado + "" + numeroEstados;
			estadoTemp.add(estadoAtual);
			numeroEstados++;
			if(automato1.estadoFinal.contains(atual)){
				estadoFinal.add(estadoAtual);
			}
		}
		
		ArrayList<String> estado2 = new ArrayList<String>();
		
		for(String atual : automato2.estado){
			estado2.add(atual);
			String estadoAtual = AutomatoFinito.nomePadraoEstado + "" + numeroEstados;
			estadoTemp.add(estadoAtual);
			numeroEstados++;
			if(automato2.estadoFinal.contains(atual)){
				estadoFinal.add(estadoAtual);
			}
		}

		estadoTemp.add(estadoInicial);

		//Percorre por todas as transicoes adicionando a nova tabela de transicao com os novos nomes dos estados.
		for(String estadoAtual : automato1.estado){
			for(String simbolo : automato1.alfabeto){
				ArrayList<String> estadoDescendenteNovo = new ArrayList<String>();
				ArrayList<String> estadoDescendente = automato1.tabelaDeTransicao.get(new Transicao(estadoAtual, simbolo).hashMap());
				for(String atual : estadoDescendente){
					estadoDescendenteNovo.add(estadoTemp.get(estado1.indexOf(atual)));
				}
				if(estadoAtual.equals(automato1.estadoInicial)){
					tabelaDeTransicao.put(new Transicao(estadoInicial, simbolo).hashMap(), estadoDescendenteNovo);
				}
				tabelaDeTransicao.put(new Transicao(estadoTemp.get(estado1.indexOf(estadoAtual)), simbolo).hashMap(), estadoDescendenteNovo);
			}
		}
		
		for(String estadoAtual : automato2.estado){
			for(String simbolo : automato2.alfabeto){
				ArrayList<String> estadoDescendenteNovo = new ArrayList<String>();
				ArrayList<String> estadoDescendente = automato2.tabelaDeTransicao.get(new Transicao(estadoAtual, simbolo).hashMap());
				for(String atual : estadoDescendente){
					estadoDescendenteNovo.add(estadoTemp.get(estado2.indexOf(atual) + automato1.estado.size()));
				}
				if(estadoAtual.equals(automato2.estadoInicial)){
					String transicao = new Transicao(estadoInicial, simbolo).hashMap();
					if(tabelaDeTransicao.containsKey(transicao)){
						Set<String> estadoDescendenteTemp1 = new HashSet<String>();
						estadoDescendenteTemp1.addAll(estadoDescendenteNovo);
						estadoDescendenteTemp1.addAll(tabelaDeTransicao.get(transicao));
						tabelaDeTransicao.put(new Transicao(estadoInicial, simbolo).hashMap(), new ArrayList<String>(estadoDescendenteTemp1));	
					}
					else{
						tabelaDeTransicao.put(new Transicao(estadoInicial, simbolo).hashMap(), estadoDescendenteNovo);		
					}
				}
				tabelaDeTransicao.put(new Transicao(estadoTemp.get(estado2.indexOf(estadoAtual) + automato1.estado.size()), simbolo).hashMap(), estadoDescendenteNovo);
			}
		}
		
		Set<String> estado = new HashSet<String>();
		estado.addAll(estadoTemp);
		
		return new AutomatoFinito(estado, alfabeto, tabelaDeTransicao, estadoInicial, estadoFinal);
	}

	/* COMPLEMENTO DE UM AUTOMATO
	 * Salva todos os estados que nao sao finais de um automato, elimina todos os finais 
	 * e faz com que os antigos nao finais se tornem finais.
	 */
	public AutomatoFinito complementar(AutomatoFinito automato){
		Set<String> estadoNaoFinal = new HashSet<String>();
		
		estadoNaoFinal.addAll(automato.estado);
		estadoNaoFinal.removeAll(automato.estadoFinal);
		
		automato.estadoFinal.clear();
		automato.estadoFinal.addAll(estadoNaoFinal);
		
		return automato;
	}
		
	/*COMPLETA UM AUTOMATO
	 * Verifica se a tabela de transicao contem uma transicao por um determinado simbolo a partir de um determinado estado.
	 * Se nao tiver, cria uma transicao por fi. Retorna um novo automato completo.
	 */
	public AutomatoFinito completar(AutomatoFinito automato){
				
		for(String estado : automato.estado){
			for(String simbolo : automato.alfabeto){
				if(!automato.tabelaDeTransicao.containsKey(new Transicao(estado, simbolo).hashMap())){
					ArrayList<String> estadoFi = new ArrayList<String>();
					estadoFi.add(AutomatoFinito.fi);
					automato.tabelaDeTransicao.put(new Transicao(estado, simbolo).hashMap(), estadoFi);
				}
			}
		}
		
		return automato;
	}
	
	/*EQUIVALENCIA DE DOIS AUTOMATOS
	Retorna um inteiro contendo o motivo da diferenca de dois automatos:
		0 = Equivalentes
		1 = Alfabetos diferentes
		2 = Quantidade distintas de estados finais
		3 = Transicoes mapeam para estados nao equivalentes
	 */
	public int determinarEquivalencia(AutomatoFinito automato1, AutomatoFinito automato2) {
		
		if (!automato1.alfabeto.equals(automato2.alfabeto)) {
			return 1;
		}
		if (automato1.estadoFinal.size() != automato2.estadoFinal.size()) {
			return 2;
		}
		
		ArrayList<String> estadoParaVerificar1 = new ArrayList<String>();
		ArrayList<String> estadoParaVerificar2 = new ArrayList<String>();
		
		estadoParaVerificar1.add(automato1.estadoInicial);
		estadoParaVerificar2.add(automato2.estadoInicial);
		
		for(int i = 0; i < estadoParaVerificar1.size(); i++){
			for(String simbolo : automato1.alfabeto){
				try{
					String descendenteAtual1 = automato1.tabelaDeTransicao.get(new Transicao(estadoParaVerificar1.get(i), simbolo).hashMap()).get(0);
					String descendenteAtual2 = automato2.tabelaDeTransicao.get(new Transicao(estadoParaVerificar2.get(i), simbolo).hashMap()).get(0);
					
					if((descendenteAtual1.equals(automato1.estadoInicial) ^ descendenteAtual2.equals(automato2.estadoInicial))
							|| (automato1.estadoFinal.contains(descendenteAtual1) ^ automato2.estadoFinal.contains(descendenteAtual2))
							|| (estadoParaVerificar1.contains(descendenteAtual1) ^ estadoParaVerificar2.contains(descendenteAtual2))){
						return 3;
					}
					if(!estadoParaVerificar1.contains(descendenteAtual1)){
						estadoParaVerificar1.add(descendenteAtual1);
						estadoParaVerificar2.add(descendenteAtual2);		
					}
				}
				catch(NullPointerException npe){
					return 3;
				}
			}
		}
		
		return 0;
	}
	
	/*MINIMIZACAO DE UM AUTOMATO
	 * Elimina primeiramente os estados inalcancaveis, depois os mortos e por fim simplifica-o.
	 */
	public AutomatoFinito minimizar(AutomatoFinito automato){
		AutomatoFinito automatoAlcancavel = excluirEstadosInalcancaveis(new AutomatoFinito(automato));
		AutomatoFinito automatoVivo = excluirEstadosMortos(new AutomatoFinito(automatoAlcancavel));
		AutomatoFinito automatoSimples = simplificar(new AutomatoFinito(automatoVivo));
		
		return automatoSimples;
	}
	
	/*SIMPLICACAO DE UM AUTOMATO
	 * Construcao de um novo automato eliminando os estados equivalentes
	 */
	private AutomatoFinito simplificar(AutomatoFinito automato) {
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

	/*DETERMINACAO DE CLASSES DE EQUIVALENCIA
	 * Verificao e criacao das classes equivalentes entre estados.
	 */
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

	/*DETERMINACAO DE EQUIVALENCIA ENTRE DOIS ESTADOS
	 * Verificao da equivalencia entre dois estados em um automato.
	 */
	private boolean determinarEquivalencia(String estado1, String estado2,ArrayList<ArrayList<String>> listaDeClasseDeEquivalencia) {
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
	
	/*EXCLUSAO DE ESTADOS INALCANCAVEIS
	 * Exclusao de estados inalcancaveis do automato.
	 */
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

	/*DETERMINACAO DE ESTADOS INALCANCAVEIS
	 * Determinacao dos estados nao alcancaveis de um automato.
	 */
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
	
	/*EXCLUSAO DE ESTADOS MORTOS
	 * Exclusao de estados mortos do automato.
	 */
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
	
	/*DETERMINACAO DE ESTADOS MORTOS
	 * Determinacao dos estados que nao sao vivos de um automato.
	 */
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
	
	/*EXCLUIR TRANSICAO
	 * Remove uma transicao de uma tabela de transicao, substituindo o estado destino para FI.
	 */
	private void excluirTransicao(HashMap<String, ArrayList<String>> tabelaDeTransicao, Transicao transicao){
			tabelaDeTransicao.remove(transicao);
			ArrayList<String> destino = new ArrayList<String>();
			destino.add(AutomatoFinito.fi);
			tabelaDeTransicao.put(transicao.hashMap(), destino);
		}
		
}