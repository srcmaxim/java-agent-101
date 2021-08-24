package com.github.srcmaxim;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.bytebuddy.asm.Advice.OnMethodEnter;
import net.bytebuddy.asm.Advice.OnMethodExit;
import net.bytebuddy.asm.Advice.Origin;

public class ByteBuddyAdvice {

  private static final Logger LOGGER = Logger.getLogger(HelloAgent.class.getName());

  @OnMethodEnter
  public static void enter(@Origin Class klass, @Origin("#m") String methodName) {
    LOGGER.log(Level.FINE, "[ByteBuddy Advice] enter class {0} method {1}",
        new Object[]{klass.getSimpleName(), methodName});
  }

  @OnMethodExit
  public static void exit() {
    LOGGER.log(Level.FINE, "[ByteBuddy Advice] exit class");
  }

}
