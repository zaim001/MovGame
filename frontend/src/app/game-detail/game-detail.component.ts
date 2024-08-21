import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { GameService } from '../services/game.service';
import { Game } from '../models/game';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game-detail',
  standalone: true,
  imports: [NavbarComponent],
  templateUrl: './game-detail.component.html',
  styleUrl: './game-detail.component.css'
})
export class GameDetailComponent {

  game : Game | undefined;
  games : Game[] = [];

  constructor(private gameService:GameService,private route: ActivatedRoute){}

  ngOnInit(){
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.getGameById(id);
  }

  getGameById(id : number){
    this.gameService.getGameById(id).subscribe(
      (game)=>{this.game = game}
    )
  }

}
