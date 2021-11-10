package com.revature.repositories;

import com.revature.models.Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepo extends JpaRepository<Info, Integer> {
}
