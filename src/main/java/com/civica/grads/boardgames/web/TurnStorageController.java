package com.civica.grads.boardgames.web;

import com.civica.grads.boardgames.display.StringBufferBoardRenderer;
import com.civica.grads.boardgames.exceptions.GameException;
import com.civica.grads.boardgames.model.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardDisplayController {

    
    @Autowired
    private Board board;
    
    
    @RequestMapping("/")
    @ResponseBody
    String renderBoard() throws GameException {
        
        StringBufferBoardRenderer boardRender = new StringBufferBoardRenderer();
        boardRender.render(board);
        
        String pageHtml = String.format("<html><body><pre>%s</pre></body></html>", boardRender.asString());
        
        return pageHtml ;
    }

}