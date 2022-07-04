package ru.nsu.minesweeper.factories;

import ru.nsu.minesweeper.controller.commands.CommandIdentifier;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import ru.nsu.minesweeper.controller.commands.*;

public class CommandFactory extends Factory {
    public CommandFactory() throws IOException, NullPointerException {
        super();
    }

    @Override
    protected String getFileName() {
        return "C:\\Users\\ishat\\IdeaProjects\\minesweeper\\src\\main\\java\\ru\\nsu\\minesweeper\\commands.property";
    }

    @Override
    public Command createObject(Object ident) throws ClassNotFoundException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {

        Class<?> productClass;
        Command object;
        var identifier = (CommandIdentifier) ident;

        productClass = Class.forName("ru.nsu.minesweeper.controller.commands." + names.get(identifier.args[0]));
        object = (Command)productClass.getConstructor(identifier.getClass()).newInstance(identifier);

        return object;
    }
}