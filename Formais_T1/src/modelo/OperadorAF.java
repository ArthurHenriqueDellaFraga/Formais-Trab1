package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OperadorAF {

	private static OperadorAF instancia;

	private OperadorAF() {
	}

	public static OperadorAF getInstance() {
		if (instancia == null)
			instancia = new OperadorAF();

		return instancia;
	}

	// FUNCOES

	/*
	 * INTERSECCAO DE DOIS AUTOMATOS realizada atraves do complemento da uniao
	 * dos complementos dos automatos argumentos
	 */
	public AutomatoFinito interseccionar(AutomatoFinito automato1,
			AutomatoFinito automato2) {
		AutomatoFinito automatoComplemento1 = complementar(automato1);
		AutomatoFinito automatoComplemento2 = complementar(automato2);
		AutomatoFinito automatoUniao = unir(automatoComplemento1,
				automatoComplemento2);
		return complementar(automatoUniao);
	}

	/*
	 * UNIAO DE DOIS AUTOMATOS Retorna um novo automato que eh o resultado da
	 * uniao de dois automatos, podendo ser AFD ou AFND.
	 */
	public AutomatoFinito unir(AutomatoFinito automato1,
			AutomatoFinito automato2) {
		Set<String> alfabeto = new HashSet<String>();
		alfabeto.addAll(automato1.alfabeto);
		alfabeto.addAll(automato2.alfabeto);
		String estadoInicial = "S";
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<>();
		Set<String> estadoFinal = new HashSet<String>();
		ArrayList<String> estadoTemp = new ArrayList<String>();
		// Renomeia os estados do automato1 e automato2 e os adiciona na lista
		// do novo automato, caso seja final, adiciona na lista de estadoFinal.
		int numeroEstados = 0;
		ArrayList<String> estado1 = new ArrayList<String>();
		for (String atual : automato1.estado) {
			estado1.add(atual);
			String estadoAtual = AutomatoFinito.nomePadraoEstado + ""
					+ numeroEstados;
			estadoTemp.add(estadoAtual);
			numeroEstados++;
			if (automato1.estadoFinal.contains(atual)) {
				estadoFinal.add(estadoAtual);
			}
		}
		ArrayList<String> estado2 = new ArrayList<String>();
		for (String atual : automato2.estado) {
			estado2.add(atual);
			String estadoAtual = AutomatoFinito.nomePadraoEstado + ""
					+ numeroEstados;
			estadoTemp.add(estadoAtual);
			numeroEstados++;
			if (automato2.estadoFinal.contains(atual)) {
				estadoFinal.add(estadoAtual);
			}
		}
		estadoTemp.add(estadoInicial);
		// Percorre por todas as transicoes adicionando a nova tabela de
		// transicao com os novos nomes dos estados.
		for (String estadoAtual : automato1.estado) {
			for (String simbolo : automato1.alfabeto) {
				ArrayList<String> estadoDescendenteNovo = new ArrayList<String>();
				ArrayList<String> estadoDescendente = automato1.tabelaDeTransicao
						.get(new Transicao(estadoAtual, simbolo).hashMap());
				for (String atual : estadoDescendente) {
					estadoDescendenteNovo.add(estadoTemp.get(estado1
							.indexOf(atual)));
				}
				if (estadoAtual.equals(automato1.estadoInicial)) {
					tabelaDeTransicao.put(
							new Transicao(estadoInicial, simbolo).hashMap(),
							estadoDescendenteNovo);
				}
				tabelaDeTransicao.put(
						new Transicao(estadoTemp.get(estado1
								.indexOf(estadoAtual)), simbolo).hashMap(),
						estadoDescendenteNovo);
			}
		}
		for (String estadoAtual : automato2.estado) {
			for (String simbolo : automato2.alfabeto) {
				ArrayList<String> estadoDescendenteNovo = new ArrayList<String>();
				ArrayList<String> estadoDescendente = automato2.tabelaDeTransicao
						.get(new Transicao(estadoAtual, simbolo).hashMap());
				for (String atual : estadoDescendente) {
					estadoDescendenteNovo.add(estadoTemp.get(estado2
							.indexOf(atual) + automato1.estado.size()));
				}
				if (estadoAtual.equals(automato2.estadoInicial)) {
					String transicao = new Transicao(estadoInicial, simbolo)
							.hashMap();
					if (tabelaDeTransicao.containsKey(transicao)) {
						Set<String> estadoDescendenteTemp1 = new HashSet<String>();
						estadoDescendenteTemp1.addAll(estadoDescendenteNovo);
						estadoDescendenteTemp1.addAll(tabelaDeTransicao
								.get(transicao));
						tabelaDeTransicao.put(new Transicao(estadoInicial,
								simbolo).hashMap(), new ArrayList<String>(
								estadoDescendenteTemp1));
					} else {
						tabelaDeTransicao.put(new Transicao(estadoInicial,
								simbolo).hashMap(), estadoDescendenteNovo);
					}
				}
				tabelaDeTransicao.put(
						new Transicao(
								estadoTemp.get(estado2.indexOf(estadoAtual)
										+ automato1.estado.size()), simbolo)
								.hashMap(), estadoDescendenteNovo);
			}
		}
		Set<String> estado = new HashSet<String>();
		estado.addAll(estadoTemp);
		return new AutomatoFinito(estado, alfabeto, tabelaDeTransicao,
				estadoInicial, estadoFinal);
	}

	/*
	 * COMPLEMENTO DE UM AUTOMATO Salva todos os estados que nao sao finais de
	 * um automato, elimina todos os finais e faz com que os antigos nao finais
	 * se tornem finais.
	 */
	public AutomatoFinito complementar(AutomatoFinito automato) {
		Set<String> estadoNaoFinal = new HashSet<String>();
		estadoNaoFinal.addAll(automato.estado);
		estadoNaoFinal.removeAll(automato.estadoFinal);
		automato.estadoFinal.clear();
		automato.estadoFinal.addAll(estadoNaoFinal);
		return automato;
	}

	/*
	 * EQUIVALENCIA DE DOIS AUTOMATOS Retorna um inteiro contendo o motivo da
	 * diferenca de dois automatos: 0 = Equivalentes 1 = Alfabetos diferentes 2
	 * = Quantidade distintas de estados finais 3 = Transicoes mapeam para
	 * estados nao equivalentes
	 */
	public int determinarEquivalencia(AutomatoFinito automato1,
			AutomatoFinito automato2) {

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

		for (int i = 0; i < estadoParaVerificar1.size(); i++) {
			for (String simbolo : automato1.alfabeto) {
				try {
					String descendenteAtual1 = automato1.tabelaDeTransicao.get(
							new Transicao(estadoParaVerificar1.get(i), simbolo)
									.hashMap()).get(0);
					String descendenteAtual2 = automato2.tabelaDeTransicao.get(
							new Transicao(estadoParaVerificar2.get(i), simbolo)
									.hashMap()).get(0);

					if ((descendenteAtual1.equals(automato1.estadoInicial) ^ descendenteAtual2
							.equals(automato2.estadoInicial))
							|| (automato1.estadoFinal
									.contains(descendenteAtual1) ^ automato2.estadoFinal
									.contains(descendenteAtual2))
							|| (estadoParaVerificar1
									.contains(descendenteAtual1) ^ estadoParaVerificar2
									.contains(descendenteAtual2))) {
						return 3;
					}
					if (!estadoParaVerificar1.contains(descendenteAtual1)) {
						estadoParaVerificar1.add(descendenteAtual1);
						estadoParaVerificar2.add(descendenteAtual2);
					}
				} catch (NullPointerException npe) {
					return 3;
				}
			}
		}

		return 0;
	}
}