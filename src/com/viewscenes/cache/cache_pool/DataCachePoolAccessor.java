package com.viewscenes.cache.cache_pool;

public interface DataCachePoolAccessor {
  /**
   * ������ȡ�Ķ���ӿڡ�
   * ���ݻ����־���ϴ�����־+1��������ȡ��־��ȡ���ݡ�
   * @param cacherSign String  �����־
   * @param lastRowSign Object �ϴ�+1��������������־
   * @throws Exception
   * @return HashMap[]
   */
  public Object getCacheData(String cacherSign, Object lastRowSign) throws
      Exception;

  /**
   * �������ϱ��Ľӿ�
   * @param cacherSign String �����־��
   * @param rowData HashMap[] Ҫ��ӵ����ݼ�
   * �������������������ݣ���ʼ��ʱ���á�
   * @param rowCoumnSignClass Class  ��������־������
   * @param rowSignColumnName String ��������־����Ӧ��������
   * @throws Exception
   */
  public void addCacheData(String cacherSign, Object rowData,
			   Class rowCoumnSignClass,
			   String rowSignColumnName) throws Exception;

}
