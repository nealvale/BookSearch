
package com.netbrains.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.netbrains.model.Library;

public interface LibraryRepository extends CrudRepository<Library, Long> {

	List<Library> findByLocationContaining(long location);

	Library findById(long id);

}
