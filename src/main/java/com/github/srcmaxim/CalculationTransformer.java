package com.github.srcmaxim;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class CalculationTransformer implements ClassFileTransformer {

  private static final Logger LOGGER = Logger.getLogger(CalculationTransformer.class.getName());
  private static final String ADD_METHOD = "add";

  private final String targetClassName;
  private final ClassLoader targetClassLoader;

  public CalculationTransformer(String targetClassName, ClassLoader targetClassLoader) {
    this.targetClassName = targetClassName;
    this.targetClassLoader = targetClassLoader;
  }

  @Override
  public byte[] transform(
      ClassLoader loader,
      String className,
      Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain,
      byte[] classfileBuffer) {
    String finalTargetClassName = targetClassName.replace('.', '/');
    if (!className.equals(finalTargetClassName) || loader.equals(targetClassLoader)) {
      return classfileBuffer;
    }

    LOGGER.info("[Agent] Transforming class AddApp");
    try {
      var classPool = ClassPool.getDefault();
      var ctClass = classPool.get(targetClassName);
      var ctMethod = ctClass.getDeclaredMethod(ADD_METHOD);
      ctMethod.addLocalVariable("startTime", CtClass.longType);
      ctMethod.insertBefore("startTime = System.currentTimeMillis();");

      ctMethod.addLocalVariable("endTime", CtClass.longType);
      ctMethod.addLocalVariable("opTime", CtClass.longType);
      ctMethod.insertAfter("""
          endTime = System.currentTimeMillis();
          opTime = (endTime-startTime)/1000;
          LOGGER.info("[Application] add completed in:" + opTime + " seconds!");
          """);

      byte[] byteCode = ctClass.toBytecode();
      ctClass.detach();
      return byteCode;
    } catch (NotFoundException | CannotCompileException | IOException e) {
      LOGGER.log(Level.SEVERE, "Exception", e);
    }
    return classfileBuffer;
  }

}
