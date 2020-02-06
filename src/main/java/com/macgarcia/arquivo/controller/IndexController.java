package com.macgarcia.arquivo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.macgarcia.arquivo.Repository.ArquivoRepository;
import com.macgarcia.arquivo.model.Arquivo;

@Controller
public class IndexController {

	@Autowired
	ArquivoRepository dao;

	@GetMapping(value = "/")
	public String iniciar() {
		return "redirect:/telaIndex";
	}
	
	@GetMapping(value = "/telaIndex")
	public ModelAndView getTela() {
		ModelAndView mv = new ModelAndView("index");
		List<Arquivo> arquivos = this.dao.findAll();
		mv.addObject("arquivos", arquivos);
		return mv;
	}

	@PostMapping(value = "/upload")
	public String uploadDoArquivo(@RequestParam("nome") String nome, @RequestParam("arquivo") Part arquivo) {
		try {
			InputStream is = arquivo.getInputStream();
			int count = 0;
			int index = 0;
			byte[] b = new byte[(int) arquivo.getSize()];
			while (count < b.length && (index = is.read(b, count, b.length - count)) >= 0) {
				count += index;
			}
			Arquivo a = new Arquivo();
			a.setNome(nome);
			a.setArquivo(b);
			this.dao.saveAndFlush(a);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}

	@GetMapping(value = "/pegarArquivo/{id}")
	public String pegarArquivo(@PathVariable("id") Long id, HttpServletResponse response) {
		Optional<Arquivo> a = this.dao.findById(id);
		if (a.isPresent()) {
			if (a.get().getArquivo() != null) {
				byte[] arquivo = a.get().getArquivo();
		        response.setContentType("image/png");
		        response.setHeader("Content-Disposition", "attachment; filename=Foto.png");
				try {
					OutputStream output = response.getOutputStream();
					output.write(arquivo);
					output.flush();
					output.close();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return "redirect:/";
	}

}
