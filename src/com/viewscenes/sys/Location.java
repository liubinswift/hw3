package com.viewscenes.sys;

public class Location {
  public String id;
  public String parentId;
  public String type;
  public String code;
  public String name;
  public String fullName;
  public String pycode;
  public String gid;
  public boolean isSelected = false;

  public Location() {
  }
//  �������	loc_id	I
//  �ϼ�����ID	Parent_loc_id	I
//  ����	Type	I
//  ����	loc_code	V(4)
//  ��������	name	V(100)
//  ȫ��	loc_name	V(1000)
//  ƴ������	pycode	V(24)
//  GID	gid	V(100)
}
