
package com.civica.grads.boardgames.web;

import com.civica.grads.boardgames.exceptions.GameException;
import com.civica.grads.boardgames.model.TurnRecord;
import com.civica.grads.boardgames.web.model.TurnStorageService;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class TurnStorageController {

    @Autowired
    private TurnStorageService turnStorageService;

    @RequestMapping("/")
    @ResponseBody
    ArrayList<TurnRecord> printTurnRecords() throws GameException {

        return turnStorageService.getTurnRecords();
    }

}
