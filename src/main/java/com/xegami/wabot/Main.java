package com.xegami.wabot;

import com.xegami.wabot.core.Bot;

public class Main {

    public static boolean DEBUG = false;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("-debug")) {
            DEBUG = true;
            System.out.println("Lanzando wabot en modo DEBUG.");
        }

        Bot bot = new Bot();
        bot.run();
    }
}
