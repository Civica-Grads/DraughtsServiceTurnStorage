package com.civica.grads.boardgames.web;

import com.civica.grads.boardgames.display.StringBufferBoardRenderer;
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
    private TurnRecord turnRecord;
    
    
    @RequestMapping("/")
    @ResponseBody
    String printTurnRecords() throws GameException {
    	
    	
    	// Sample moves
    	MoveRecord moveRecord = new MoveRecord(new Position(0,0), new Position(3,3),
    			 Colour.BLACK, CounterType.KING, true);
    	MoveRecord moveRecord2 = new MoveRecord(new Position(3,3), new Position(7,7),
   			 Colour.BLACK, CounterType.KING, true);
    	
    	// add sample moves to turn record
    	turnRecord.addMoveRecord(moveRecord);
    	turnRecord.addMoveRecord(moveRecord2);
    	
        StringBuilder builder = new StringBuilder();
        ArrayList<MoveRecord> moveRecords = turnRecord.getMoveRecordArrayList();
        
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
        
        return pageHtml ;
    }

}