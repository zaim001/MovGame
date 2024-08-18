package com.movigame.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movigame.entity.Game;

public interface GameRepo extends JpaRepository<Game,Long> {

}
