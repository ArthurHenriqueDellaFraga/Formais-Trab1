package Modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.AutomatoFinito;
import Modelo.SingletonAF;
import Modelo.Transicao;


public class Testes {

	public SingletonAF singletonAF = SingletonAF.getInstance();
	
	public Testes(){
		
	}
	
	public void executarTestesMinimizacao(){
		for(int i = 1; i < 10; i++){
			System.out.println("----------- CDT " + i + " INICIO -----------");
			
			switch(i){
				case 1:	System.out.println(cdtMinimizacao1());
						break;
				
				case 2:	System.out.println(cdtMinimizacao2());
						break;
				
				case 3:	System.out.println(cdtMinimizacao3());
						break;
				
				case 4:	System.out.println(cdtMinimizacao4());
						break;
				
				case 5:	System.out.println(cdtMinimizacao5());
						break;
				
				case 6:	System.out.println(cdtMinimizacao6());
						break;
				
				case 7:	System.out.println(cdtMinimizacao7());
						break;
				
				case 8:	System.out.println(cdtMinimizacao8());
						break;
				
				case 9:	System.out.println(cdtMinimizacao9());
						break;
			}
			
			System.out.println("----------- CDT " + i + " FIM -----------");
		}
	}
	
	//3 estados - 2 finais equivalentes - Sem fi
	public int cdtMinimizacao1(){
		
		//CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = {"q0", "q1", "q2"};
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));
			
		String[] alfabeto1 = {"a", "b"};
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));
		
		String estadoInicial = "q0";

		String[] estadoFinal1 = {"q0", "q2"};
		Set<String> estadoFinal = new HashSet<String>(criarConjunto(estadoFinal1));
				
		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();
				
			tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(), encapsulador.get(2));
			tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(), encapsulador.get(0));
			tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(), encapsulador.get(2));
			
		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto, tabelaDeTransicao, estadoInicial, estadoFinal);	
		
		//CRIACAO DO AUTOMATO ESPERADO
		String[] estado2 = {"q0", "q1"};
		Set<String> estado3 = new HashSet<String>(criarConjunto(estado2));
			
		String[] alfabeto2 = {"a", "b"};
		Set<String> alfabeto3 = new HashSet<String>(criarConjunto(alfabeto2));
		
		String estadoInicial3 = "q0";

		String[] estadoFinal2 = {"q0"};
		Set<String> estadoFinal3 = new HashSet<String>(criarConjunto(estadoFinal2));
				
		ArrayList<ArrayList<String>> encapsulador2 = criarLista(estado2);
		HashMap<String, ArrayList<String>> tabelaDeTransicao3 = new HashMap<String, ArrayList<String>>();
				
			tabelaDeTransicao3.put(new Transicao("q0", "a").hashMap(), encapsulador2.get(1));
			tabelaDeTransicao3.put(new Transicao("q1", "a").hashMap(), encapsulador2.get(0));
			tabelaDeTransicao3.put(new Transicao("q0", "b").hashMap(), encapsulador2.get(0));
			tabelaDeTransicao3.put(new Transicao("q1", "b").hashMap(), encapsulador2.get(1));
			
		AutomatoFinito automatoEsperado = new AutomatoFinito(estado3, alfabeto3, tabelaDeTransicao3, estadoInicial3, estadoFinal3);	
		
		//MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);
		
		int comparacao = singletonAF.determinarEquivalencia(automatoEsperado, automatoMinimizado);
		return comparacao;		
	}

	//5 estados - 2 nao finais equivalentes - Sem fi
	public String cdtMinimizacao2(){
		
		//CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = {"q0", "q1", "q2", "q3", "q4", "q5", "q6"};
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));
			
		String[] alfabeto1 = {"a", "b"};
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));
		
		String estadoInicial = "q0";

		String[] estadoFinal1 = {"q0", "q2"};
		Set<String> estadoFinal = new HashSet<String>(criarConjunto(estadoFinal1));
				
		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();
				
			tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(), encapsulador.get(2));
			tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(), encapsulador.get(2));
			tabelaDeTransicao.put(new Transicao("q3", "a").hashMap(), encapsulador.get(4));
			tabelaDeTransicao.put(new Transicao("q4", "a").hashMap(), encapsulador.get(4));
			tabelaDeTransicao.put(new Transicao("q5", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q6", "a").hashMap(), encapsulador.get(6));
			tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(), encapsulador.get(3));
			tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(), encapsulador.get(6));
			tabelaDeTransicao.put(new Transicao("q3", "b").hashMap(), encapsulador.get(0));
			tabelaDeTransicao.put(new Transicao("q4", "b").hashMap(), encapsulador.get(0));
			tabelaDeTransicao.put(new Transicao("q5", "b").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q6", "b").hashMap(), encapsulador.get(6));
			
		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto, tabelaDeTransicao, estadoInicial, estadoFinal);	
		
		//MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);
		
		System.out.println(automatoMinimizado.toString());
		
		String comparacao = "";
		return comparacao;	
	}

	//3 estados - 2 nao finais equivalentes - Com fi
	public String cdtMinimizacao3(){
		
		//CRIACAO DO AUTOMATO ORIGINAL
		String[] estado1 = {"q0", "q1", "q2"};
		Set<String> estado = new HashSet<String>(criarConjunto(estado1));
			
		String[] alfabeto1 = {"a", "b"};
		Set<String> alfabeto = new HashSet<String>(criarConjunto(alfabeto1));
		
		String estadoInicial = "q0";

		String[] estadoFinal1 = {"q0", "q2"};
		Set<String> estadoFinal = new HashSet<String>(criarConjunto(estadoFinal1));
				
		ArrayList<ArrayList<String>> encapsulador = criarLista(estado1);
		HashMap<String, ArrayList<String>> tabelaDeTransicao = new HashMap<String, ArrayList<String>>();
				
			tabelaDeTransicao.put(new Transicao("q0", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q1", "a").hashMap(), encapsulador.get(2));
			tabelaDeTransicao.put(new Transicao("q2", "a").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q0", "b").hashMap(), encapsulador.get(0));
			tabelaDeTransicao.put(new Transicao("q1", "b").hashMap(), encapsulador.get(1));
			tabelaDeTransicao.put(new Transicao("q2", "b").hashMap(), encapsulador.get(2));
			
		AutomatoFinito automato = new AutomatoFinito(estado, alfabeto, tabelaDeTransicao, estadoInicial, estadoFinal);	
		
		//CRIAÇÃO DO AUTOMATO ESPERADO
		String[] estado2 = {"q0", "q1"};
		Set<String> estado3 = new HashSet<String>(criarConjunto(estado2));
			
		String[] alfabeto2 = {"a", "b"};
		Set<String> alfabeto3 = new HashSet<String>(criarConjunto(alfabeto2));
		
		String estadoInicial3 = "q0";

		String[] estadoFinal2 = {"q0"};
		Set<String> estadoFinal3 = new HashSet<String>(criarConjunto(estadoFinal2));
				
		ArrayList<ArrayList<String>> encapsulador2 = criarLista(estado2);
		HashMap<String, ArrayList<String>> tabelaDeTransicao3 = new HashMap<String, ArrayList<String>>();
				
			tabelaDeTransicao3.put(new Transicao("q0", "a").hashMap(), encapsulador2.get(1));
			tabelaDeTransicao3.put(new Transicao("q1", "a").hashMap(), encapsulador2.get(0));
			tabelaDeTransicao3.put(new Transicao("q0", "b").hashMap(), encapsulador2.get(0));
			tabelaDeTransicao3.put(new Transicao("q1", "b").hashMap(), encapsulador2.get(1));
			
		AutomatoFinito automatoEsperado = new AutomatoFinito(estado3, alfabeto3, tabelaDeTransicao3, estadoInicial3, estadoFinal3);	
		
		//MINIZAR AUTOMATO ORIGINAL
		AutomatoFinito automatoMinimizado = singletonAF.minimizar(automato);
		
		return "";	
	}
	
	//5 estados - 2 finais equivalentes - Com fi
	public String cdtMinimizacao4(){
		return "Oi";
	}
	
	//3 estados - 2 finais equivalentes - 1 inalcancavel - Sem fi
	public String cdtMinimizacao5(){
		return "Oi";
	}
	
	//5 estados - 2 nao finais equivalentes - 1 inalvancavel - Com fi
	public String cdtMinimizacao6(){
		return "Oi";
	}
	
	//3 estados - 2 nao finais equivalentes - 1 morto - Sem fi
	public String cdtMinimizacao7(){
		return "Oi";
	}
	
	//5 estados - 2 finais equivalentes - 1 morto - Com fi
	public String cdtMinimizacao8(){
		return "Oi";
	}
		
	//12 estados 6 simbolos - 2 pares de finais equivalentes - 2 pares de nao finais equivalentes - 2 morto - 2 Inalcancavel - 2 transicao por fi - Com fi
	public String cdtMinimizacao9(){
		return "Oi";
	}
	
	
	
	//CASO DE TESTE UNIAO
	public String cdtUniao1(){
		// CRIACAO DO AUTOMATO UM
		String[] estadoTemp = { "q0", "q1", "q2" };
		Set<String> estado1 = new HashSet<String>(criarConjunto(estadoTemp));

		String[] alfabetoTemp = { "a", "b" };
		Set<String> alfabeto1 = new HashSet<String>(criarConjunto(alfabetoTemp));

		String estadoInicial1 = "q0";

		String[] estadoFinalTemp = { "q0", "q2" };
		Set<String> estadoFinal1 = new HashSet<String>(criarConjunto(estadoFinalTemp));

		ArrayList<ArrayList<String>> encapsulador1 = criarLista(estadoTemp);
		HashMap<String, ArrayList<String>> tabelaDeTransicao1 = new HashMap<String, ArrayList<String>>();

		tabelaDeTransicao1.put(new Transicao("q0", "a").hashMap(), encapsulador1.get(1));
		tabelaDeTransicao1.put(new Transicao("q1", "a").hashMap(), encapsulador1.get(2));
		tabelaDeTransicao1.put(new Transicao("q2", "a").hashMap(), encapsulador1.get(1));
		tabelaDeTransicao1.put(new Transicao("q0", "b").hashMap(), encapsulador1.get(0));
		tabelaDeTransicao1.put(new Transicao("q1", "b").hashMap(), encapsulador1.get(1));
		tabelaDeTransicao1.put(new Transicao("q2", "b").hashMap(), encapsulador1.get(2));

		AutomatoFinito automato1 = new AutomatoFinito(estado1, alfabeto1, tabelaDeTransicao1, estadoInicial1, estadoFinal1);
		
		// CRIACAO DO AUTOMATO UM
				String[] estadoTemp2 = { "q0", "q1", "q2" };
				Set<String> estado2 = new HashSet<String>(criarConjunto(estadoTemp2));

				String[] alfabetoTemp2 = { "a", "b" };
				Set<String> alfabeto2 = new HashSet<String>(criarConjunto(alfabetoTemp2));

				String estadoInicial2 = "q0";

				String[] estadoFinalTemp2 = { "q0", "q2" };
				Set<String> estadoFinal2 = new HashSet<String>(criarConjunto(estadoFinalTemp2));

				ArrayList<ArrayList<String>> encapsulador2 = criarLista(estadoTemp);
				HashMap<String, ArrayList<String>> tabelaDeTransicao2 = new HashMap<String, ArrayList<String>>();

				tabelaDeTransicao2.put(new Transicao("q0", "a").hashMap(), encapsulador2.get(1));
				tabelaDeTransicao2.put(new Transicao("q1", "a").hashMap(), encapsulador2.get(2));
				tabelaDeTransicao2.put(new Transicao("q2", "a").hashMap(), encapsulador2.get(1));
				tabelaDeTransicao2.put(new Transicao("q0", "b").hashMap(), encapsulador2.get(0));
				tabelaDeTransicao2.put(new Transicao("q1", "b").hashMap(), encapsulador2.get(1));
				tabelaDeTransicao2.put(new Transicao("q2", "b").hashMap(), encapsulador2.get(2));

				AutomatoFinito automato2 = new AutomatoFinito(estado2, alfabeto2, tabelaDeTransicao2, estadoInicial2, estadoFinal2);
		
		AutomatoFinito automatoUnido = singletonAF.unir(automato1, automato2);
		
		return automatoUnido.toString();
				
	}
	
	public Set<String> criarConjunto(String[] elemento){
		Set<String> conjunto = new HashSet<String>();
		for(String atual : elemento){
			conjunto.add(atual);
		}
		return conjunto;
	}
	
	public ArrayList<ArrayList<String>> criarLista(String[] elemento){
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		for(String atual : elemento){
			ArrayList<String> lista1 = new ArrayList<String>();
			lista1.add(atual);
			lista.add(lista1);
		}
		return lista;
	}
	
}