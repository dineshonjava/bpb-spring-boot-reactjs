/**
 * 
 */
package com.dineshonjava.prodos.repository;

import org.springframework.data.repository.CrudRepository;

import com.dineshonjava.prodos.domain.ProdosUser;

/**
 * @author Dinesh.Rajput
 *
 */
public interface ProdosUserRepository extends CrudRepository<ProdosUser, Long> {
	
	ProdosUser findByUsername(String username);
}
