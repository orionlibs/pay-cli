package com.yapily.paycli;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BASHScriptTest
{
    private static final Path PROJECT_ROOT = Paths.get("").toAbsolutePath();
    /*private static final Path SCRIPT = PROJECT_ROOT.resolve("run-all-commands.sh");


    @BeforeEach
    public void ensureScriptExecutable()
    {
        // Make sure the script file exists and is executable
        assertTrue(Files.exists(SCRIPT), "run-all-commands.sh must exist in project root before running tests");
        SCRIPT.toFile().setExecutable(true);
    }


    @Test
    public void testBASHScriptOfShellCommands() throws IOException, InterruptedException
    {
        ProcessBuilder pb = new ProcessBuilder()
                        .command("bash", SCRIPT.toAbsolutePath().toString())
                        .directory(PROJECT_ROOT.toFile())
                        .redirectErrorStream(true);
        Process p = pb.start();
        boolean finished = p.waitFor(30, TimeUnit.SECONDS);
        if(!finished)
        {
            p.destroyForcibly();
            fail("Script did not finish within timeout");
        }
        int exitCode = p.exitValue();
        String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        assertThat(exitCode).isEqualTo(0);
        assertThat(output.contains("Available commands: help")).isTrue();
        assertThat(output.contains("log message")).isTrue();
    }*/
}
