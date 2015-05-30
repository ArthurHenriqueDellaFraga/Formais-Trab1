package modelo.util;


import java.util.ArrayList;

import modelo.AutomatoFinito;

public class ArvoreBinaria {

	public static Nodo fi = new Nodo(AutomatoFinito.fi);
	private Nodo raiz;
	
	public ArvoreBinaria(Nodo _raiz){
		raiz = _raiz;
	}
	
	//ACESSO
	
	public Nodo getRaiz(){
		return raiz;
	}
	
	//FUNCOES
	
	public String toString(){
		ArrayList<Nodo> listaNodo = new ArrayList<Nodo>();
		listaNodo.add(raiz);
		
		String resultado = "";
		
		while(listaNodo.size() > 0){
			for(Nodo atual : new ArrayList<Nodo>(listaNodo)){
				resultado += atual != null ? "[" + atual.chave + "]" : "[]";
				listaNodo.remove(atual);
				
				if(atual != null){
					for(int i = 0; i < atual.filho.length; i++){
						listaNodo.add(atual.filho[i]);
					}
				}
				resultado += " ";
			}
			resultado += "\n";
		}
		
		return resultado;
	}

	  
	
}
