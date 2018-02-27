package com.viewscenes.device.framework;


public abstract class BaseTVMessageCommand extends BaseMessageCommand{

    private String type = "TVMon";

    protected String getType(){
        return this.type;
    }
}
