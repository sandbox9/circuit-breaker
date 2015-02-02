package circuitbreaker;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import circuitbreaker.hystrix.SimpleHystrixCommand;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.MethodInfo;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
          
        	@Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

//        		FIXME: 적용대상 설정으로 빼기
                if ("sample/front/service/SampleFrontServiceImpl".equals(s)) {
                	
                    try {
                    	ClassPool cp = ClassPool.getDefault();
                    	CtClass cc = cp.get("sample.front.service.SampleFrontServiceImpl");
                    	
                    	wrappedHystixMethods(cc);
                    	
                    	cc.writeFile();
                    	byte[] bytecode = cc.toBytecode();
                    	cc.detach();
                    	
                    	return bytecode;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                
                return null;
            }

			private void wrappedHystixMethods(CtClass cc) throws CannotCompileException, NotFoundException {
				
				CtMethod[] methods = cc.getDeclaredMethods();
				for(CtMethod method : methods) {
					CtMethod wrappMethod = CtNewMethod.copy(method, cc, null);
					method.setName(method.getName() + "WrappedHystrix");
					StringBuilder methodBody = new StringBuilder();
					methodBody.append("{")
							  .append(" Object[] parameters = $args; ")
							  .append(" Object[] parameterTypes = $sig; ")
							  .append(" return")
							  .append(" ($r) new circuitbreaker.hystrix.SimpleHystrixCommand(this, \"" + method.getName() + "\"");
					if(method.getParameterTypes().length > 0) {
						methodBody.append(", parameters, parameterTypes");
					}
					methodBody.append(").execute();")
							  .append("}");
					
					wrappMethod.setBody(methodBody.toString(), "this", method.getName());
					cc.addMethod(wrappMethod);
				}
			}
        });
    }
}
