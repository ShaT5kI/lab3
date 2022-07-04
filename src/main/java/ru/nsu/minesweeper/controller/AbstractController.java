package ru.nsu.minesweeper.controller;

import java.lang.reflect.InvocationTargetException;

import ru.nsu.minesweeper.controller.commands.Command;
import ru.nsu.minesweeper.controller.commands.CommandIdentifier;
import ru.nsu.minesweeper.exceptions.*;
import ru.nsu.minesweeper.model.Field;
import ru.nsu.minesweeper.factories.*;

public abstract class AbstractController implements Controller {
    protected CommandFactory factory;
    protected Field field;

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    protected Command makeCommand(String cmdStr) throws ClassExistenceException {
        var args = cmdStr.split( " ");

        Command command;
        try {
            command = factory.createObject(new CommandIdentifier(args, field));
        } catch (ClassNotFoundException | InvocationTargetException |
                IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            throw new ClassExistenceException();
        }

        assert command != null;
        return command;
    }
}
