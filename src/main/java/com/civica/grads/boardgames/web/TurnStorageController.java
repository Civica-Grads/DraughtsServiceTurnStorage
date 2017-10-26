package com.civica.grads.boardgames.web;


import com.civica.grads.boardgames.enums.Colour;
import com.civica.grads.boardgames.enums.CounterType;
import com.civica.grads.boardgames.exceptions.GameException;
import com.civica.grads.boardgames.model.MoveRecord;
import com.civica.grads.boardgames.model.Position;
import com.civica.grads.boardgames.model.TurnRecord;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class TurnStorageController {

    
    @Autowired
    private ArrayList<TurnRecord> turnRecords;
    
    
    @RequestMapping("/")
    @ResponseBody
    ArrayList<TurnRecord> printTurnRecords() throws GameException {
    	
    	TurnRecord turnRecord = new TurnRecord();
    	
    	
    	/**
    	 * Turn record 1
    	 */
    	
    	// Sample moves
    	MoveRecord moveRecord = new MoveRecord(new Position(0,0), new Position(3,3),
    			 Colour.BLACK, CounterType.KING, true);
    	MoveRecord moveRecord2 = new MoveRecord(new Position(3,3), new Position(7,7),
   			 Colour.BLACK, CounterType.KING, true);
    	
    	// add sample moves to turn record
    	turnRecord.addMoveRecord(moveRecord);
    	turnRecord.addMoveRecord(moveRecord2);
    	
    	/** 
    	 * Turn record 2
    	 */
    	
    	TurnRecord turnRecord2 = new TurnRecord();
    	// Sample move
    	MoveRecord moveRecord3 = new MoveRecord(new Position(2,5), new Position(3,6),
    			Colour.WHITE, CounterType.NORMAL, false);
    	
    	turnRecord2.addMoveRecord(moveRecord3);
    			
    	
    	turnRecords.add(turnRecord);
    	turnRecords.add(turnRecord2);
    	
        //StringBuilder builder = new StringBuilder();
       // ArrayList<MoveRecord> moveRecords = turnRecord.getMoveRecordArrayList();
        
        
        /**
         * string building commented out since we're using json
        builder.append("This turn contains following properties\n");
        
        for (int i=1;i<=moveRecords.size(); i++){
        	Position start = moveRecords.get(i-1).getPositionStart();
        	Position finish = moveRecords.get(i-1).getPositionFinish();
        	builder.append("Move " + i + "\n" + 
        			"Start: (" + start.getX() + "," + 
        			start.getY() + ")\n" + 
        			"Finish: (" + finish.getX() + "," + 
        			finish.getY() + ")\n");
        }
        builder.append("Counters taken: ");
        builder.append(turnRecord.getCountersTakenThisTurn());
        
        String pageHtml = String.format("<html><body><pre>%s</pre></body></html>", builder.toString());
        */
    	
        return turnRecords;
    }

}