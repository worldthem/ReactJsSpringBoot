package com.jcom.cms.repository;


import com.jcom.cms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
         Role findFirstByName(String name);
}