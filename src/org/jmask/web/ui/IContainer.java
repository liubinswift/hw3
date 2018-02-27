package org.jmask.web.ui;

import java.util.List;
import org.jmask.web.exception.*;

/**
 * <p>Title: Web�����ӿ�</p>
 *
 * <p>Description: ����Web�����Ļ���������ֻ��ʵ�������ӿڵ�������ܰ������������</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
 * @version 1.0
 */
public interface IContainer extends IComponent{
        /**
         * ���һ���ؼ�
         * @param component IComponent
         */
        public void addComponent(IComponent component) throws WebAppException;
        /**
         * ��������ɾ��һ���ؼ�
         * @param id long
         */
        public void removeComponent(String id);
        /**
         * ���ݿؼ�ID��ȡ�����еĿؼ�
         * @param id long
         * @return IComponent
         */
        public IComponent getComponent(String id);
        /**
         * ��ȡ���������еĿؼ�
         * @return List
         */
        public List getComponents();

}
