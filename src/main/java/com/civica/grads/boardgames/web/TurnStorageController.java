
package com.civica.grads.boardgames.web;

import com.civica.grads.boardgames.enums.Colour;
import com.civica.grads.boardgames.enums.CounterType;
import com.civica.grads.boardgames.exceptions.GameException;
import com.civica.grads.boardgames.model.MoveRecord;
import com.civica.grads.boardgames.model.Position;
import com.civica.grads.boardgames.model.TurnRecord;
import com.civica.grads.boardgames.web.model.TurnStorageService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class TurnStorageController {

    @Autowired
    private TurnStorageService turnStorageService;

    @RequestMapping("/")
    @ResponseBody
    ArrayList<TurnRecord> printTurnRecords() throws GameException {

        return turnStorageService.getTurnRecords();
    }

    
    @RequestMapping("/add")
    ArrayList<TurnRecord> addTurn(
            @RequestParam int startPosX, 
            @RequestParam int startPosY , 
            @RequestParam                int newPosX, 
            @RequestParam                int newPosY,
            @RequestParam                String  counterColour,
            @RequestParam                String  counterType,
            @RequestParam                boolean counterTaken 
            )
    {
        MoveRecord move = new MoveRecord(new Position(startPosX,startPosY),
                new Position(newPosX,newPosY)  
                , Colour.valueOf(counterColour), CounterType.valueOf(counterType), counterTaken);
        
        turnStorageService.addMovesToNewTurn(move);
        
        return turnStorageService.getTurnRecords();
    }
    
    @RequestMapping("/addMove")
    void addMoveToLastTurn(
            @RequestParam int startPosX, 
            @RequestParam int startPosY , 
            @RequestParam                int newPosX, 
            @RequestParam                int newPosY,
            @RequestParam                String  counterColour,
            @RequestParam                String  counterType,
            @RequestParam                boolean counterTaken 
            )
    {
    	MoveRecord move = new MoveRecord(new Position(startPosX,startPosY),
                new Position(newPosX,newPosY)  
                , Colour.valueOf(counterColour), CounterType.valueOf(counterType), counterTaken);
    	turnStorageService.getTurnRecords()
    	.get(turnStorageService.getTurnRecords().size() -1).addMoveRecord(move);
    }
    
    
    @RequestMapping("/clear")
    void clearBoard(){
    	turnStorageService.getTurnRecords().clear();
    }
    @RequestMapping("/get")
    TurnRecord getTurn(@RequestParam int index){
    	return turnStorageService.getTurnRecords().get(index);
    }
    


    
}
