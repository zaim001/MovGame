import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { GameService } from '../services/game.service';
import { Game } from '../models/game';
import { ActivatedRoute } from '@angular/router';
import { GameDetails } from '../models/gamedetails';
import { DomSanitizer } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-game-detail',
  standalone: true,
  imports: [NavbarComponent,CommonModule],
  templateUrl: './game-detail.component.html',
  styleUrl: './game-detail.component.css'
})
export class GameDetailComponent {

  gameDetails!: GameDetails;

  constructor(private gameService:GameService,private route: ActivatedRoute, private sanitizer: DomSanitizer){}

  ngOnInit(){
    const rawgId = Number(this.route.snapshot.paramMap.get('rawgId'));
    this.getGameByRawgId(rawgId);
  }

  getGameByRawgId(rawgId : number){
    this.gameService.getGameDetails(rawgId).subscribe(
      (details)=>{this.gameDetails = details}
    )
  }
  getSafeUrl(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
    
}
