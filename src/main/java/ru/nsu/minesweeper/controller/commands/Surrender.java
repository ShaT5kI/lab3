package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.*;

import java.io.IOException;

public class Surrender implements Command{
    private final Field field;
    public Surrender(CommandIdentifier identifier) throws ClassExistenceException {
        this.field = identifier.field;
    }

    @Override
    public Marks run() throws ClassExistenceException, IOException {
        field.setLose();
        return Marks.False;
    }

    @Override
    public Point getArg() {
        return null;
    }
}
