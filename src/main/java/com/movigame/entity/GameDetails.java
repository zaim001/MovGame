package com.movigame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GameDetails")
public class GameDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long rawgId;	
	@OneToOne
	@JoinColumn(name = "game_id")
	private Game game;
	private String Description;
	
	private String background_image_additional;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Long getRawgId() {
		return rawgId;
	}

	public void setRawgId(Long rawgId) {
		this.rawgId = rawgId;
	}

	public String getBackground_image_additional() {
		return background_image_additional;
	}

	public void setBackground_image_additional(String background_image_additional) {
		this.background_image_additional = background_image_additional;
	}



	
	
}
