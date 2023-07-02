package com.example.soundb;

public class SoundObject {
    private String itemName;
    private Integer itemID;

    SoundObject(String itemName,Integer itemID){
        this.itemID=itemID;
        this.itemName=itemName;

    }
    public String getItemName(){
        return itemName;
    }
    public Integer getItemID(){
        return itemID;
    }
}
