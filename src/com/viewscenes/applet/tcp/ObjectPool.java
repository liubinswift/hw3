package com.viewscenes.applet.tcp;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectPool {
    int m_iCount = 0;
    public ObjectPool() {
        super();
    }

    public ArrayList freeObjList = new ArrayList();
    public HashMap usedObjMap = new HashMap();

    public synchronized int getFreeObj() {
        if (freeObjList.size() == 0) {
            System.out.println("对象池中没有足够的对象！");
            return -1;
        }
        m_iCount++;
        if (m_iCount > Integer.MAX_VALUE - 1)
            m_iCount = 0;
        Object obj = freeObjList.get(0);
        freeObjList.remove(0);
        usedObjMap.put(new Integer(m_iCount), obj);
        return m_iCount;
    }

    public synchronized void releaseObj(int i) {
        Integer temp = new Integer(i);
        Object obj = usedObjMap.get(temp);
        if (obj!=null){
            usedObjMap.remove(temp);
            freeObjList.add(obj);
        }
    }

    public Object getObj(int i){
        Integer temp = new Integer(i);
        Object obj = usedObjMap.get(temp);
        return obj;
    }

    public void addFreeObj(Object obj){
        freeObjList.add(obj);
    }
}

