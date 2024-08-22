package com.movigame.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rawgId;

    private String name;
    private List<String> plateforms;
    private String released;
    private String backgroundImage;
    private Double rating;
    private List<String> genres;
    private List<String> shortScreenshots;
    
    
    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private GameDetails gameDetails;
    
    
	public GameDetails getGameDetails() {
		return gameDetails;
	}
	public void setGameDetails(GameDetails gameDetails) {
		this.gameDetails = gameDetails;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<String> getPlateforms() {
		return plateforms;
	}
	public void setPlateforms(List<String> plateforms) {
		this.plateforms = plateforms;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getRawgId() {
		return rawgId;
	}
	public void setRawgId(Long rawgId) {
		this.rawgId = rawgId;
	}
	public List<String> getShortScreenshots() {
		return shortScreenshots;
	}
	public void setShortScreenshots(List<String> shortScreenshots) {
		this.shortScreenshots = shortScreenshots;
	}
    
    
}
