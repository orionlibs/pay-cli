package com.yapily.paycli.command.user;

import com.yapily.paycli.utils.FileService;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LoginCommand
{
    @Value("${cli.login.url}")
    String loginURL;
    @Value("${cli.configuration.path}")
    String configurationPath;
    @Value("${cli.configuration.file}")
    String configurationFile;
    @Autowired FileService fileService;


    @ShellMethod(key = "login")
    public String login(@ShellOption(value = {"--api-key", "-a"}, defaultValue = ShellOption.NULL) String apiKey,
                    @ShellOption(value = {"--project", "-p"}, defaultValue = ShellOption.NULL) String projectName)
    {
        if((apiKey == null || apiKey.isEmpty()) && (projectName == null || projectName.isEmpty()))
        {
            if(Desktop.isDesktopSupported())
            {
                try
                {
                    Desktop.getDesktop().browse(URI.create(loginURL));
                    return "Opening browser: " + loginURL;
                }
                catch(IOException e)
                {
                    return "Failed to open browser: " + e.getMessage();
                }
            }
            else
            {
                return "Desktop operations are not supported on this system";
            }
        }
        else if(projectName != null && !projectName.isEmpty())
        {
            System.out.println("Your pairing code is: " + UUID.randomUUID());
            System.out.println("Press Enter to open the browser and enter the above code...");
            try(Scanner scanner = new Scanner(System.in))
            {
                scanner.nextLine();
            }
            if(Desktop.isDesktopSupported())
            {
                try
                {
                    Desktop.getDesktop().browse(URI.create(loginURL));
                    System.out.println("Opening browser: " + loginURL);
                    System.out.println("Enter the API key you got...");
                    try(Scanner scanner = new Scanner(System.in))
                    {
                        apiKey = scanner.nextLine();
                        boolean saved = saveConfigurationFile(apiKey, projectName);
                        if(saved)
                        {
                            return "API Key is: " + apiKey;
                        }
                        else
                        {
                            return "Cannot save configuration file for the API Key";
                        }
                    }
                }
                catch(IOException e)
                {
                    return "Failed to open browser: " + e.getMessage();
                }
            }
            else
            {
                return "Desktop operations are not supported on this system";
            }
        }
        else
        {
            boolean saved = saveConfigurationFile(apiKey, projectName);
            if(saved)
            {
                return "API Key is: " + apiKey;
            }
            else
            {
                return "Cannot save configuration file for the API Key";
            }
        }
    }


    private boolean saveConfigurationFile(String apiKey, String projectName)
    {
        Optional<Path> configFile = fileService.resolveDirectoriesAndFile(configurationFile, configurationPath);
        if(configFile.isPresent())
        {
            try
            {
                StringBuilder toml = new StringBuilder("[auth]\n");
                toml.append("api_key = \"");
                toml.append(apiKey);
                toml.append("\"\n");
                toml.append("project_name = \"");
                toml.append(projectName);
                toml.append("\"\n");
                Files.writeString(configFile.get(), toml.toString());
                return true;
            }
            catch(IOException e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
