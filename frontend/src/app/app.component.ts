import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { GamesListComponent } from './games-list/games-list.component';
import { LandingComponent } from './landing/landing.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterModule,LandingComponent,GamesListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'movgame';
}
