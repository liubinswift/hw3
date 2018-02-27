package org.jmask.util;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;

import org.jmask.persist.annotation.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ObjectToXml {
    StringBuffer buffer = null;
    private Collection<Object>  amVisiting      = new ArrayList<Object>();

    public ObjectToXml() {
    }

    public String toXml( Object obj ) throws IOException
    {
        buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"GB2312\" standalone=\"yes\"?>\n");
        flush(obj, null, null );
        return buffer.toString();
    }

    public void flush( Object obj, String nameAttribute, String fallbackElementName ) throws IOException
    {
        if (obj==null)
            return;

        if( amVisiting.contains(obj) )  // Cyclic object graph.
            return;                     // Silently ignore. cycles.

        amVisiting.add(obj);

        if( obj instanceof Map )
        {   for( Iterator i = ((Map)obj).keySet().iterator(); i.hasNext(); )
            {   Object element = i.next();
                flush( ((Map)obj).get(element), element.toString(), null );
            }
        }
        else if( obj instanceof Collection )
        {   for( Iterator i = ((Collection)obj).iterator(); i.hasNext(); )
                toXml( i.next() );
        }
        else
        {
            if( fallbackElementName == null )
                fallbackElementName = extractNameFromClass( obj );

            String elementName = fallbackElementName;

            buffer.append(
                     "<"
                    + elementName
                    + (" className=\"" + obj.getClass().getName()+"\"" )
                    + (nameAttribute == null ? " " :
                             (" name=\"" + nameAttribute +"\"") )
                    + ">\n" );

                flushFields( obj );     // then process its fields.

            buffer.append( "</" + elementName + ">\n");
        }

        amVisiting.remove(obj);
    }

    private void flushFields( Object obj ) throws IOException
    {   try
        {
            Field[] fields = obj.getClass().getFields();
            for( Field f : fields )
            {
                Persistent annotation = f.getAnnotation( Persistent.class );
                if( annotation == null )
                    continue;

                f.setAccessible(true); // Make private fields accessible.

                String value = null;
                Class  type = f.getType();
                if(type == byte.class  ) value=Byte.toString    ( f.getByte(obj) );
                else if(type == short.class ) value=Short.toString   ( f.getShort(obj) );
                else if(type == char.class  ) value=Character.toString( f.getChar(obj) );
                else if(type == int.class   ) value=Integer.toString ( f.getInt(obj) );
                else if(type == long.class  ) value=Long.toString    ( f.getLong(obj) );
                else if(type == float.class ) value=Float.toString   ( f.getFloat(obj) );
                else if(type == double.class) value=Double.toString  ( f.getDouble(obj) );
                else if(type == Boolean.class) value=Boolean.toString  ( f.getBoolean(obj) );
                else if(type == String.class) value= (String)        ( f.get(obj) );


                // If an element name is specified in the annotation, use it.
                // Otherwise, use the field name as the element name.
                String name = annotation.value();
                if( name.length() == 0 )
                    name = f.getName();

                if(value != null)   // Then it's a primitive type or a String.
                {   buffer.append ("<" + name + ">");
                    buffer.append( value );
                    buffer.append("</" + name + ">\r\n");
                }
                else if(  f.get(obj) instanceof Collection
                        || f.get(obj) instanceof Map )
                {   buffer.append ("<" + name + ">");
                    flush( f.get(obj), f.getName(), null );
                    buffer.append("</" + name + ">\r\n");
                }
                else
                {
                    if( type.getAnnotation(Exportable.class) != null )
                        flush( f.get(obj), f.getName(), null );
                }
            }
        }
        catch( IllegalAccessException e )   // Shouldn't happen
        {   assert false : "Unexpected exception:\n" + e ;
        }
    }

    /** Get the class name from the prefix. If the fully-qualified name
     *  contains a $, assume it's an inner class and the class name is
     *  everything to the right of the rightmost $. Otherwise, if the
     *  fully qualified name has a dot, then the class name is everything
     *  to the right of the rightmost dot. Otherwise, the name is the
     *  string returned from getClass().getName().
     *
     * @param obj
     * @return
     */
    private String extractNameFromClass( Object obj )
    {   String name = obj.getClass().getName();
        int index;
        if( (index = name.lastIndexOf('$')) != -1 )
            return name.substring( index + 1 );

        if( (index = name.lastIndexOf('.')) != -1 )
            return name.substring( index + 1 );

        return name;
    }
}



