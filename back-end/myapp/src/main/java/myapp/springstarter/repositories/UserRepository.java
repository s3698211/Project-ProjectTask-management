package myapp.springstarter.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.springstarter.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User getById(Long id);
	
	
	
}
