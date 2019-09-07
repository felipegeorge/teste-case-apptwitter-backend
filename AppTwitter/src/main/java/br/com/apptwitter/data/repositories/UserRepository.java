package br.com.apptwitter.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.apptwitter.data.entities.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
}
