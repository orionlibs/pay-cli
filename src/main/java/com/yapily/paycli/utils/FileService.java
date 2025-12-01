package com.yapily.paycli.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FileService
{
    public Optional<Path> resolveDirectoriesAndFile(String fileName, String path1, String... paths2)
    {
        Path configDir = Path.of(path1, paths2);
        Path configFile = configDir.resolve(fileName);
        if(!Files.exists(configDir))
        {
            try
            {
                Files.createDirectories(configDir);
            }
            catch(IOException e)
            {
                return Optional.empty();
            }
        }
        return Optional.of(configFile);
    }
}
