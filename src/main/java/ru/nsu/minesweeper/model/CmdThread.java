package ru.nsu.minesweeper.model;

import ru.nsu.minesweeper.controller.*;
import ru.nsu.minesweeper.controller.commands.Command;
import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.view.*;

public class CmdThread extends Thread {
    private final Controller controller;
    private final View view;
    private boolean flag;
    public Command cmd;
    Executor executor;

    public CmdThread (Controller controller, View view, Executor executor) {
        this.controller = controller;
        this.view = view;
        this.executor = executor;
    }

    @Override
    public void run() {
        super.run();
        waitCmd();
    }

    private void waitCmd() {
        cmd = null;
        while (cmd == null && !flag) {
            try {
                cmd = controller.waitCommand();
            } catch (ClassExistenceException e) {
                view.showList("wrong input, return");
            }
        }
        executor.setCmd(cmd);
    }

    public void kill() {
        flag = true;
        controller.interrupt();
    }
}
