package ru.nsu.minesweeper.controller.commands;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.*;

public abstract class AbstractGameCommand implements Command {
    protected Point point;
    protected Field field;

    protected AbstractGameCommand(CommandIdentifier identifier) throws ClassExistenceException {
        this.field = identifier.field;

        try {
            point = new Point(Integer.parseInt(identifier.args[1]), Integer.parseInt(identifier.args[2]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ClassExistenceException();
        }
    }

    @Override
    public Point getArg() {
        return point;
    }
}
