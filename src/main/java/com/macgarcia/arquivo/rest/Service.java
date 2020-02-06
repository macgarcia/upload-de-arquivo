package com.macgarcia.arquivo.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macgarcia.arquivo.Repository.ArquivoRepository;
import com.macgarcia.arquivo.model.Arquivo;

@RestController
@RequestMapping(value = "/arquivo")
public class Service {
	
	@Autowired
	ArquivoRepository dao;

	@GetMapping(value = "/arq/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Arquivo> getArquivo(@PathVariable("id") Long id) {
		Optional<Arquivo> arquivo = this.dao.findById(id);
		if (arquivo.isPresent()) {
			return new ResponseEntity<Arquivo>(arquivo.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Arquivo>(new Arquivo(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
