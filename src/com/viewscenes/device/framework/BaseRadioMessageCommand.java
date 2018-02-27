package com.viewscenes.device.framework;


public abstract class BaseRadioMessageCommand extends BaseMessageCommand{

    private String type = "Radio";

    protected String getType(){
        return this.type;
    }

}
