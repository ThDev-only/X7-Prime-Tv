package com.ultra.vision.tv.epg;

public class EpgGennerator {
    
    EpgGennerator.searchEpgChannel EPG;
    
    public static interface searchEpgChannel{
        void onSucess();
        void onFailed();
    }
    
    public EpgGennerator(){
        
    }
    
    public void searchChannel(String nome, EpgGennerator.searchEpgChannel epg){
        this.EPG = epg;
    }
}
