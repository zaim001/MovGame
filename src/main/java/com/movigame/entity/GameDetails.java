package com.movigame.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
	@OneToMany(mappedBy = "gameDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Developer> devs;
	
	private String Description;
	private String website;
	
	private String background_image_additional;
	
	private String trailer;
	private List<String> developerName;
	
	

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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public List<String> getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(List<String> developerName) {
		this.developerName = developerName;
	}
	
	public List<Developer> getDevs() {
		return devs;
	}

	public void setDevs(List<Developer> devs) {
		this.devs = devs;
	}
	


	
	
}
