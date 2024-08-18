import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Game } from '../models/game';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private generatedGames: Game[] = [];
  
  private apiUrl = 'http://localhost:8080/recommend';

  constructor(private http: HttpClient) { }

  getGames(moviePrompt : string): Observable<Game[]> {
    return this.http.post<Game[]>(this.apiUrl, { moviePrompt });
  }

  setGames(games: Game[]): void {
    this.generatedGames = games;
  }

  getStoredGames(): Game[] {
    return this.generatedGames;
  }
  
}
