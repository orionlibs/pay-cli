package com.yapily.paycli.command.help;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelpCommand
{
    @ShellMethod(key = "help")
    public String help()
    {
        return "Available commands: help";
    }
}
