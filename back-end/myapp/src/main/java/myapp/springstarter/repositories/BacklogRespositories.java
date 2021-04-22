package myapp.springstarter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.springstarter.domain.Backlog;

@Repository
public interface BacklogRespositories extends CrudRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String identifier);
}
