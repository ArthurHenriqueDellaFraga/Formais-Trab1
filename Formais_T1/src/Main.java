import java.util.ArrayList;

import Modelo.ArvoreBinaria;
import Modelo.ExpressaoRegular;
import Modelo.OperadorEnum;
import Modelo.Testes;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modelo.AutomatoFinito;
import Modelo.Transicao;
import Teste.Testes;

public class Main {

	public static void main(String[] args) {
		System.out.println(new ArvoreBinaria(new ExpressaoRegular("(((a|b)))?.d.d*").gerarArvore()).toString());			
	}
	
	

}
