package org.jmask.web.ui;

/**
 * <p>Title:Web����ؼ��ӿ� </p>
 *
 * <p>Description: ����Web�ؼ��Ļ�������</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ʯ</p>
 *
 * @author �¸�
 * @version 1.0
 */
public interface IComponent {

        /**
         * ��ÿؼ�ʵ����id
         * @return long
         */
        public String getID();
        /**
         * ��ȡ�����ؼ�������
         * @return IContainer
         */
        public IContainer getContainer();
        /**
         * ���ð����ؼ�������
         * @param container IContainer
         */
        public void setContainer(Container container);
        /**
         * ���ؼ����ڲ���������ΪXml
         * @return String
         */
         public String toXml();


}
