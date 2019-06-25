import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "countFile", defaultPhase = LifecyclePhase.PACKAGE)
public class CountFileMojo extends AbstractMojo {

    @Parameter(property = "basedir")
    private String rootPath;

    @Parameter(property = "fileType", defaultValue = ".java")
    private String fileType;

    private int countFile(String rootPath, String fileType) {
        int sum = 0;
        File file = new File(rootPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                boolean isFile = files[i].isFile();
                if (isFile) {
                    if (files[i].getName().endsWith(fileType)) {
                        sum ++;
                        System.out.println(files[i].getName());
                    }
                } else if (files[i].isDirectory()) {
                    sum += countFile(files[i].getAbsolutePath(), fileType);
                }
            }
        }
        return sum;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("Starting counting file---------------rootPath: "+ rootPath);
        int totalNumber = countFile(rootPath, fileType);
        System.out.println("total File Number: " + totalNumber);
    }
}
