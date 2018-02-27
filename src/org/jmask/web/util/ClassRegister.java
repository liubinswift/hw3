package org.jmask.web.util;

import java.lang.reflect.Method;
import java.util.Hashtable;

import com.viewscenes.util.LogTool;

/**
 * 类方法参数注册。暂不支持重写方法的参数获取。要求类的方法名唯一。
 * <p>由于整个服务都是基于反射调用机制，所以，为了快速的确定一个方法的原型，可以考虑将已经确定过的方法原型置于一个静态区域，可以提高访问速度。</p>
 * @author user
 *
 */

public class ClassRegister {

        private static Hashtable<String, String[]> classRegist = new Hashtable <String, String[]> ();

        /**
         * 返回指定方法的参数类型列表
         * @param className 类名
         * @param methodName 方法名
         * @return String[]
         */
        public static String[] getMethodParaType(String className, String methodName){

                String classMethodName = className + "." + methodName;

                String[] methodParas = classRegist.get(classMethodName);

                if( methodParas != null){

                        return methodParas;

                }

                Class cla = null;

                try {

                        cla = Class.forName(className);

                        Method[] methods = cla.getMethods();

                        for(int i = 0; i < methods.length; i++){

                                String method = methods[i].getName();

                                if(methodName.equals(method)){

                                        Class parameterTypes[] = methods[i].getParameterTypes();

                                        methodParas = new String[parameterTypes.length];

                                        for(int t = 0; t < parameterTypes.length; t++){

                                                methodParas[t] = parameterTypes[t].getName();

                                        }

                                        classRegist.put(classMethodName, methodParas);

                                        break;

                                }

                        }

                } catch (Exception e) {

                        LogTool.warning("ClassRegister:getMethodParameter()::" + e.getClass().getName() + "--" + e.toString() );
                }

                return methodParas;

        }

}
