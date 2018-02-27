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
//  地域编码	loc_id	I
//  上级地域ID	Parent_loc_id	I
//  类型	Type	I
//  代码	loc_code	V(4)
//  地区名称	name	V(100)
//  全称	loc_name	V(1000)
//  拼音代码	pycode	V(24)
//  GID	gid	V(100)
}
