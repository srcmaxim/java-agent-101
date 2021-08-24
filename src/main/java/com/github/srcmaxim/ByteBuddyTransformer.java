package com.github.srcmaxim;

import java.lang.instrument.ClassFileTransformer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class ByteBuddyTransformer implements ClassFileTransformer {

  private static final Logger LOGGER = Logger.getLogger(ByteBuddyTransformer.class.getName());
  private static final String ADD_METHOD = "add";

  public static ClassFileTransformer createClassFileTransformer(String className) {
    return new AgentBuilder.Default()
        .type(ElementMatchers.nameStartsWith("com.github.srcmaxim." + className))
        .transform(((builder, typeDescription, classLoader, module) -> {
          LOGGER.log(Level.FINE, "[Agent] Transforming: " + className);
          return builder
              .visit(Advice.to(ByteBuddyAdvice.class)
                  .on(methodDescription -> ADD_METHOD.equals(methodDescription.getName())));
        }))
        .makeRaw();
  }

}
