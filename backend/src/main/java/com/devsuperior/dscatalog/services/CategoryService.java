package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	//Injeção de dependência = injeta automaticamente uma instância gerenciada pelo Spring
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Método com transação com o BD -> garante a integridade da transação
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = categoryRepository.findAll(pageRequest);
		
		//Convertendo a lista de Category para CategoryDTO com expressão lambda
		//map = transforma cada elemento original de uma lista em uma outra coisa = aplica uma função para cada elemento da lista
		//  -> no caso, está transformando cada elemento x da lista de Category em CategoryDTO
		//Page já é um stream do java 8, então nao precisa utilizar mais o strem nem o collect
		return list.map(x -> new CategoryDTO(x));
		
//		Convertendo a lista de Category para CategoryDTO 
//		List<CategoryDTO> listDto = new ArrayList<>();
//		for(Category cat : list) {
//			listDto.add(new CategoryDTO(cat));
//		}
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		//Optional = evita trabalhar com valor null
		//O retorno de um Optional nunca será null, porém pode conter ou não categorias dentro dele
		Optional<Category> obj = categoryRepository.findById(id);
		//.get = obtem o objeto que está dentro do optional
		//orElseTrhow = se não encontrar uma entidade retorna uma exception
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		CategoryDTO dto = new CategoryDTO(entity);
		return dto;
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		//.save retorna uma referência para a entidade salva
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			//.getOne não vai até o BD, ele apenas instancia um objeto provisório com os dados, os utilizando no BD na hora de realizar o comando final (update)
			Category entity = categoryRepository.getOne(id);
			entity.setName(dto.getName());
			entity = categoryRepository.save(entity);
			return new CategoryDTO(entity);			
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	//Não se utiliza o @Transactional porque é indicado utilizar uma excecão vinda do BD, e se o utilizar nao consegue capturar a mesma
	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			//Caso não encontre o id inserido
			throw new ResourceNotFoundException("Id not found " + id);
		} catch(DatabaseException e) {
			//Caso a categoria com esse id nao possa ser deletada, por possuir produtos cadastrados nesta categoria por exemplo
			throw new DatabaseException("Integrity violation");
		}
	}

}
