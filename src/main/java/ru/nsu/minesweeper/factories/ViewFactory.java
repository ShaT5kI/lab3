package ru.nsu.minesweeper.factories;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.view.View;

public class ViewFactory extends Factory {
    public ViewFactory() throws IOException, NullPointerException {
        super();
    }

    @Override
    public View createObject(Object desc) throws ClassExistenceException {
        Class<?> productClass;
        View object;

        try {
            productClass = Class.forName("ru.nsu.minesweeper.view." +
                    names.get("interface") + "View");
        object = (View)productClass.getConstructor().newInstance();

        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new ClassExistenceException("Class does not exist");
        }

        return object;
    }

    @Override
    protected String getFileName() {
        return "C:\\Users\\ishat\\IdeaProjects\\minesweeper\\src\\main\\java\\ru\\nsu\\minesweeper\\cfg.config";
    }
}