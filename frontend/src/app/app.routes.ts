import { Routes } from '@angular/router';
import { GamesListComponent } from './games-list/games-list.component';
import { LandingComponent } from './landing/landing.component';

export const routes: Routes = [
    {
        path: "home",
        component:LandingComponent,
    },
    {
        path: "",
        redirectTo: "home",
        pathMatch: "full"  
    }, 
    {
        path : 'games',
        component : GamesListComponent
    }
];
