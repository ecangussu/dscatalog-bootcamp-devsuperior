package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	//Get = padrão rest para buscar uma categoria
	//RespondeEntity = objeto do spring que encapsula uma resposta http
	//<> Tipo do dado que estará no corpo dessa resposta
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<CategoryDTO> list = categoryService.findAllPaged(pageRequest);
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
	//Post = no padrão rest ao inserir um novo recurso se usa o método post
	//@RequestBody = para que o endpoint reconheça que o objeto que será enviado na requisição (corpo da requisição) case com o CategoryDTO dto
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = categoryService.insert(dto);
		//Criação de um parâmetro adicional no cabeçalho da resposta com o endereço desse novo recurso criado (padrão REST)
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		//.created(uri) = código 201 = recurso criado
		return ResponseEntity.created(uri).body(dto);		
	}
	
	
	//Endpoint para atualizar uma categoria
	//Se utiliza o método Put
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = categoryService.update(id, dto);
		return ResponseEntity.ok().body(dto);		
	}
	
	//Endpoint para deletar uma categoria
	//Se utiliza o método Delete
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
		categoryService.delete(id);
		//Resposta 204 = código HTTP que indica que resposta deu certo = corpo da resposta está vazio
		return ResponseEntity.noContent().build();		
	}

}