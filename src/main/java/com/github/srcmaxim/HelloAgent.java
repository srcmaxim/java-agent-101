package com.github.srcmaxim;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloAgent {

  private static final Logger LOGGER = Logger.getLogger(HelloAgent.class.getName());

  public static void premain(String agentArgs, Instrumentation inst) {
    LOGGER.info("[Agent] In premain method");
    String className = "com.github.srcmaxim.AddApp";
    transformClass(className, inst);
  }

  public static void agentmain(String agentArgs, Instrumentation inst) {
    LOGGER.info("[Agent] In agentmain method");
    String className = "com.baeldung.instrumentation.application.MyAtm";
    transformClass(className, inst);
  }

  private static void transformClass(String className, Instrumentation instrumentation) {
    Class<?> targetCls = null;
    ClassLoader targetClassLoader = null;
    // see if we can get the class using forName
    try {
      targetCls = Class.forName(className);
      targetClassLoader = targetCls.getClassLoader();
      transform(targetCls, targetClassLoader, instrumentation);
      return;
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Class [{}] not found with Class.forName", ex);
    }
    // otherwise iterate all loaded classes and find what we want
    for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
      if (clazz.getName().equals(className)) {
        targetCls = clazz;
        targetClassLoader = targetCls.getClassLoader();
        transform(targetCls, targetClassLoader, instrumentation);
        return;
      }
    }
    throw new RuntimeException("Failed to find class [" + className + "]");
  }

  private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation) {
    ClassFileTransformer dt1 = new JavaAssistTransformer(clazz.getName(), classLoader);
    instrumentation.addTransformer(dt1, true);
    ClassFileTransformer dt2 = ByteBuddyTransformer.createClassFileTransformer(clazz.getName());
    instrumentation.addTransformer(dt2, true);
    try {
      instrumentation.retransformClasses(clazz);
    } catch (Exception ex) {
      throw new RuntimeException("Transform failed for: [" + clazz.getName() + "]", ex);
    }
  }

}
