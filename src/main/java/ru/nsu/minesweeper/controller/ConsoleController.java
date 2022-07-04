package ru.nsu.minesweeper.controller;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.ControllerIdentifier;
import ru.nsu.minesweeper.factories.*;
import ru.nsu.minesweeper.controller.commands.*;

import java.io.IOException;
import java.util.Scanner;


public class ConsoleController extends AbstractController {
    private final Scanner scanner;


    public ConsoleController(ControllerIdentifier identifier) {
        field = null;
        try {
            factory = new CommandFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(System.in);
    }

    @Override
    public void interrupt() {

    }

    @Override
    public String waitAnswer() {
        return scanner.nextLine().trim();
    }

    @Override
    public Command waitCommand() throws ClassExistenceException {
//        System.out.println(scanner.nextLine().trim());
//        var cmdStr = scanner.nextLine().trim();
        var cmdStr = "1";
        return makeCommand(cmdStr);
    }
}
