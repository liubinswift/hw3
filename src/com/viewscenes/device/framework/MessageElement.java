package com.viewscenes.device.framework;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MessageElement
{
  private String name;
  private Map attributes;
  private Collection children;
  public static final MessageElement EMPTY_ELEMENT;


  public MessageElement(String name, Map attrs, Collection children)
  {
    this.name = name;
    this.attributes = attrs;
    this.children = children;
  }

  public String getName() {
    return this.name;
  }

  public Map getAttributes() {
    return this.attributes;
  }

  public Collection getChildren() {
    return this.children;
  }

  public String getAttributeValue(String name) {
    if ( (name == null)) throw new AssertionError();
    boolean containsName = (this.attributes != null) && (this.attributes.containsKey(name));
    return ((containsName) ? (String)this.attributes.get(name) : "");
  }

  public MessageElement getChildValue(String name) {
    if ( (name == null)) throw new AssertionError();
    if ((this.children == null) || (this.children.isEmpty())) {
      return EMPTY_ELEMENT;
    }
    for (Iterator it = this.children.iterator(); it.hasNext(); ) {
      MessageElement child = (MessageElement)it.next();
      if (name.equals(child.getName())) {
        return child;
      }
    }
    return EMPTY_ELEMENT;
  }

  static
  {
    EMPTY_ELEMENT = new MessageElement("", null, null);
  }
}
