package com.movigame.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movigame.entity.Game;

public interface GameRepo extends JpaRepository<Game,Long> {
	Optional<Game> findById(Long id);
}
