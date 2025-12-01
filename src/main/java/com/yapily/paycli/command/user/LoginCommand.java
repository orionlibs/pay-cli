package com.yapily.paycli.command.user;

import com.yapily.paycli.utils.FileService;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
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
    public String login(@ShellOption(value = {"--api-key", "-a"}, defaultValue = ShellOption.NULL) String apiKey)
    {
        if(apiKey == null || apiKey.isEmpty())
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
        else
        {
            boolean saved = saveConfigurationFile(apiKey);
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


    private boolean saveConfigurationFile(String apiKey)
    {
        Optional<Path> configFile = fileService.resolveDirectoriesAndFile(configurationFile, configurationPath);
        if(configFile.isPresent())
        {
            try
            {
                String toml = "[auth]\n" + "api_key = \"" + apiKey + "\"\n";
                Files.writeString(configFile.get(), toml);
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
