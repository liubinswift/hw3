package org.jmask.persist.bo;


public abstract class AbstractBo {
  public AbstractBo() {
  }

//  public abstract String getTableName();
//  public abstract String getSeqName();

//  public abstract String getDeleteSql();
//  public abstract String getInsertSql();
//  public abstract String getUpdateSql();

//  public abstract boolean updateFromObj(BaseBo bo);

//  public abstract void getFromGDSet(GDSet dataSet,int row);
//  public abstract boolean save();
//  public abstract boolean delete();
  public abstract String toXml();

}
