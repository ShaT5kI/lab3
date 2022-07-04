package ru.nsu.minesweeper.factories;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.model.*;
import ru.nsu.minesweeper.controller.Controller;

public class ControllerFactory extends Factory {
    public ControllerFactory() throws IOException, NullPointerException {
        super();
    }

    @Override
    public Controller createObject(Object desc) throws ClassExistenceException {
        Class<?> productClass;
        Controller object;

        var descriptor = (ControllerIdentifier)desc;

        try {
            productClass = Class.forName("ru.nsu.minesweeper.controller." + names.get("interface") + "Controller");
            object = (Controller)productClass.getConstructor(descriptor.getClass()).newInstance(descriptor);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException
                | IllegalAccessException | NoSuchMethodException e) {
            throw new ClassExistenceException();
        }

        return object;
    }

    @Override
    protected String getFileName() {
        return "ru\\nsu\\minesweeper\\settings.config";
    }
}
