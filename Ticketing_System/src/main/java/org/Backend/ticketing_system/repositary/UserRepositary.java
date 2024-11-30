package org.Backend.ticketing_system.repositary;

import org.Backend.ticketing_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositary extends JpaRepository<User, Integer> {

}
