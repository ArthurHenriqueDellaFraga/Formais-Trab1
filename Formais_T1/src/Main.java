import java.util.ArrayList;

import Modelo.ArvoreBinaria;
import Modelo.ExpressaoRegular;
import Modelo.OperadorEnum;
import Modelo.Testes;

public class Main {

	public static void main(String[] args) {
		System.out.println(new ArvoreBinaria(new ExpressaoRegular("(((a|b)))?.d.d*").gerarArvore()).toString());			
	}

}
