package com.mballem.curso.jasper.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.mballem.curso.jasper.spring.repository.EnderecoRepository;
import com.mballem.curso.jasper.spring.repository.NivelRepository;
import com.mballem.curso.jasper.spring.service.JasperService;

//import net.sf.jasperreports.engine.JRException;

@Controller
public class JasperController {
	
	@Autowired
	private JasperService jasperService;
	
	@Autowired
	private NivelRepository nivelRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@GetMapping("/reports")
	public String abrirReports() {
		return "reports";
	}

	@GetMapping("/relatorio/pdf/jr1")
	public void exberRelatorio(@RequestParam("code") String code,
								@RequestParam("acao") String acao, 
								HttpServletResponse response) throws Exception {
		
		byte[] bytes = jasperService.exportarPDF(code);
		
		response.setContentType(org.springframework.http.MediaType.APPLICATION_PDF_VALUE);
		if(acao.equals("v")) {
			response.setHeader("Content-diposition", "inline; filename=repositorio-"+code+".pdf");
		}else {
			response.setHeader("Content-diposition", "attachment; filename=repositorio-"+code+".pdf");
		}
		
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping("/relatorio/pdf/jr1")
	public void exberRelatorio09(@RequestParam("code") String code,
								@RequestParam(name="nivel") String nivel,
								@RequestParam(name="uf") String uf,
								HttpServletResponse response) throws Exception {
		
		jasperService.addParams("NIVEL_DESC", nivel.isEmpty() ? nivel:nivel);
		jasperService.addParams("UF", uf.isEmpty()? uf:nivel );
		
		byte[] bytes = jasperService.exportarPDF(code);
		response.setContentType(org.springframework.http.MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-diposition", "inline; filename=repositorio-"+code+".pdf");
		response.getOutputStream().write(bytes);
	}
	
	
	@ModelAttribute("niveis")
	public List<String> getNiveis(){
		return nivelRepository.findNiveis();
	}
	
	@ModelAttribute("ufs")
	public List<String> getUfs(){
		return enderecoRepository.findByUfs(); 
	}
}
