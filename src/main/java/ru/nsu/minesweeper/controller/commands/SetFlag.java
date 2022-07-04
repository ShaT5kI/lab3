package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;

public class SetFlag extends AbstractGameCommand {
    public SetFlag(CommandIdentifier identifier) throws ClassExistenceException {
        super(identifier);
    }

    @Override
    public Marks run() throws ClassExistenceException {
        if (field.outOf(point)) {
            throw new ClassExistenceException();
        }
        field.setFlag(point);
        return Marks.True;
    }
}