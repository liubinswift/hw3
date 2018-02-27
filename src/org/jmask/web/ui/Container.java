package org.jmask.web.ui;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.jmask.web.exception.WebAppException;
import org.jmask.web.*;

/**
 * <p>Title: ��������</p>
 *
 * <p>Description: ʵ�������ӿڵĻ�������</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
 * @version 1.0
 */
public class Container extends Component implements IContainer{



    ArrayList componentList;
    HashMap componentMap;

    public Container() {
        componentList = new ArrayList();
        componentMap = new HashMap();
    }


    /**
     * ���һ���ؼ�
     * @param component IComponent
     */
    public void addComponent(IComponent component) throws WebAppException{
        if (component==null)
            return;
        Object obj = componentMap.get(String.valueOf(component.getID()));
        if (obj==null){
            componentMap.put(id,component);
            componentList.add(component);
        }
        else{
            IContainer oldContainer = (IContainer)obj;
            if (oldContainer==container)
                return;
            else
                throw new WebAppException("WebӦ��������������쳣:������ID�����ظ���");
        }
    }
    /**
     * ��������ɾ��һ���ؼ�
     * @param id long
     */
    public void removeComponent(String id){
        Object obj = componentMap.get(id);
        if (obj!=null){
            componentMap.remove(id);
            boolean bFound = false;
            int i=0;
            while (!bFound){
                IComponent subComponent = (IComponent)componentList.get(i);
                if (subComponent.getID().equals(id)){
                    bFound = true;
                    componentList.remove(i);
                }
                else
                    i++;
            }
        }
    }
    /**
     * ���ݿؼ�ID��ȡ�����еĿؼ�
     * @param id long
     * @return IComponent
     */
    public IComponent getComponent(String id){
        Object obj = componentMap.get(id);
        return (IComponent)obj;
    }
    /**
     * ��ȡ���������еĿؼ�
     * @return List
     */
    public List getComponents(){
        return componentList;
    }

}
