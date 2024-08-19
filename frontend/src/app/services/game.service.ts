import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Game } from '../models/game';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  recommendGames(moviePrompt : string): Observable<Game[]> {
    return this.http.post<Game[]>(`${this.apiUrl}/recommend`, { moviePrompt });
  }

  getGames(): Observable<Game[]>{
    return this.http.get<Game[]>(`${this.apiUrl}/games`)
  }

  
}
