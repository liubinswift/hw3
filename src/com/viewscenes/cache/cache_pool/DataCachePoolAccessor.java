package com.viewscenes.cache.cache_pool;

public interface DataCachePoolAccessor {
  /**
   * 数据提取的对外接口。
   * 根据缓存标志、上次最后标志+1／初次提取标志提取数据。
   * @param cacherSign String  缓存标志
   * @param lastRowSign Object 上次+1／初次行索引标志
   * @throws Exception
   * @return HashMap[]
   */
  public Object getCacheData(String cacherSign, Object lastRowSign) throws
      Exception;

  /**
   * 新数据上报的接口
   * @param cacherSign String 缓存标志。
   * @param rowData HashMap[] 要添加的数据集
   * 以下两参数仅当无数据，初始化时才用。
   * @param rowCoumnSignClass Class  行索引标志类类型
   * @param rowSignColumnName String 行索引标志所对应的列名。
   * @throws Exception
   */
  public void addCacheData(String cacherSign, Object rowData,
			   Class rowCoumnSignClass,
			   String rowSignColumnName) throws Exception;

}
