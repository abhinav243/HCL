package com.hcl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Question2Repository extends JpaRepository<Question2Model, Long>{

}
