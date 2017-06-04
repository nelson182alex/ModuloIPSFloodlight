package net.floodlightcontroller.persistencia;

import java.awt.List;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.floodlightcontroller.estatistica.Estatistica;
 

public class Persistencia {
	

     Estatistica est = new Estatistica();
     
	 private Connection connect() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:/home/nelson/Banco_Projeto_IPS/banco";
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	    }
	 
	 private Connection connect2() {
	        // SQLiteer connection string
	        String url = "jdbc:sqlite:/home/nelson/Banco_Projeto_IPS/banco_entropiaIPOrigem";
	        Connection conn2 = null;
	        try {
	            conn2 = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn2;
	    }
	 
	 private Connection connect3() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:/home/nelson/Banco_Projeto_IPS/banco_entropiaIPDestino";
	        Connection conn3 = null;
	        try {
	            conn3 = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn3;
	    }
	 
	 private Connection connect4() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:/home/nelson/Banco_Projeto_IPS/banco_entropiaPortaOrigem";
	        Connection conn4 = null;
	        try {
	            conn4 = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn4;
	    }
	 
	 private Connection connect5() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:/home/nelson/Banco_Projeto_IPS/banco_entropiaPortaDestino";
	        Connection conn5 = null;
	        try {
	            conn5 = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn5;
	    }
	 
	//Guarda Informações no Banco
	 
    public synchronized void  insert(int Numero, String Protocolo, String IPOrigem, String IPDestino, String PortaOrigem, String PortaDestino) {
        String sql = "INSERT INTO logs(Numero,Protocolo,IPOrigem,IPDestino,PortaOrigem,PortaDestino) VALUES(?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Numero);
            pstmt.setString(2, Protocolo);
            pstmt.setString(3, IPOrigem);
            pstmt.setString(4, IPDestino);
            pstmt.setString(5, PortaOrigem);
            pstmt.setString(6, PortaDestino);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere entropia do conjunto de IP de Origem
    public synchronized void insertIPOrigem(Double entropiaIPOrigem) {
        String sql = "INSERT INTO tabela(EntropiaIPOrigem) VALUES(?)";
 
        try (Connection conn2 = this.connect2();
            PreparedStatement pstmt = conn2.prepareStatement(sql)) {
            pstmt.setDouble(1, entropiaIPOrigem);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere entropia do conjunto de IP de Destino
    
    public synchronized void insertIPDestino(Double entropiaIPDestino) {
        String sql = "INSERT INTO tabela(EntropiaIPDestino) VALUES(?)";
 
        try (Connection conn3 = this.connect3();
            PreparedStatement pstmt = conn3.prepareStatement(sql)) {
            pstmt.setDouble(1, entropiaIPDestino);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere entropia do conjunto de Porta de Origem
    public synchronized void insertPortaOrigem(Double entropiaPortaOrigem) {
        String sql = "INSERT INTO tabela(EntropiaPortaOrigem) VALUES(?)";
 
        try (Connection conn4 = this.connect4();
            PreparedStatement pstmt = conn4.prepareStatement(sql)) {
            pstmt.setDouble(1, entropiaPortaOrigem);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere entropia do conjunto de Porta de Destino
    
    public synchronized void insertPortaDestino(Double entropiaPortaDestino) {
        String sql = "INSERT INTO tabela(EntropiaPortaDestino) VALUES(?)";
 
        try (Connection conn5 = this.connect5();
            PreparedStatement pstmt = conn5.prepareStatement(sql)) {
            pstmt.setDouble(1, entropiaPortaDestino);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia do IP de Origem
    public synchronized void insertMedidasEstatisticasEntropiaIPOrigem(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaIPOrigem(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia do IP de Destino
    public synchronized void insertMedidasEstatisticasEntropiaIPDestino(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaIPDestino(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Origem
    public synchronized void insertMedidasEstatisticasEntropiaPortaOrigem(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaPortaOrigem(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Destino
    public synchronized void insertMedidasEstatisticasEntropiaPortaDestino(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaPortaDestino(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    
    //Insere estatisticas da Entropia do IP de Origem em Ataque
    public synchronized void insertMedidasEstatisticasEntropiaIPOrigemAtaque(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaIPOrigemAtaque(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia do IP de Destino em Ataque
    public synchronized void insertMedidasEstatisticasEntropiaIPDestinoAtaque(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaIPDestinoAtaque(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Origem em Ataque
    public synchronized void insertMedidasEstatisticasEntropiaPortaOrigemAtaque(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaPortaOrigemAtaque(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertMedidasEstatisticasEntropiaPortaDestinoAtaque(Double media, Double variancia, Double desvioPadrao) {
        String sql = "INSERT INTO tabelaEstatisticaPortaDestinoAtaque(Media, Variancia, DesvioPadrao) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, media);
            pstmt.setDouble(2, variancia);
            pstmt.setDouble(3, desvioPadrao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertResultadosIPDestino(Double z, Double ataque, String vitima) {
        String sql = "INSERT INTO RegistroAtaqueIPDestino(Z, Ataque, Vitima) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, z);
            pstmt.setDouble(2, ataque);
            pstmt.setString(3, vitima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
  //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertResultadosIPOrigem(Double z, Double ataque, String vitima) {
        String sql = "INSERT INTO RegistroAtaqueIPOrigem(Z, Ataque, Vitima) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, z);
            pstmt.setDouble(2, ataque);
            pstmt.setString(3, vitima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertResultadosPortaOrigem(Double z, Double ataque, String vitima) {
        String sql = "INSERT INTO RegistroAtaquePortaOrigem(Z, Ataque, Vitima) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, z);
            pstmt.setDouble(2, ataque);
            pstmt.setString(3, vitima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertResultadosPortaDestino(Double z, Double ataque, String vitima) {
        String sql = "INSERT INTO RegistroAtaquePortaDestino(Z, Ataque, Vitima) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, z);
            pstmt.setDouble(2, ataque);
            pstmt.setString(3, vitima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
  //Insere estatisticas da Entropia da Porta de Destino em Ataque
    public synchronized void insertResultadosGeral(Double ataque, String vitima) {
        String sql = "INSERT INTO RegistroAtaqueGeral(Ataque, Vitima) VALUES(?, ?)";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, ataque);
            pstmt.setString(2, vitima);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public synchronized double selectMedidaEntropia(int number, String tabela, String medida){
    	
    	String row = String.valueOf(number);
    	
    	  String sql = "SELECT "+medida+" FROM '"+tabela+"' WHERE rowid="+ row +";" ;
    	  double result = 0;
    	  try (Connection conn = this.connect();
    	             Statement stmt  = conn.createStatement();
    	             ResultSet rs    = stmt.executeQuery(sql)){
    	            
    	            // loop through the result set
    	            while (rs.next()) {
    	            	result = rs.getDouble(medida);
    	            }
    	        } catch (SQLException e) {
    	            System.out.println(e.getMessage());
    	        }
		return result;
    
    
    
    
    }
}

