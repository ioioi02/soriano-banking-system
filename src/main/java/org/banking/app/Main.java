package org.banking.app;

import org.banking.terminal.TerminalInterface;

public class Main {
    public static void main(String[] args) {
        TerminalInterface terminalInterface = new TerminalInterface();

        terminalInterface.start();
    }
}