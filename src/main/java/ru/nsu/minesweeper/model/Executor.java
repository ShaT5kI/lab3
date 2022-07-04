package ru.nsu.minesweeper.model;

import ru.nsu.minesweeper.controller.Controller;
import ru.nsu.minesweeper.controller.commands.*;
import ru.nsu.minesweeper.exceptions.ClassExistenceException;
import ru.nsu.minesweeper.view.View;

import java.io.*;
import java.util.*;

public class Executor {
    private final Controller controller;
    private final View view;

    private Field field;
    private Map<Integer, String> scores;
    private long time;
    private Command cmd;
    private boolean gameEnd;
    private MapCreator creator;
    private String level;

    public Executor(Controller controller, View view) {
        this.controller = controller;
        this.view = view;
    }

    private int createField() {

        GameIdentifier identifier;
        try {
            identifier = makeIdentifier();
        } catch (IOException e) {
            view.showWarning("Can't create field");
            return -1;
        }

        field = new Field(identifier);
        creator = new MapCreator(identifier, field);
        controller.setField(field);
        return 0;
    }

    public Marks run() {
        gameEnd = false;
        Marks mark = Marks.False;
        view.setAlreadyCreated(false);

        if (createField() == -1) return Marks.Menu;

        while (!field.isStart()) {
            view.update(field.getView(), time);

            cmd = null;
            while (cmd == null) {
                try {
                    cmd = controller.waitCommand();
                } catch (ClassExistenceException e) {
                    view.showWarning("wrong input, return");
                    e.printStackTrace();
                }

                if (cmd instanceof Open) {
                    try {
                        creator.initField((Point) cmd.getArg());
                    } catch (IOException e) {
                        view.showList(e.getMessage());
                        cmd = null;
                    }
                    field.setStart();
                }
            }

            try {
                mark = cmd.run();
            } catch (ClassExistenceException | IOException e) {
                view.showWarning("incorrect point");
                mark = Marks.True;
            }

            if (mark == Marks.Exit || mark == Marks.Restart || mark == Marks.Menu)
                return mark;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!gameEnd) {
                    view.update(field.getView(), time);
                }
                timeIsOff();
            }
        };

        timer.schedule(task, time);
        view.startGame();
        long startTime = System.currentTimeMillis();

        while (!field.isWin() && !field.isLose() && !(mark == Marks.Exit || mark == Marks.Restart || mark == Marks.Menu)) {
            view.update(field.getView(), time);
            mark = runCommand();
        }

        timer.cancel();
        view.endGame();
        gameEnd = true;

        if (field.isWin()) {
            view.update(field.getView(), time);
            view.showWarning("You win!");
            try {
                readScores();
                writeScores(System.currentTimeMillis() - startTime);
            } catch (IOException ignored) {

            }
            while (mark == Marks.True || mark == Marks.False) {
                mark = waitExit();
            }
            return mark;

        } else if (mark == Marks.Exit || mark == Marks.Restart || mark == Marks.Menu) {
            return mark;

        } else {
            view.update(field.getView(), time);
            view.showWarning("You lose");
            while (mark == Marks.True || mark == Marks.False) {
                mark = waitExit();
            }
            return mark;
        }
    }

    private Marks runCommand() {
        Marks tag = null;
        CmdThread cmdThread = new CmdThread(controller, view, this);
        cmdThread.start();

        cmd = null;
        while (cmd == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cmdThread.kill();

        try {
            cmdThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            tag = cmd.run();
        } catch (IOException | ClassExistenceException e) {
            view.showWarning("incorrect point");
        }
        return tag;
    }

    public void setCmd(Command cmd) {
        if (this.cmd == null)
            this.cmd = cmd;
    }

    private Marks waitExit() {
        Marks tag;
        cmd = null;

        while (cmd == null) {
            try {
                cmd = controller.waitCommand();
            } catch (ClassExistenceException e) {
                view.showList("wrong input, return");
                e.printStackTrace();
            }
        }

        try {
            tag = cmd.run();
        } catch (ClassExistenceException | IOException e) {
            view.showList("incorrect point");
            tag = Marks.True;
        }

        return tag;
    }

    private GameIdentifier makeIdentifier() throws IOException {
        Properties properties = new Properties();
        var reader = new FileReader("cfg/" + level + ".config");
        properties.load(reader);

        int bombs = Integer.parseInt(properties.getProperty("bombs"));
        int size = Integer.parseInt(properties.getProperty("size"));
        int safetyRad = Integer.parseInt(properties.getProperty("safetyRad"));
        int labyrinth = Integer.parseInt(properties.getProperty("labyrinth"));
        time = (long)Integer.parseInt(properties.getProperty("time")) * 60 * 1000;

        return new GameIdentifier(bombs, size, safetyRad, labyrinth);
    }

    private void readScores() throws IOException {
        File file = new File( "data/" + level + ".txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        scores = new TreeMap<>();
        String line;
        String [] strArray;

        line = reader.readLine();
        while (line != null) {
            strArray = line.split(" ");
            scores.put(Integer.parseInt(strArray[0]), strArray[1]);
            line = reader.readLine();
        }

        fileReader.close();
        reader.close();
    }

    private void writeScores(long time) throws IOException {
        File output = new File("data/" + level + ".txt");
        FileWriter fileWriter = new FileWriter(output);

        view.askUser("Who are you?");
        var name = controller.waitAnswer();
        scores.put((int)time, name);

        for (var i : scores.entrySet()) {
            fileWriter.write(i.getKey() + " " + i.getValue() + "\n");
        }

        fileWriter.close();
    }

    private void timeIsOff() {
        try {
            cmd = new Surrender(new CommandIdentifier("surrender".split(" "), field));
        } catch (ClassExistenceException e) {
            e.printStackTrace();
        }
    }
}