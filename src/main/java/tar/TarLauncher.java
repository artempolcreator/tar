package tar;

import org.kohsuke.args4j.*;

import java.io.*;
import java.util.*;

public class TarLauncher {

    @Option(name = "-out", metaVar = "Connect", usage = "Connect files", forbids = {"-u"})
    private File total;

    @Option(name = "-u", metaVar = "Split", usage = "Split files", forbids = {"-out"})
    private File split;

    @Argument(usage = "InputFileName")
    private List<File> inputFilesName;

    void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar tar-1.0.jar [-u filename.txt] | file1.txt file2.txt [-out output.txt]");
            parser.printUsage(System.err);
            return;
        }

        Tar tar = new Tar();
        if (split != null) tar.spliter(split);
        else {
            if (total != null) tar.connect(total, inputFilesName);
            else {
                System.err.println("ERROR: Can't define the task. Use -out or -u to set the task");
                throw new IllegalArgumentException();
            }
        }
        System.out.println("SUCCESS");

    }
}
