package Modelo;

public enum OperadorEnum{
	
	Alternativa("|"), Concatenacao("."), Fechamento("*"), Opcao("?");

	public String identificacao;
	
	OperadorEnum(String _identificacao) {
		identificacao = _identificacao;
	}
	
	public String[] quebrarExpressao(String expressao, OperadorEnum operador){
		String[] substring = new String[2];
		
		if(expressao.indexOf("(") == 0 && expressao.indexOf(")") == expressao.length() -1){
			int cursorAuxiliar1 = expressao.indexOf("(", 1);
			int cursorAuxiliar2 = expressao.indexOf(")", 1);
			while(cursorAuxiliar1 < cursorAuxiliar2 && cursorAuxiliar1 != -1){
				cursorAuxiliar1 = expressao.indexOf("(", cursorAuxiliar1 +1);
				cursorAuxiliar2 = expressao.indexOf(")", cursorAuxiliar2 +1);
			}
			if(cursorAuxiliar2 == expressao.length() -1){
				expressao = expressao.substring(1, expressao.length() -1);				
			}
		}
		
		for(int i = 0; i < expressao.length(); i++){
			String characterAtual = expressao.substring(i, i +1);
			if(characterAtual.equals(operador.identificacao)){
				switch(characterAtual){
				case "|":
				case ".":
					substring[0] = expressao.substring(0, i);
					substring[1] = expressao.substring(i +1, expressao.length());
					return substring;
					
				case "*":
				case "?":
					substring[0] = expressao.substring(0, i);
					substring[1] = null;
					return substring;
				}
			}
			else{
				if(characterAtual.equals("(")){
					int cursorAuxiliar = expressao.indexOf("(", i+1);
					i = expressao.indexOf(")", i);
					while(cursorAuxiliar < i && cursorAuxiliar != -1){
						cursorAuxiliar = expressao.indexOf("(", cursorAuxiliar +1);
						i = expressao.indexOf(")", i +1);
					}
				}
			}
		}	
		return null;	
	}
}