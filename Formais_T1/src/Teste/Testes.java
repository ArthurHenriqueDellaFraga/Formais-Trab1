package Teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.AutomatoFinito;
import Modelo.GramaticaRegular;
import Modelo.Producao;
import Modelo.SingletonAF;
import Modelo.Transicao;

public class Testes {

	public SingletonAF singletonAF = SingletonAF.getInstance();

	public Testes() {

	}

	public void executarTestesMinimizacao() {
		for (int i = 1; i < 10; i++) {
			System.out.println("----------- CDT " + i + " INICIO -----------");

			switch (i) {
			case 1:
				System.out.println(cdtMinimizacao1());
				break;

			case 2:
				System.out.println(cdtMinimizacao2());
				break;

			case 3:
				System.out.println(cdtMinimizacao3());
				break;

			case 4:
				System.out.println(cdtMinimizacao4());
				break;

			case 5:
				System.out.println(cdtMinimizacao5());
				break;

			case 6:
				System.out.println(cdtMinimizacao6());
				break;

			case 7:
				System.out.println(cdtMinimizacao7());
				break;

			case 8:
				System.out.println(cdtMinimizacao8());
				break;

			case 9:
				System.out.println(cdtMinimizacao9());
				break;
			}

			System.out.println("----------- CDT " + i + " FIM -----------");
		}
	}

	public void cdtGramaticaParaAutomato(){
		
		String[] simboloNaoTerminal1 = {"S", "A", "B"};
		Set<String> simboloNaoTerminal = new HashSet<String>(criarConjunto(simboloNaoTerminal1));
		
		String[] simboloTerminal1 = {"a", "b"};
		Set<String> simboloTerminal = new HashSet<String>(criarConjunto(simboloTerminal1));
		
		HashMap<String, ArrayList<Producao>> regraDeProducao = new HashMap<String, ArrayList<Producao>>();
			ArrayList<Producao> producao = new ArrayList<Producao>();
				producao.add(new Producao("a", "A"));
				producao.add(new Producao("b", "B"));
				producao.add(new Producao("a"));
				producao.add(new Producao("b"));
			regraDeProducao.put("S", new ArrayList<Producao>(producao));
				producao.clear();
				producao.add(new Producao("a", "A"));
				producao.add(new Producao("a"));
			regraDeProducao.put("A", new ArrayList<Producao>(producao));
				producao.clear();
				producao.add(new Producao("b", "B"));
				producao.add(new Producao("b"));
			regraDeProducao.put("B", new ArrayList<Producao>(producao));
		
		String simboloInicial = "S";	
			
		GramaticaRegular gramatica = new GramaticaRegular(simboloNaoTerminal, simboloTerminal, regraDeProducao, simboloInicial);
		
		System.out.println(singletonAF.gerarAutomatoFinito(gramatica).toString());
	}
	
	public void cdtDeterminizacaoEpsilon1() {

		// CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = { "q0", "q1", "q2", "q3", "q4", "q5",
				AutomatoFinito.fi, AutomatoFinito.fi, AutomatoFinito.fi,
				AutomatoFinito.fi, AutomatoFinito.fi, AutomatoFinito.fi,
				AutomatoFinito.fi, AutomatoFinito.fi };
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));

		String[] alfabeto1 = { "a", "b", AutomatoFinito.epsilon };
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));

		String estadoInicial = "q0";

		String[] estadoFinal1 = { "q5" };
		Set<String> estadoFinal = new HashSet<String>(
				criarConjunto(estadoFinal1));

		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(),
				encapsulador.get(3));
		tabelaDeTransicao.put(
				new Transicao("q0", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(0));

		tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(),
				encapsulador.get(6));
		tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(),
				encapsulador.get(0));
		tabelaDeTransicao.put(
				new Transicao("q1", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(1));

		tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(),
				encapsulador.get(5));
		tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(),
				encapsulador.get(7));
		tabelaDeTransicao.put(
				new Transicao("q2", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(
				new Transicao("q2", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(3));

		tabelaDeTransicao.put(new Transicao("q3", "a").hashMap(),
				encapsulador.get(5));
		tabelaDeTransicao.put(new Transicao("q3", "b").hashMap(),
				encapsulador.get(8));
		tabelaDeTransicao.put(
				new Transicao("q3", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(4));

		tabelaDeTransicao.put(new Transicao("q4", "a").hashMap(),
				encapsulador.get(5));
		tabelaDeTransicao.put(new Transicao("q4", "b").hashMap(),
				encapsulador.get(9));
		tabelaDeTransicao.put(
				new Transicao("q4", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(4));

		tabelaDeTransicao.put(new Transicao("q5", "a").hashMap(),
				encapsulador.get(10));
		tabelaDeTransicao.put(new Transicao("q5", "b").hashMap(),
				encapsulador.get(3));
		tabelaDeTransicao.put(
				new Transicao("q5", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(5));

		tabelaDeTransicao.put(new Transicao(AutomatoFinito.fi, "a").hashMap(),
				encapsulador.get(11));
		tabelaDeTransicao.put(new Transicao(AutomatoFinito.fi, "b").hashMap(),
				encapsulador.get(12));
		tabelaDeTransicao.put(new Transicao(AutomatoFinito.fi,
				AutomatoFinito.epsilon).hashMap(), encapsulador.get(13));

		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto,
				tabelaDeTransicao, estadoInicial, estadoFinal);

		// DETERMINIZAR EPSILON TRANSICOES AUTOMATO ORIGINAL
		AutomatoFinito automatoEpsilonDeterminizado = singletonAF
				.determinizarEpsilonTransicoes(automato);

		System.out.println(automatoEpsilonDeterminizado.toString());
	}

	public void cdtDeterminizacaoEpsilon2() {
		String[] estado1 = { "q0", "q1", "q2", AutomatoFinito.fi,
				AutomatoFinito.fi, AutomatoFinito.fi };
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));

		String[] alfabeto1 = { "a", "b", AutomatoFinito.epsilon };
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));

		String estadoInicial = "q0";

		String[] estadoFinal1 = { "q2" };
		Set<String> estadoFinal = new HashSet<String>(
				criarConjunto(estadoFinal1));

		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(),
				encapsulador.get(3));
		tabelaDeTransicao.put(
				new Transicao("q0", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(0));

		tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(),
				encapsulador.get(4));
		tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(),
				encapsulador.get(5));
		tabelaDeTransicao.put(
				new Transicao("q1", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(2));

		tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(
				new Transicao("q2", AutomatoFinito.epsilon).hashMap(),
				encapsulador.get(2));

		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto,
				tabelaDeTransicao, estadoInicial, estadoFinal);

		System.out.println(automato.toString());

		// DETERMINIZAR EPSILON TRANSICOES AUTOMATO ORIGINAL
		AutomatoFinito automatoEpsilonDeterminizado = singletonAF
				.determinizarEpsilonTransicoes(automato);

		System.out.println(automatoEpsilonDeterminizado.toString() + "\n");
	}

	// 3 estados - 2 finais equivalentes - Sem fi
	public int cdtMinimizacao1() {

		// CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = { "q0", "q1", "q2" };
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));

		String[] alfabeto1 = { "a", "b" };
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));

		String estadoInicial = "q0";

		String[] estadoFinal1 = { "q0", "q2" };
		Set<String> estadoFinal = new HashSet<String>(
				criarConjunto(estadoFinal1));

		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(),
				encapsulador.get(0));
		tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(),
				encapsulador.get(2));

		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto,
				tabelaDeTransicao, estadoInicial, estadoFinal);

		// CRIACAO DO AUTOMATO ESPERADO
		String[] estado2 = { "q0", "q1" };
		Set<String> estado3 = new HashSet<String>(criarConjunto(estado2));

		String[] alfabeto2 = { "a", "b" };
		Set<String> alfabeto3 = new HashSet<String>(criarConjunto(alfabeto2));

		String estadoInicial3 = "q0";

		String[] estadoFinal2 = { "q0" };
		Set<String> estadoFinal3 = new HashSet<String>(
				criarConjunto(estadoFinal2));

		ArrayList<ArrayList<String>> encapsulador2 = criarLista(estado2);
		HashMap<String, ArrayList<String>> tabelaDeTransicao3 = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao3.put(new Transicao("q0", "a").hashMap(),
				encapsulador2.get(1));
		tabelaDeTransicao3.put(new Transicao("q1", "a").hashMap(),
				encapsulador2.get(0));
		tabelaDeTransicao3.put(new Transicao("q0", "b").hashMap(),
				encapsulador2.get(0));
		tabelaDeTransicao3.put(new Transicao("q1", "b").hashMap(),
				encapsulador2.get(1));

		AutomatoFinito automatoEsperado = new AutomatoFinito(estado3,
				alfabeto3, tabelaDeTransicao3, estadoInicial3, estadoFinal3);

		// MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);

		int comparacao = singletonAF.determinarEquivalencia(automatoEsperado,
				automatoMinimizado);
		return comparacao;
	}

	// 5 estados - 2 nao finais equivalentes - Sem fi
	public String cdtMinimizacao2() {

		// CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = { "q0", "q1", "q2", "q3", "q4", "q5", "q6" };
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));

		String[] alfabeto1 = { "a", "b" };
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));

		String estadoInicial = "q0";

		String[] estadoFinal1 = { "q0", "q2" };
		Set<String> estadoFinal = new HashSet<String>(
				criarConjunto(estadoFinal1));

		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q3", "a").hashMap(),
				encapsulador.get(4));
		tabelaDeTransicao.put(new Transicao("q4", "a").hashMap(),
				encapsulador.get(4));
		tabelaDeTransicao.put(new Transicao("q5", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q6", "a").hashMap(),
				encapsulador.get(6));
		tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(),
				encapsulador.get(3));
		tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(),
				encapsulador.get(6));
		tabelaDeTransicao.put(new Transicao("q3", "b").hashMap(),
				encapsulador.get(0));
		tabelaDeTransicao.put(new Transicao("q4", "b").hashMap(),
				encapsulador.get(0));
		tabelaDeTransicao.put(new Transicao("q5", "b").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q6", "b").hashMap(),
				encapsulador.get(6));

		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto,
				tabelaDeTransicao, estadoInicial, estadoFinal);

		// MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);

		System.out.println(automatoMinimizado.toString());

		String comparacao = "";
		return comparacao;
	}

	// 3 estados - 2 nao finais equivalentes - Com fi
	public String cdtMinimizacao3() {

		// CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = { "q0", "q1", "q2" };
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));

		String[] alfabeto1 = { "a", "b" };
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));

		String estadoInicial = "q0";

		String[] estadoFinal1 = { "q0", "q2" };
		Set<String> estadoFinal = new HashSet<String>(
				criarConjunto(estadoFinal1));

		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(),
				encapsulador.get(2));
		tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(),
				encapsulador.get(0));
		tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(),
				encapsulador.get(1));
		tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(),
				encapsulador.get(2));

		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto,
				tabelaDeTransicao, estadoInicial, estadoFinal);

		// CRIAÇÃO DO AUTOMATO ESPERADO
		String[] estado2 = { "q0", "q1" };
		Set<String> estado3 = new HashSet<String>(criarConjunto(estado2));

		String[] alfabeto2 = { "a", "b" };
		Set<String> alfabeto3 = new HashSet<String>(criarConjunto(alfabeto2));

		String estadoInicial3 = "q0";

		String[] estadoFinal2 = { "q0" };
		Set<String> estadoFinal3 = new HashSet<String>(
				criarConjunto(estadoFinal2));

		ArrayList<ArrayList<String>> encapsulador2 = criarLista(estado2);
		HashMap<String, ArrayList<String>> tabelaDeTransicao3 = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao3.put(new Transicao("q0", "a").hashMap(),
				encapsulador2.get(1));
		tabelaDeTransicao3.put(new Transicao("q1", "a").hashMap(),
				encapsulador2.get(0));
		tabelaDeTransicao3.put(new Transicao("q0", "b").hashMap(),
				encapsulador2.get(0));
		tabelaDeTransicao3.put(new Transicao("q1", "b").hashMap(),
				encapsulador2.get(1));

		AutomatoFinito automatoEsperado = new AutomatoFinito(estado3,
				alfabeto3, tabelaDeTransicao3, estadoInicial3, estadoFinal3);

		// MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);

		return "";
	}

	// 5 estados - 2 finais equivalentes - Com fi
	public String cdtMinimizacao4() {
		return "Oi";
	}

	// 3 estados - 2 finais equivalentes - 1 inalcancavel - Sem fi
	public String cdtMinimizacao5() {
		return "Oi";
	}

	// 5 estados - 2 nao finais equivalentes - 1 inalvancavel - Com fi
	public String cdtMinimizacao6() {
		return "Oi";
	}

	// 3 estados - 2 nao finais equivalentes - 1 morto - Sem fi
	public String cdtMinimizacao7() {
		return "Oi";
	}

	// 5 estados - 2 finais equivalentes - 1 morto - Com fi
	public String cdtMinimizacao8() {
		return "Oi";
	}

	// 12 estados 6 simbolos - 2 pares de finais equivalentes - 2 pares de nao
	// finais equivalentes - 2 morto - 2 Inalcancavel - 2 transicao por fi - Com
	// fi
	public String cdtMinimizacao9() {
		return "Oi";
	}

	public Set<String> criarConjunto(String[] elemento) {
		Set<String> conjunto = new HashSet<String>();
		for (String atual : elemento) {
			conjunto.add(atual);
		}
		return conjunto;
	}

	public ArrayList<ArrayList<String>> criarLista(String[] elemento) {
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		for (String atual : elemento) {
			ArrayList<String> lista1 = new ArrayList<String>();
			lista1.add(atual);
			lista.add(lista1);
		}
		return lista;
	}
	
	public ArrayList<ArrayList<Producao>> criarLista(Producao[] elemento) {
		ArrayList<ArrayList<Producao>> lista = new ArrayList<>();
		for (Producao atual : elemento) {
			ArrayList<Producao> lista1 = new ArrayList<Producao>();
			lista1.add(atual);
			lista.add(lista1);
		}
		return lista;
	}

}