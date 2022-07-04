package ru.nsu.minesweeper.model;

import ru.nsu.minesweeper.controller.Controller;
import ru.nsu.minesweeper.controller.commands.*;
import ru.nsu.minesweeper.controller.commands.Marks;
import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.factories.ControllerFactory;
import ru.nsu.minesweeper.factories.ViewFactory;
import ru.nsu.minesweeper.view.View;


import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    private View view;
    private Controller controller;
    private Executor executor;

    public GameManager() {
        ViewFactory viewFactory;
        ControllerFactory controllerFactory;
        try {
            viewFactory = new ViewFactory();
            controllerFactory = new ControllerFactory();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            view = viewFactory.createObject(null);
            controller = controllerFactory.createObject(new ControllerIdentifier(view));
        } catch (ClassExistenceException e) {
            e.printStackTrace();
        }

        assert view != null;
        assert controller != null;
        executor = new Executor(controller, view);
    }

    public Marks start() {
        view.showGreetScreen();

        Command cmd;
        Marks res = Marks.False;

        while (res == Marks.False) {
            cmd = null;
            while (cmd == null) {
                try {
                    cmd = controller.waitCommand();
                } catch (ClassExistenceException e) {
                    view.showList("wrong input, return");
                    e.printStackTrace();
                }
            }

            if (cmd instanceof HighScores) {
                ((HighScores) cmd).setArg("C:\\Users\\ishat\\IdeaProjects\\minesweeper\\data\\scores.txt");
            }

            try {
                res = cmd.run();
                if (res == Marks.Record) {
                    assert cmd instanceof AbstractRecordCommand;
                    write((AbstractRecordCommand) cmd);
                    res = Marks.False;
                }
            } catch (IOException | ClassExistenceException e) {
                view.showList("Can't run command");
                e.printStackTrace();
            }
        }

        if (res == Marks.Exit) {
            System.exit(0);
        }

        Marks mark = null;
        while (mark != Marks.Exit && mark != Marks.Menu) {
            mark = executor.run();
        }

        return mark;
    }


    boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    void write(AbstractRecordCommand command) {
        StringBuilder message = new StringBuilder();
        for (var i : (ArrayList<?>)command.getOutput()) {
            var arr = i.toString().split(" ");
            for (var j : arr) {
                if (isNumber(j)) {
                    long num = Integer.parseInt(j);
                    message.append(String.format("%tM:%tS ", num, num));
                } else {
                    message.append(j);
                }
            }
            message.append("\n");
        }

        if (command instanceof HighScores) {
            view.showList(message.toString());
        }

        if (command instanceof About) {
            view.showText(message.toString());
        }
    }
}
