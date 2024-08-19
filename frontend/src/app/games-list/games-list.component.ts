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

  constructor(private gameService: GameService) {}

  ngOnInit(): void {
    this.getGames();
  }
  getGames(){
    this.gameService.getGames().subscribe(
      (data) => {this.games = data}
    )
  }

}
