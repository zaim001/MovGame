package com.movigame.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movigame.entity.Developer;

public interface DeveloperRepo extends JpaRepository<Developer, Long> {

}
