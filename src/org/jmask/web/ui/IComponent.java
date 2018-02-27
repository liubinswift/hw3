package org.jmask.web.ui;

/**
 * <p>Title:Web界面控件接口 </p>
 *
 * <p>Description: 定义Web控件的基本方法</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: 金石</p>
 *
 * @author 陈刚
 * @version 1.0
 */
public interface IComponent {

        /**
         * 获得控件实例的id
         * @return long
         */
        public String getID();
        /**
         * 获取包含控件的容器
         * @return IContainer
         */
        public IContainer getContainer();
        /**
         * 设置包含控件的容器
         * @param container IContainer
         */
        public void setContainer(Container container);
        /**
         * 将控件的内部数据生成为Xml
         * @return String
         */
         public String toXml();


}
