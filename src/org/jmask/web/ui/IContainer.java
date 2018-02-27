package org.jmask.web.ui;

import java.util.List;
import org.jmask.web.exception.*;

/**
 * <p>Title: Web容器接口</p>
 *
 * <p>Description: 定义Web容器的基本方法，只有实现容器接口的组件才能包含其它组件。</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
public interface IContainer extends IComponent{
        /**
         * 添加一个控件
         * @param component IComponent
         */
        public void addComponent(IComponent component) throws WebAppException;
        /**
         * 从容器中删除一个控件
         * @param id long
         */
        public void removeComponent(String id);
        /**
         * 根据控件ID获取容器中的控件
         * @param id long
         * @return IComponent
         */
        public IComponent getComponent(String id);
        /**
         * 获取容器中所有的控件
         * @return List
         */
        public List getComponents();

}
