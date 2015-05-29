package Modelo;

import java.util.ArrayList;

public class ArvoreBinaria {

	protected Nodo raiz;
	
	public ArvoreBinaria(Nodo _raiz){
		raiz = _raiz;
	}
	
	//FUNCOES

	public void definirCostura(){
		Nodo avo = raiz;
		while(avo != null){
			if(!avo.folha){
				Nodo pai = avo.filho[1];
				pai.costura = avo;
				
				if(pai.folha){
				
			}
				
				
			}
		}
	}
	
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
