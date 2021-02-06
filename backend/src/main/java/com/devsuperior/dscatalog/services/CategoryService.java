package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	//Injeção de dependência = injeta automaticamente uma instância gerenciada pelo Spring
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Método com transação com o BD -> garante a integridade da transação
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		
		//Convertendo a lista de Category para CategoryDTO com expressão lambda
		//.stream = converte o list em stream = recurso que permite trabalhar com funções de alta ordem (expressões lambda)
		//map = transforma cada elemento original de uma lista em uma outra coisa = aplica uma função para cada elemento da lista
		//  -> no caso, está transformando cada elemento x da lista de Category em CategoryDTO
		//.collect(Collectors.toList()) = converter o stream em List novamente
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDto;
		
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
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada"));
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

}
