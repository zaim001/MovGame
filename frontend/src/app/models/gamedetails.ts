import { Developer } from "./developer";
import { Game } from "./game";

export interface GameDetails{
    id: number;
    rawgId: number;
    game : Game;
    description : string;
    background_image_additional : string;
    website: string;
    trailer: string;
    devs : Developer[];
}