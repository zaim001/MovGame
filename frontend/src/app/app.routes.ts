import { Routes } from '@angular/router';
import { GamesListComponent } from './games-list/games-list.component';
import { LandingComponent } from './landing/landing.component';
import { GameDetailComponent } from './game-detail/game-detail.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

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
    },
    {
        path : 'signin',
        component : LoginComponent
    },
    {
        path : 'register',
        component : RegisterComponent
    }
];
