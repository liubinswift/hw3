package com.viewscenes.device.framework;


public abstract class BaseCommonMessageCommand extends BaseMessageCommand{

    private String type = "Common";

    protected String getType(){
        return this.type;
    }

}
