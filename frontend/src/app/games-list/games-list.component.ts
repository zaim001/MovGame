import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { GameService } from '../services/game.service';
import { CommonModule } from '@angular/common';
import { Game } from '../models/game';

@Component({
  selector: 'app-games-list',
  standalone: true,
  imports: [NavbarComponent,CommonModule],
  templateUrl: './games-list.component.html',
  styleUrl: './games-list.component.css'
})
export class GamesListComponent implements OnInit {

  games: Game[] = [];
  isLoading: boolean = true;  // Add loading state
  // prompt: string = "recommand 2 games like readed redemption. give only titles";

  constructor(private gameService: GameService) { }

  ngOnInit(): void {
    this.getGames();
    
  }

  getGames(): void {
    this.gameService.getGames().subscribe(
      (data: Game[]) => {
        this.games = data;
        this.isLoading = false;

        // Log all games
        console.log('Games:', this.games);

        // Log specific properties
        this.games.forEach(game => {
          console.log('Game Name:', game.name);
          console.log('Released Date:', game.released);
          // console.log('Platforms:', game.platforms.map(p => p.platform.name).join(', '));
          // console.log('Stores:', game.stores.map(s => s.store.name).join(', '));
          console.log('Background Image:', game.backgroundImage);
        });
      },
      error => {
        console.error('Error fetching games', error);
        this.isLoading = false;
      }
    );
  }

}
