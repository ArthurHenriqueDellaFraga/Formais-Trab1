package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DeterminizadorAF {

	private static DeterminizadorAF instancia;

	private DeterminizadorAF() {
	}

	public static DeterminizadorAF getInstance() {
		if (instancia == null)
			instancia = new DeterminizadorAF();

		return instancia;
	}

	// FUNCOES

	/*
	 * DETERMINIZACAO DO AUTOMATO
	 * Avalia as transicoes que mapeiam pra multiplos estados ou que são realizadas a partir do simbolo epsilon e reconstroi o automato eliminando-as, mantendo-o
	 * equivalente
	 */
	public AutomatoFinito determinizar(AutomatoFinito automato) {
		AutomatoFinito automatoEpsilonDeterminizado = determinizarEpsilonTransicoes(new AutomatoFinito(
				automato));
		AutomatoFinito automatoDeterminizado = determinizarTransicoes(new AutomatoFinito(
				automatoEpsilonDeterminizado));

		return automatoDeterminizado;
	}

	/*
	 * DETERMINIZACAO DAS EPSILON TRANSICOES
	 * Elimina todas as transicoes realizadas pelo simbolo epsilon, mantendo o automato equivalente
	 */
	private AutomatoFinito determinizarEpsilonTransicoes(AutomatoFinito automato) {
		HashMap<String, ArrayList<String>> _tabelaDeTransicao = new HashMap<String, ArrayList<String>>(
				automato.tabelaDeTransicao);
		for (String estado1 : automato.estado) {
			String transicaoEpsilon = new Transicao(estado1,
					AutomatoFinito.epsilon).hashMap();

			while (_tabelaDeTransicao.containsKey(transicaoEpsilon)) {
				ArrayList<String> _listaEstado1EpsilonDestino = new ArrayList<String>(
						_tabelaDeTransicao.get(transicaoEpsilon));
				for (String estado2 : _listaEstado1EpsilonDestino) {
					if (!estado2.equals(AutomatoFinito.fi)) {
						for (String simbolo : automato.alfabeto) {
							String transicao1 = new Transicao(estado1, simbolo)
									.hashMap();
							String transicao2 = new Transicao(estado2, simbolo)
									.hashMap();
							if (_tabelaDeTransicao.containsKey(transicao1)
									&& _tabelaDeTransicao
											.containsKey(transicao2)) {
								HashSet<String> _conjuntoEstado1Destino = new HashSet<String>(
										_tabelaDeTransicao.get(transicao1));

								_conjuntoEstado1Destino
										.addAll(_tabelaDeTransicao
												.get(transicao2));

								if (_conjuntoEstado1Destino.size() > 1) {
									_conjuntoEstado1Destino
											.remove(AutomatoFinito.fi);
								}

								_tabelaDeTransicao.put(transicao1,
										new ArrayList<String>(
												_conjuntoEstado1Destino));
							}
						}
					}
					_tabelaDeTransicao.get(transicaoEpsilon).remove(estado2);
				}
				if (_tabelaDeTransicao.get(transicaoEpsilon).size() == 0) {
					_tabelaDeTransicao.remove(transicaoEpsilon);
				}
			}
		}
		HashSet<String> _alfabeto = new HashSet<String>(automato.alfabeto);
		_alfabeto.remove(AutomatoFinito.epsilon);

		return new AutomatoFinito(automato.estado, _alfabeto,
				_tabelaDeTransicao, automato.estadoInicial,
				automato.estadoFinal);
	}

	/*
	 * DETERMINIZACAO DE UM AUTOMATO QUE NAO POSSUA TRANSICOES POR EPSILON
	 * Determiniza um automato que nao possua epsilon transicoes.
	 */
	public AutomatoFinito determinizarTransicoes(AutomatoFinito automato) {
		Set<String> alfabeto = new HashSet<String>();
		alfabeto.addAll(automato.alfabeto);
		Set<String> estado = new HashSet<>();
		Set<String> estadoFinal = new HashSet<>();
		String estadoInicial = automato.estadoInicial;
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<>();
		Set<ArrayList<String>> estadoParaDeterminizar = new HashSet<ArrayList<String>>();
		for (String estadoAtual : automato.estado) {
			for (String simboloAtual : automato.alfabeto) {
				ArrayList<String> estadoDestino = automato.tabelaDeTransicao
						.get(new Transicao(estadoAtual, simboloAtual).hashMap());
				if (estadoDestino.size() > 1) {
					estadoParaDeterminizar.add(estadoDestino);
				} else {
					tabelaDeTransicao.put(new Transicao(estadoAtual,
							simboloAtual).hashMap(), estadoDestino);
				}
			}
		}
		Set<ArrayList<String>> estadoJaDeterminizado = new HashSet<ArrayList<String>>();
		while (estadoParaDeterminizar.size() >= 1) {
			Set<ArrayList<String>> estadoTemp = new HashSet<ArrayList<String>>();
			estadoTemp.addAll(estadoParaDeterminizar);
			estadoTemp.removeAll(estadoJaDeterminizado);
			for (ArrayList<String> estadoConjunto : estadoTemp) {
				String estadoAtual = estadoConjunto.toString()
						.replaceAll(",", "").replaceAll(" ", "");
				for (String simboloAtual : automato.alfabeto) {
					Set<String> estadoDestino = new HashSet<String>();
					for (String estadoDestinoAtual : estadoConjunto) {
						estadoDestino.addAll(automato.tabelaDeTransicao
								.get(new Transicao(estadoDestinoAtual,
										simboloAtual).hashMap()));
						if (automato.estadoFinal.contains(estadoDestinoAtual)) {
							estadoFinal.add(estadoAtual);
						}
					}
					estadoDestino.remove(AutomatoFinito.fi);
					ArrayList<String> estadoDestinoLista = new ArrayList<String>();
					estadoDestinoLista.addAll(estadoDestino);
					if ((!estadoParaDeterminizar.contains(estadoDestino))
							&& estadoDestino.size() > 1) {
						estadoParaDeterminizar.add(estadoDestinoLista);
					}
					tabelaDeTransicao.put(new Transicao(estadoAtual,
							simboloAtual).hashMap(), estadoDestinoLista);
				}
				estadoParaDeterminizar.remove(estadoConjunto);
				estadoJaDeterminizado.add(estadoConjunto);
				estado.add(estadoAtual);
			}
		}
		return new AutomatoFinito(estado, alfabeto, tabelaDeTransicao,
				estadoInicial, estadoFinal);
	}

}