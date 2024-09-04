import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Game } from '../models/game';
import { GameDetails } from '../models/gamedetails';
import { environment } from '../../env/env.prod';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  recommendGames(moviePrompt : string): Observable<Game[]> {
    return this.http.post<Game[]>(`${this.apiUrl}/recommend`, { moviePrompt });
  }

  getGames(): Observable<Game[]>{
    return this.http.get<Game[]>(`${this.apiUrl}/games`)
  }
  getGameById(id: number) : Observable<Game>{
    return this.http.get<Game>(`${this.apiUrl}/games/${id}`)
  }

  getGameDetails(rawgid: number) : Observable<GameDetails>{
    return this.http.get<GameDetails>(`${this.apiUrl}/games/details/${rawgid}`)
  }


  
}
