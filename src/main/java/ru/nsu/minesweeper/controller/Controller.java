package ru.nsu.minesweeper.controller;

import ru.nsu.minesweeper.controller.commands.Command;
import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.Field;

public interface Controller {
    void setField(Field field);

    Command waitCommand() throws ClassExistenceException;

    String waitAnswer();

    void interrupt();
}
