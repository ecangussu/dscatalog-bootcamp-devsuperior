package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

//Está classe é um recurso da entidade Category
//Disponibiliza os recursos da aplicação relacionados a Category de acordo com as requisições solicitadas

//Configuração no spring para informar que a classe é um controlador rest
@RestController
//Rota rest do recurso
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	//Endpoint para buscar todas as categorias
	//RespondeEntity = objeto do spring que encapsula uma resposta http
	//<> Tipo do dado que estará no corpo dessa resposta
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = categoryService.findAll();
		//Retornar a lista no corpo da resposta http desta requisição
		//.ok = resposta 200
		return ResponseEntity.ok().body(list);
	}
	
	//Endpoint para buscar as categorias por id
	//@PathVariable = pra associar o {id} da rota com o id que virá como parametro no método
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = categoryService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	//Endpoint para inserir numa categoria
	//@PostMapping = no padrão request ao inserir um novo recurso se usa o método post
	//@RequestBody = para que o endpoint reconheça que o objeto que será enviado na requisição case com o CategoryDTO dto
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = categoryService.insert(dto);
		//Criação de um parâmetro adicional no cabeçalho da resposta com o endereço desse novo recurso criado (padrão REST)
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		//.created(uri) = código 201 = recurso criado
		return ResponseEntity.created(uri).body(dto);
		
	}

}