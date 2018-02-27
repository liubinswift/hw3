package org.jmask.web.ui;

import org.jmask.persist.annotation.*;

/**
 * <p>Title: �ؼ�����</p>
 *
 * <p>Description: ʵ�ֿռ�ӿڵĻ�������</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
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
     * ��ÿؼ�ʵ����id
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
     * ��ȡ�����ؼ�������
     * @return IContainer
     */
    public IContainer getContainer(){
        return container;
    }
    /**
     * ���ð����ؼ�������
     * @param container IContainer
     */
    public void setContainer(Container container){
        this.container = container;
        this.zIndex = container.zIndex + 1;
    }
    /**
     * ���ؼ����ڲ���������ΪXml
     * @return String
         */
         public String toXml(){
             return "";
         }
}
