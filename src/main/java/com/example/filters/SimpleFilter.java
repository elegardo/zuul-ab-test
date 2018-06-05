package com.example.filters;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class SimpleFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

	@Autowired
	private TestBProperties testB;
	
	@Value("${testa.url}")
	private String testaURL;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		
		String rut = RequestContext.getCurrentContext().getRequest().getParameter("rut");
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

		try {
			if (redirectTestB(rut)) {
				response.sendRedirect(testB.getUrl());
				log.info("--->"+rut+":testb:"+testB.getUrl());
			}
			else {
				response.sendRedirect(testaURL);
				log.info("--->"+rut+":testa:"+testaURL);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean redirectTestB(String rut) {
		
		log.info("***************************");
		//java7
		log.info("--->Inicia Recorre ruts");
		for (Map.Entry<String, String> entry : testB.getRuts().entrySet()) {
			log.info(entry.getKey()+":"+entry.getValue());
			if(rut.equals(entry.getValue())) {
				log.info("--->Coincidencia rut="+entry.getValue());
				return true;
			}
		}
		log.info("--->Termina Recorre ruts");
		log.info("--->No encontro coincidencias para rut="+rut);
		return false;
		
		//java8
//		String result = testB.getRuts().entrySet().stream()
//								.filter(x -> {
//										if (x.getValue().contains(rut)) {
//											return true;
//										}
//										return false;
//								})
//								.map(map -> map.getValue())
//								.collect(Collectors.joining());
//
//		return !result.isEmpty();
	}
	
}
