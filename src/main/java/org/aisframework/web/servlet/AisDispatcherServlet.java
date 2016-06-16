package org.aisframework.web.servlet;

import org.aisframework.web.classcollection.ClassCollection;
import org.aisframework.web.structure.MethodPro;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.aisframework.web.test.asmutil;
import  org.aisframework.web.test.test;
import org.aisframework.web.utils.ReflectProcessor;


public class AisDispatcherServlet extends HttpServlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		long start=System.currentTimeMillis();
		ClassCollection.scanClassSetByPackage("org.aisframework.web.test");
		System.out.println("class initalized in " + (System.currentTimeMillis() - start) + " ms");

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo=req.getServletPath();
		Map<String,MethodPro> methodProMap=ClassCollection.getMethodMap();
		Map<String,Class<?>> classMap=ClassCollection.getClassMap();


		Iterator it=methodProMap.entrySet().iterator();

		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			String key= entry.getKey().toString();
			MethodPro value= (MethodPro)entry.getValue();
			if(("/"+key+".do").equals(pathInfo)){
				try {

//					String[] s = asmutil.getMethodParamNames(test.class.getDeclaredMethod("get",String.class)
//

					Map params  = req.getParameterMap();

					Iterator it1 = params.keySet().iterator();
					String paramValue ="";
					while(it1.hasNext()){
						String paramName =(String)it1.next();
						 paramValue = req.getParameter(paramName);
						//处理你得到的参数名与值
						System.out.println(paramName+"="+paramValue);
					}
					//System.out.println(Arrays.toString(s));

					ReflectProcessor.parseMethod(test.class,key,paramValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}


}
