package com.ultra.vision.connection;

import java.util.List;

public class Testando {
    
	public static getCanais GET_CANAIS;
	
    public interface getCanais {
		public void pegueiOsCanaisMano(List<String> nome, List<String> context, List<String> logo, List<String> link);
	}
    
	public void get(getCanais get){
		this.GET_CANAIS = get;
	}
}
