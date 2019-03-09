/**
 * 
 */
package com.dineshonjava.prodos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dineshonjava.prodos.domain.ProdosUser;

/**
 * @author Dinesh.Rajput
 *
 */
public interface ProdosUserRepository extends CrudRepository<ProdosUser, Long> {
	
	Optional<ProdosUser> findByUsername(String username);
}
