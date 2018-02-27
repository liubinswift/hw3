package org.jmask.web.ui;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.jmask.web.exception.WebAppException;
import org.jmask.web.*;

/**
 * <p>Title: 容器基类</p>
 *
 * <p>Description: 实现容器接口的基本方法</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
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
     * 添加一个控件
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
                throw new WebAppException("Web应用添加容器发生异常:容器的ID不能重复！");
        }
    }
    /**
     * 从容器中删除一个控件
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
     * 根据控件ID获取容器中的控件
     * @param id long
     * @return IComponent
     */
    public IComponent getComponent(String id){
        Object obj = componentMap.get(id);
        return (IComponent)obj;
    }
    /**
     * 获取容器中所有的控件
     * @return List
     */
    public List getComponents(){
        return componentList;
    }

}
