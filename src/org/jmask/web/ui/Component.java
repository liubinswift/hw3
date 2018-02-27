package org.jmask.web.ui;

import org.jmask.persist.annotation.*;

/**
 * <p>Title: 控件基类</p>
 *
 * <p>Description: 实现空间接口的基本方法</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
import org.jmask.persist.annotation.*;

public @Exportable class Component implements IComponent{

    public @Persistent String id;

    public @Persistent int left = 0;
    public @Persistent int top = 0;
    public @Persistent int width;
    public @Persistent int height;

    public @Persistent boolean active;
    public @Persistent int tabIndex;
    public @Persistent int zIndex = 1;

    public @Persistent boolean enabled;
    public @Persistent String skin;
    public @Persistent String htmlTemplate;
    public @Persistent String text;

    IContainer container;
    WebApplication app;

    public Component() {
         id = String.valueOf(this.hashCode());
    }
    /**
     * 获得控件实例的id
     * @return long
     */
    public String getID(){
        return id;
    }
    public WebApplication getWebApplication(){
        return app;
    }
    public void setWebApplication(WebApplication application){
        this.app = application;
    }

    /**
     * 获取包含控件的容器
     * @return IContainer
     */
    public IContainer getContainer(){
        return container;
    }
    /**
     * 设置包含控件的容器
     * @param container IContainer
     */
    public void setContainer(Container container){
        this.container = container;
        this.zIndex = container.zIndex + 1;
    }
    /**
     * 将控件的内部数据生成为Xml
     * @return String
         */
         public String toXml(){
             return "";
         }
}
