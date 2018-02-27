/*    */ package org.jmask.web.controller;
/*    */ 
/*    */ import flex.messaging.FlexContext;
/*    */ import flex.messaging.client.FlexClient;
/*    */ import flex.messaging.util.MethodMatcher;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class RPCController
/*    */ {
/*    */   public Object getResult(String mid, String cla, String fun, Object[] param, Boolean isLong)
/*    */   {
/* 11 */     Object ret = null;
/* 12 */     if (isLong.booleanValue()) {
/* 13 */       processThread pt = new processThread(mid, cla, fun, param, FlexContext.getFlexClient().getId());
/* 14 */       pt.start();
/*    */     } else {
/*    */       try {
/* 17 */         Class cl = Class.forName(cla);
/* 18 */         Object obj = cl.newInstance();
/* 19 */         Method method = null;
/*    */         try {
/* 21 */           MethodMatcher mm = new MethodMatcher();
/* 22 */           method = mm.getMethod(cl, fun, Arrays.asList(param));
/*    */         }
/*    */         catch (Exception e) {
/* 25 */           ret = new EXEException("", e.getMessage(), null);
/* 26 */           e.printStackTrace();
/*    */         }
/* 28 */         if (method != null)
/* 29 */           ret = method.invoke(obj, param);
/*    */       }
/*    */       catch (SecurityException e) {
/* 32 */         ret = new EXEException("", e.getMessage(), null);
/* 33 */         e.printStackTrace();
/*    */       }
/*    */       catch (IllegalArgumentException e) {
/* 36 */         ret = new EXEException("", e.getMessage(), null);
/* 37 */         e.printStackTrace();
/*    */       } catch (IllegalAccessException e) {
/* 39 */         ret = new EXEException("", e.getMessage(), null);
/* 40 */         e.printStackTrace();
/*    */       }
/*    */       catch (InvocationTargetException e) {
/* 43 */         ret = new EXEException("", e.getMessage(), null);
/* 44 */         e.printStackTrace();
/*    */       }
/*    */       catch (ClassNotFoundException e) {
/* 47 */         ret = new EXEException("", e.getMessage(), null);
/* 48 */         e.printStackTrace();
/*    */       }
/*    */       catch (InstantiationException e) {
/* 51 */         ret = new EXEException("", e.getMessage(), null);
/* 52 */         e.printStackTrace();
/*    */       } catch (Exception e) {
/* 54 */         ret = new EXEException("", e.getMessage(), null);
/* 55 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 58 */     return ret;
/*    */   }
/*    */ }

