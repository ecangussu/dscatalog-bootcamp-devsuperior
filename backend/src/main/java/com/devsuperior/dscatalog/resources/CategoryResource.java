package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;

//Está classe é um recurso da entidade Category
//Disponibiliza os recursos da aplicação relacionados a Category de acordo com as requisições solicitadas

//Configuração no spring para informar que a classe é um controlador rest
@RestController
//Rota rest do recurso
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	//Endpoint para buscar todas as categorias
	//RespondeEntity = objeto do spring que encapsula uma resposta http
	//<> Tipo do dado que estará no corpo dessa resposta
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = new ArrayList<>(); 
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		//Retornar a lista no corpo da resposta http desta requisição
		//.ok = resposta 200
		return ResponseEntity.ok().body(list);
	}

}