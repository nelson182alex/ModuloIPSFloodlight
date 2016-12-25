package net.floodlightcontroller.estatistica;

import java.io.IOException;
import java.util.*;



import net.floodlightcontroller.persistencia.Persistencia;

public class Estatistica {
	
	static Persistencia p = new Persistencia();
	static Estatistica est = new Estatistica();
	static ArrayList listaIPOrigem =  new ArrayList();
	static ArrayList listaIPDestino =  new ArrayList();
	static ArrayList listaPortaOrigem =  new ArrayList();
	static ArrayList listaPortaDestino =  new ArrayList();
	static ArrayList listaEntropiaIPOrigem =  new ArrayList();
	static ArrayList listaEntropiaIPDestino =  new ArrayList();
	static ArrayList listaEntropiaPortaOrigem =  new ArrayList();
	static ArrayList listaEntropiaPortaDestino =  new ArrayList();
	static ArrayList listaIPOrigemZ =  new ArrayList();
	static ArrayList listaIPDestinoZ =  new ArrayList();
	static ArrayList listaPortaOrigemZ =  new ArrayList();
	static ArrayList listaPortaDestinoZ =  new ArrayList();
	static ArrayList listaEntropiaIPOrigemZ =  new ArrayList();
	static ArrayList listaEntropiaIPDestinoZ =  new ArrayList();
	static ArrayList listaEntropiaPortaOrigemZ =  new ArrayList();
	static ArrayList listaEntropiaPortaDestinoZ =  new ArrayList();
	static int cont = 0;
	static int cont1 = 0;
	static int cont2 = 0;
	static int cont3 = 0;
	static int cont4 = 0;
	static int contx1 = 1;
	static int contx2 = 1;
	static int contx3= 1;
	static int contx4 = 1;
	static int flag1 = 0;
	static int flag2 = 0;
	static int flag3 = 0;
	static int flag4 = 0;
	
	//Calcula a Média de uma Lista
	
	public double arredondar(double valor, int casas, int ceilOrFloor) {
	    double arredondado = valor;
	    arredondado *= (Math.pow(10, casas));
	    if (ceilOrFloor == 0) {
	        arredondado = Math.ceil(arredondado);           
	    } else {
	        arredondado = Math.floor(arredondado);
	    }
	    arredondado /= (Math.pow(10, casas));
	    return arredondado;
	}

	
	public static double media(List list){
	    double sum = 0.0;
	    double mean = 0.0;
	    double num = 0.0;
	    double numi = 0.0;
	    double deno = 0.0;
	    for (int i=0; i < list.size(); i++){
	        sum += (double)list.get(i);
	        mean = Math.abs(sum/list.size());
	        

	    }
	    return mean;
	}
	
	//Calculaaa a variancia de uma Lista
	public static double variancia(List list){
	    double sum = 0.0;
	    double mean = 0.0;
	    double num = 0.0;
	    double numi = 0.0;
	    double deno = 0.0;
	    for (int i=0; i < list.size(); i++){
	        sum += (double) list.get(i);
	        mean = sum/list.size()-1;

	    }

	    for (int i=0; i <list.size(); i++){
	         numi = Math.pow(((double)list.get(i) - mean),2);
	         num+=numi;
	         deno =list.size() - 1;  
	    }


	    double variancia = num/deno;
	    return variancia;
	}
	
	//Calcula o desvio padrão de uma Lista
	public static double desvioPadrao(List list){
	    double sum = 0.0;
	    double mean = 0.0;
	    double num = 0.0;
	    double numi = 0.0;
	    double deno = 0.0;
	    for (int i=0; i < list.size(); i++){
	        sum+= (double)list.get(i);
	        mean = sum/list.size()-1;

	    }

	    for (int i=0; i <list.size(); i++){
	         numi = Math.pow(((double)list.get(i) - mean),2);
	         num+=numi;
	         deno =list.size() - 1;  
	    }


	    double stdevResult = Math.sqrt(num/deno);
	    return stdevResult;
	}

	// Calcula a Entropia de uma lista
	public static Double calculateEntropy(List<String> values) {
       
        
		HashMap <String, Integer>  map= new HashMap<String, Integer>();
  
        // Somatório para calcular a ocorrencia de cada valor
        for (String sequence : values) {
            // Preenche o mapa com a key sendo o valor e o value a quantidade
            if (!map.containsKey(sequence)) {
                map.put(sequence, 0);
            }
            // Adiciona um a quantidade
            map.put(sequence, map.get(sequence) + 1);
        }

        // Calcula a entropia
        Double result = 0.0;
  
        // Itera pelo conjunto de possíveis valores
        for (String sequence : map.keySet()) {
            // Calcula a frequencia que o registro aparece, a probabilidade do valor
            // aparecer na base de dados informada
            Double frequency = (double) map.get(sequence) / values.size();
            // Faz o calculo da entropia
            result -= frequency * (Math.log(frequency) / Math.log(2));
        }

        // Retorna o valor
        return result;
    }

	//Calcula Entropia do IP de Origem
	public static synchronized void entropiaIPOrigemTreinamento(int number, String IPOrigem){
		
		double media = 0.0;
		double variancia = 0.0;
		double desvioPadrao = 0.0;
	   
		listaIPOrigem.add(IPOrigem);
		if(listaIPOrigem.size() == number){
		   p.insertIPOrigem(est.calculateEntropy(listaIPOrigem));
		   listaEntropiaIPOrigem.add(est.calculateEntropy(listaIPOrigem));
		   if(listaEntropiaIPOrigem.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			   media = est.media(listaEntropiaIPOrigem);
			   variancia = est.variancia(listaEntropiaIPOrigem);
			   desvioPadrao = est.desvioPadrao(listaEntropiaIPOrigem);
			   p.insertMedidasEstatisticasEntropiaIPOrigem(est.arredondar(media, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadrao, 2, 0));
			   listaEntropiaIPOrigem.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		   listaIPOrigem.clear();
			
		}
	}
	
	//Calcula Entropia do IP de Destino
	
	public static synchronized void entropiaIPDestinoTreinamento(int number, String IPDestino){
		
		double media = 0.0;
		double variancia = 0.0;
		double desvioPadrao = 0.0;
		
		listaIPDestino.add(IPDestino);
		if(listaIPDestino.size() == number){
		   p.insertIPDestino(est.calculateEntropy(listaIPDestino));
		   listaEntropiaIPDestino.add(est.calculateEntropy(listaIPDestino));
		   if(listaEntropiaIPDestino.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			    media = est.media(listaEntropiaIPDestino);
			    variancia = est.variancia(listaEntropiaIPDestino);
			    desvioPadrao = est.desvioPadrao(listaEntropiaIPDestino);
			   p.insertMedidasEstatisticasEntropiaIPDestino(est.arredondar(media, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadrao, 2, 0));
			   listaEntropiaIPDestino.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		   listaIPDestino.clear();
			
		}
		
	}
	
	//Calcula Entropia da Porta de Origem
	
	public static synchronized void entropiaPortaOrigemTreinamento(int number, String PortaOrigem){
		
		double media = 0.0;
		double variancia = 0.0;
		double desvioPadrao = 0.0;
		
		listaPortaOrigem.add(PortaOrigem);
		if(listaPortaOrigem.size() == number){
		   p.insertPortaOrigem(est.calculateEntropy(listaPortaOrigem));
		   listaEntropiaPortaOrigem.add(est.calculateEntropy(listaPortaOrigem));
		   if(listaEntropiaPortaOrigem.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			    media = est.media(listaEntropiaPortaOrigem);
			    variancia = est.variancia(listaEntropiaPortaOrigem);
			    desvioPadrao = est.desvioPadrao(listaEntropiaPortaOrigem);
			   p.insertMedidasEstatisticasEntropiaPortaOrigem(est.arredondar(media, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadrao, 2, 0));
			   listaEntropiaPortaOrigem.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		   listaPortaOrigem.clear();
			
		}
	}
	
	//Calcula Entropia da Porta de Destino
	public static synchronized void entropiaPortaDestinoTreinamento(int number, String PortaDestino){
		double media = 0.0;
		double variancia = 0.0;
		double desvioPadrao = 0.0;
		listaPortaDestino.add(PortaDestino);
		if(listaPortaDestino.size() == number){
		   p.insertPortaDestino(est.calculateEntropy(listaPortaDestino));
		   listaEntropiaPortaDestino.add(est.calculateEntropy(listaPortaDestino));
		   if(listaEntropiaPortaDestino.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			   media = est.media(listaEntropiaPortaDestino);
			   variancia = est.variancia(listaEntropiaPortaDestino);
			   desvioPadrao = est.desvioPadrao(listaEntropiaPortaDestino);
			   p.insertMedidasEstatisticasEntropiaPortaDestino(est.arredondar(media, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadrao, 2, 0));
			   listaEntropiaPortaDestino.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		  listaPortaDestino.clear();
			
		}
	}
	
	
	//Calcula Entropia do IP de Origem
	public static synchronized void entropiaIPOrigemSobAtaque(int number, String IPOrigem){
	   
		listaIPOrigem.add(IPOrigem);
		if(listaIPOrigem.size() == number){
		   p.insertIPOrigem(est.calculateEntropy(listaIPOrigem));
		   listaEntropiaIPOrigem.add(est.calculateEntropy(listaIPOrigem));
		   if(listaEntropiaIPOrigem.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			   double media = est.media(listaEntropiaIPOrigem);
			   double variancia = est.variancia(listaEntropiaIPOrigem);
			   double desvioPadrao = est.desvioPadrao(listaEntropiaIPOrigem);
			   p.insertMedidasEstatisticasEntropiaIPOrigemAtaque(media, variancia, desvioPadrao);
			   listaEntropiaIPOrigem.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		   listaIPOrigem.clear();
			
		}
	}
	
	//Percebe Ataque IP de Destino
		public static synchronized void entropiaIPDestinoSobAtaque(int number, String IPDestino){
			listaIPDestino.add(IPDestino);
			if(listaIPDestino.size() == number){
			   p.insertIPDestino(est.calculateEntropy(listaIPDestino));
			   listaEntropiaIPDestino.add(est.calculateEntropy(listaIPDestino));
			   if(listaEntropiaIPDestino.size() == 30){
				   //Calcula média, variancia e desvio padrão e insere em banco
				   double media = est.media(listaEntropiaIPDestino);
				   double variancia = est.variancia(listaEntropiaIPDestino);
				   double desvioPadrao = est.desvioPadrao(listaEntropiaIPDestino);
				   p.insertMedidasEstatisticasEntropiaIPDestinoAtaque(media, variancia, desvioPadrao);
				   listaEntropiaIPDestino.clear();
				   System.out.println("Média da Entropia:" + media);
				   System.out.println("Variância da Entropia:" + variancia);
				   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
			   }
			  listaIPDestino.clear();
				
			}
		}
	
	
	//Calcula Entropia da Porta de Origem
	
	public static synchronized void entropiaPortaOrigemSobAtaque(int number, String PortaOrigem){
		listaPortaOrigem.add(PortaOrigem);
		if(listaPortaOrigem.size() == number){
		   p.insertPortaOrigem(est.calculateEntropy(listaPortaOrigem));
		   listaEntropiaPortaOrigem.add(est.calculateEntropy(listaPortaOrigem));
		   if(listaEntropiaPortaOrigem.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			   double media = est.media(listaEntropiaPortaOrigem);
			   double variancia = est.variancia(listaEntropiaPortaOrigem);
			   double desvioPadrao = est.desvioPadrao(listaEntropiaPortaOrigem);
			   p.insertMedidasEstatisticasEntropiaPortaOrigemAtaque(media, variancia, desvioPadrao);
			   listaEntropiaPortaOrigem.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		   listaPortaOrigem.clear();
		   
		   
			
		}
	}
	
	//Calcula Entropia da Porta de Destino
	public static synchronized void entropiaPortaDestinoSobAtaque(int number, String PortaDestino){
		listaPortaDestino.add(PortaDestino);
		if(listaPortaDestino.size() == number){
		   p.insertPortaDestino(est.calculateEntropy(listaPortaDestino));
		   listaEntropiaPortaDestino.add(est.calculateEntropy(listaPortaDestino));
		   if(listaEntropiaPortaDestino.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			   double media = est.media(listaEntropiaPortaDestino);
			   double variancia = est.variancia(listaEntropiaPortaDestino);
			   double desvioPadrao = est.desvioPadrao(listaEntropiaPortaDestino);
			   p.insertMedidasEstatisticasEntropiaPortaDestinoAtaque(media, variancia, desvioPadrao);
			   listaEntropiaPortaDestino.clear();
			   System.out.println("Média da Entropia:" + media);
			   System.out.println("Variância da Entropia:" + variancia);
			   System.out.println("Desvio Padrão da Entropia:" + desvioPadrao);
		   }
		  listaPortaDestino.clear();
			
		}
	}
	
public static synchronized void entropiaIPDestinoDetectaAtaque(int number, String IPDestino){
		
		double mediaN = 0;
		double mediaA = 0;
		double desvioPadraoA = 0;
		double desvioPadraoN = 0;
		double z = 0;
		
		listaIPDestino.add(IPDestino);
		if(listaIPDestino.size() == number){
		   p.insertIPDestino(est.calculateEntropy(listaIPDestino));
		   listaEntropiaIPDestino.add(est.calculateEntropy(listaIPDestino));
		   if(listaEntropiaIPDestino.size() == 30){
			   //Calcula média, variancia e desvio padrão e insere em banco
			    mediaA = est.media(listaEntropiaIPDestino);
			   double variancia = est.variancia(listaEntropiaIPDestino);
			   desvioPadraoA = est.desvioPadrao(listaEntropiaIPDestino);
			   p.insertMedidasEstatisticasEntropiaIPDestinoAtaque(est.arredondar(mediaA, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadraoA, 2, 0));
			   mediaN = p.selectMedidaEntropia(contx1, "tabelaEstatisticaIPDestino", "Media");
			   desvioPadraoN = p.selectMedidaEntropia(contx1, "tabelaEstatisticaIPDestino", "DesvioPadrao");
			   z = est.calcularZ(mediaN, desvioPadraoN, 30, mediaA, desvioPadraoA, 30);
			   
			   if((z > 1.64)){
				   flag1 = 1;
				   String vitima = est.descobrirIPRepete(listaIPDestino);
				   p.insertResultadosIPDestino(z, 1.0, vitima);              
				   
			   }
		           
		            else
			        p.insertResultadosIPDestino(z, 0.0, null);
			   
			   listaEntropiaIPDestino.clear();
		   }
		   listaIPDestino.clear();
			
		}
	}

public static synchronized void entropiaIPOrigemDetectaAtaque(int number, String IPOrigem){
	
	double mediaN = 0;
	double mediaA = 0;
	double desvioPadraoA = 0;
	double desvioPadraoN = 0;
	double z = 0;
	
	listaIPOrigem.add(IPOrigem);
	if(listaIPOrigem.size() == number){
	   p.insertIPOrigem(est.calculateEntropy(listaIPOrigem));
	   listaEntropiaIPOrigem.add(est.calculateEntropy(listaIPOrigem));
	   if(listaEntropiaIPOrigem.size() == 30){
		   //Calcula média, variancia e desvio padrão e insere em banco
		    mediaA = est.media(listaEntropiaIPOrigem);
		   double variancia = est.variancia(listaEntropiaIPOrigem);
		   desvioPadraoA = est.desvioPadrao(listaEntropiaIPOrigem);
		   p.insertMedidasEstatisticasEntropiaIPOrigemAtaque(est.arredondar(mediaA, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadraoA, 2, 0));
		   mediaN = p.selectMedidaEntropia(contx2, "tabelaEstatisticaIPOrigem", "Media");
		   desvioPadraoN = p.selectMedidaEntropia(contx2, "tabelaEstatisticaIPOrigem", "DesvioPadrao");
		   z = est.calcularZ(mediaN, desvioPadraoN, 30, mediaA, desvioPadraoA, 30);
		   
		   if((z > 1.64)){
			   flag2 = 1;
			   p.insertResultadosIPOrigem(z, 1.0, null);
			
		   }
			  
	           
	            else
		        p.insertResultadosIPOrigem(z, 0.0, null);
		   
		   listaEntropiaIPOrigem.clear();
	   }
	   listaIPOrigem.clear();
		
	}

}

public static synchronized void entropiaPortaOrigemDetectaAtaque(int number, String PortaOrigem){
	
	double mediaN = 0;
	double mediaA = 0;
	double desvioPadraoA = 0;
	double desvioPadraoN = 0;
	double z = 0;
	
	listaPortaOrigem.add(PortaOrigem);
	if(listaPortaOrigem.size() == number){
	   p.insertPortaOrigem(est.calculateEntropy(listaPortaOrigem));
	   listaEntropiaPortaOrigem.add(est.calculateEntropy(listaPortaOrigem));
	   if(listaEntropiaPortaOrigem.size() == 30){
		   //Calcula média, variancia e desvio padrão e insere em banco
		    mediaA = est.media(listaEntropiaPortaOrigem);
		   double variancia = est.variancia(listaEntropiaPortaOrigem);
		   desvioPadraoA = est.desvioPadrao(listaEntropiaPortaOrigem);
		   p.insertMedidasEstatisticasEntropiaPortaOrigemAtaque(est.arredondar(mediaA, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadraoA, 2, 0));
		   mediaN = p.selectMedidaEntropia(contx3, "tabelaEstatisticaPortaOrigem", "Media");
		   desvioPadraoN = p.selectMedidaEntropia(contx3, "tabelaEstatisticaPortaOrigem", "DesvioPadrao");
		   z = est.calcularZ(mediaN, desvioPadraoN, 30, mediaA, desvioPadraoA, 30);
		   
		   if((z > 1.64)){
			   flag3 = 1;
			   p.insertResultadosPortaOrigem(z, 1.0, null);
		   }
			  
	           
	            else
		        p.insertResultadosPortaOrigem(z, 0.0, null);
		   
		   listaEntropiaPortaOrigem.clear();
	   }
	   listaPortaOrigem.clear();
		
	}

}
	
public static synchronized void entropiaPortaDestinoDetectaAtaque(int number, String PortaDestino){
	
	double mediaN = 0;
	double mediaA = 0;
	double desvioPadraoA = 0;
	double desvioPadraoN = 0;
	double z = 0;
	
	listaPortaDestino.add(PortaDestino);
	if(listaPortaDestino.size() == number){
	   p.insertPortaDestino(est.calculateEntropy(listaPortaDestino));
	   listaEntropiaPortaDestino.add(est.calculateEntropy(listaPortaDestino));
	   if(listaEntropiaPortaDestino.size() == 30){
		   //Calcula média, variancia e desvio padrão e insere em banco
		    mediaA = est.media(listaEntropiaPortaDestino);
		   double variancia = est.variancia(listaEntropiaPortaDestino);
		   desvioPadraoA = est.desvioPadrao(listaEntropiaPortaDestino);
		   p.insertMedidasEstatisticasEntropiaPortaDestinoAtaque(est.arredondar(mediaA, 2, 0), est.arredondar(variancia, 2, 0), est.arredondar(desvioPadraoA, 2, 0));
		   mediaN = p.selectMedidaEntropia(contx4, "tabelaEstatisticaPortaDestino", "Media");
		   desvioPadraoN = p.selectMedidaEntropia(contx4, "tabelaEstatisticaPortaDestino", "DesvioPadrao");
		   z = est.calcularZ(mediaN, desvioPadraoN, 30, mediaA, desvioPadraoA, 30);
		   
		   if((z > 1.64)){
			   flag4 = 1;
			   p.insertResultadosPortaDestino(z, 1.0, null);
		   }
			  
	           
	            else
		        p.insertResultadosPortaDestino(z, 0.0, null);
		   
		   listaEntropiaPortaDestino.clear();
	   }
	   listaPortaDestino.clear();
	}
}

public void detectarAtaqueGeral(){
	int res = flag1 + flag2 + flag3;
	if(res > 2){
		String vitima = est.descobrirIPRepete(listaIPDestino);
		p.insertResultadosGeral(1.0, vitima);
		flag1 = 0;
		flag2 = 0;
		flag3 = 0;
		flag4 = 0;
		
	}
	else{
		p.insertResultadosGeral(0.0, null);
	}
}
	

	
	// Cálculo de Z
	public Double calcularZ(double mediaN, double desvioPadraoN, int n, double mediaA, double desvioPadraoR, int r){
		double z = 0;
		z = (Math.abs(mediaN - mediaA))/(Math.sqrt(Math.pow(desvioPadraoN, 2)/n) + Math.sqrt(Math.pow(desvioPadraoR, 2)/r));
		return z;
	}
	
	//Descobrir IP que mais se repete em lista
	
	public String descobrirIPRepete(ArrayList <String> lista){
	
		
		Map<String, Integer> stringsCount = new HashMap<>();
		for(String s: lista)
		{
		  Integer c = stringsCount.get(s);
		  if(c == null) c = new Integer(0);
		  c++;
		  stringsCount.put(s,c);
		}
		
		Map.Entry<String,Integer> mostRepeated = null;
		for(Map.Entry<String, Integer> e: stringsCount.entrySet())
		{
		    if(mostRepeated == null || mostRepeated.getValue()<e.getValue())
		        mostRepeated = e;
		}
		
		if(mostRepeated != null)
	        return mostRepeated.getKey();
		return null;
	}
	
	
	
	



}
