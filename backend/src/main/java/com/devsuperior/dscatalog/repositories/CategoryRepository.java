package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;

@Repository
//<Tipo da entidade, tipo da chave primária>
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
