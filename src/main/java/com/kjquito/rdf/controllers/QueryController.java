package com.kjquito.rdf.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjquito.rdf.services.Query;
import com.kjquito.rdf.entity.*;

@RestController
@RequestMapping("/query")
public class QueryController {

	@Autowired
	private Query queryService;

	@GetMapping("/query1")
	public List<Results1> query1() {
		return queryService.consulta1();
	}

	@GetMapping("/query2")
	public List<Results2> query2() {
		return queryService.consulta2();
	}
	
	@GetMapping("/query3")
	public List<Results3> query3() {
		return queryService.consulta3();
	}
	
	@GetMapping("/query4")
	public List<Results4> query4() {
		return queryService.consulta4();
	}
	
	@GetMapping("/query5")
	public List<Results5> query5() {
		return queryService.consulta5();
	}

}
