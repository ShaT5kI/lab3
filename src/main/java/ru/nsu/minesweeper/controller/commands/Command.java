package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;

import java.io.IOException;

public interface Command {
    Marks run() throws ClassExistenceException, IOException;

    Object getArg();
}
