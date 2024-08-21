package com.movigame.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movigame.entity.GameDetails;

public interface GameDetailsRepo extends JpaRepository<GameDetails,Long> {

}
