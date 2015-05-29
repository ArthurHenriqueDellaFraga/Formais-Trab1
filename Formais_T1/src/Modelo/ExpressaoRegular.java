package Modelo;

import java.util.ArrayList;
import java.util.HashSet;

public class ExpressaoRegular {

	protected String expressao;
	protected ArvoreBinaria arvore;
	
	public ExpressaoRegular(String _expressao){
		expressao = _expressao;
	}
	
	//FUNCOES
	
	public Nodo gerarArvore(String expressao){
		if(expressao != null){
			System.out.println(expressao);
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
	
	public Nodo gerarArvore(){
		return gerarArvore(expressao);
	}
	
}
