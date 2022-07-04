package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;

public class RemoveFlag extends AbstractGameCommand {
    public RemoveFlag(CommandIdentifier identifier) throws ClassExistenceException {
        super(identifier);
    }

    @Override
    public Marks run() throws ClassExistenceException {
        if (field.outOf(point)) {
            throw new ClassExistenceException();
        }
        if (field.isFlag(point)) {
            field.removeFlag(point);
        }
        return Marks.True;
    }
}