package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;

/**
 *
 * @author Kornél
 */
public class WallFollowerPlayerImpl implements Player {

    //csak jobbra forduló játékos
    
    private Direction thisWay = Direction.EAST;
    //eltárolja, hogy eddig melyik irányba tartott

    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleMoves = l.possibleMoves();
        //legeneráljuk a lehetséges lépések irányát.

        if (possibleMoves.size() == 1) {
            //ha csak 1 lehetséges irány van, akkor arra felé megyünk
            thisWay = possibleMoves.get(0);
            //az új irány ez lesz
            return thisWay;
        } else if (possibleMoves.size() == 2) {
            if (possibleMoves.contains(thisWay)) {
                //ha 2 lehetséges irány van, akkor megpróbál tovább menni az eddigi irányba
                //...jobban átgondolva itt mindegy hogy mivel próbálkozik, abba az irányba 
                //...kell tovább mennie, ami nem egyezik meg azzal ahonnan jött. ehhez elég 
                //...lenne eltárolni azt, hogy honnan jött, és a másikat választani. már nem írom át
                return thisWay;
            } else {
                //ha nem tud továbbmenni az eddigi irányba
                Direction next = rotate90(thisWay);
                //akkor megpróbál jobbra fordulni.
                if (possibleMoves.contains(next)) {
                    thisWay = next;
                    return thisWay;
                } else {
                    //ha nem tud jobbra fordulni akkor az eddig irányhoz képest balra fordul.
                    //(a most próbált jobb-hoz képest 180°-ot elfordul)
                    thisWay = rotate180(next);
                    return thisWay;
                }
            }
        } else if (possibleMoves.size() == 3) {
            //ha 3 lehetséges irány van akkor megpróbál jobbra fordulni
            Direction next = rotate90(thisWay);
            if (possibleMoves.contains(next)) {
                thisWay = next;
                return thisWay;
            } else {
                //ha nem sikerül jobbra fordulni akkor megy tovább az eddigi irányba
                //ez biztos sikerülni fog, mivel 3 lehetséges irány van.
                return thisWay;
            }
        } else {
            //ha 4 lehetséges irány van akkor bármerre mehet, jobbra megy.
            thisWay = rotate90(thisWay);
            return thisWay;
        }

    }

    private Direction rotate90(Direction thisWay) {
        //az eddigi irányhz képest elfordul jobbra
        Direction next = Direction.EAST;
        switch (thisWay) {
            case EAST:
                next = Direction.SOUTH;
                break;
            case SOUTH:
                next = Direction.WEST;
                break;
            case WEST:
                next = Direction.NORTH;
                break;
            case NORTH:
                next = Direction.EAST;
                break;
        }
        return next;
    }

    private Direction rotate180(Direction thisWay) {
        //az eddigi irányhoz képest 180-at fordul
        Direction next = Direction.EAST;
        switch (thisWay) {
            case EAST:
                next = Direction.WEST;
                break;
            case SOUTH:
                next = Direction.NORTH;
                break;
            case WEST:
                next = Direction.EAST;
                break;
            case NORTH:
                next = Direction.SOUTH;
                break;
        }
        return next;
    }
}
