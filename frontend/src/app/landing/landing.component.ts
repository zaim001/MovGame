import { Component, OnInit } from '@angular/core';
import { Game } from '../models/game';
import { GameService } from '../services/game.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  loading : boolean = false;
  moviePrompt : string = ''
  games : Game[] = []


  constructor(private gameService : GameService, private router : Router){}

  getRecommendations(){
    if(this.moviePrompt){
      this.loading = true;
      this.gameService.recommendGames(this.moviePrompt).subscribe(
        (data) => {
          this.games = data ;
           this.router.navigate(['/games'])},
        (error) => {
          console.error('Error fetching game recommendations:', error);
        }
      )
    }
    else{alert("Enter Movie name")}
    }
   


}
