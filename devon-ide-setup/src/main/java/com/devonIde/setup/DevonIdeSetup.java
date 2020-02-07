package com.devonIde.setup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLoggerManager;

import com.devonIde.helper.FileCreator;

/**
 * @author rrohitku
 *
 */
public class DevonIdeSetup {

  public static final String FILE_SEPARATOR = File.separator + "" + File.separator;

  private static void downloadSetup() throws IOException {

    File currDir = new File(FILE_SEPARATOR + "SWTBOT-repo" + FILE_SEPARATOR + "download");
    currDir.mkdir();
    String absPath = currDir.getAbsolutePath();
    URL url = new URL("http://de-mucevolve02/files/devonfw-ide/releases/devonfw-ide-scripts-3.2.2.tar.gz");
    URLConnection urlConnection = url.openConnection();
    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
    File destFile = new File(absPath + FILE_SEPARATOR + "devonfw-ide-scripts-3.2.2.tar.gz");
    destFile.createNewFile();
    System.out.println("File created");

    FileOutputStream out = new FileOutputStream(absPath + FILE_SEPARATOR + "devonfw-ide-scripts-3.2.2.tar.gz");
    int i = 0;
    byte[] bytesIn = new byte[3000000];
    while ((i = in.read(bytesIn)) >= 0) {
      out.write(bytesIn, 0, i);
    }
    System.out.println("Setup Downloaded");
    out.close();
    in.close();
  }

  /**
   *
   */
  private static void extractDownloadedSetup() {

    File sourceFile = new File(FILE_SEPARATOR + "SWTBOT-repo" + FILE_SEPARATOR + "download" + FILE_SEPARATOR
        + "devonfw-ide-scripts-3.2.2.tar.gz");
    File destDir = new File(
        FILE_SEPARATOR + "SWTBOT-repo" + FILE_SEPARATOR + "projects" + FILE_SEPARATOR + "my-project");
    destDir.mkdirs();
    TarGZipUnArchiver unArchiver = new TarGZipUnArchiver();
    // Need to set/enable logging for the unArchiver to avoid null pointer
    ConsoleLoggerManager manager = new ConsoleLoggerManager();
    manager.initialize();
    unArchiver.enableLogging(manager.getLoggerForComponent("Extract Setup"));
    unArchiver.setSourceFile(sourceFile);
    unArchiver.setDestDirectory(destDir);
    unArchiver.extract();
    System.out.println("File Extracted");

  }

  private static void runSetup() throws IOException, InterruptedException {

    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start", "setup-helper.bat");
    File dir = new File(
        new File(FILE_SEPARATOR + "SWTBOT-repo" + FILE_SEPARATOR + "projects" + FILE_SEPARATOR + "my-project")
            .getAbsolutePath());
    pb.directory(dir);
    pb.start();

  }

  public static void main(String[] args) throws IOException, InterruptedException {

    File baseFolder = new File(FILE_SEPARATOR + "SWTBOT-repo");
    baseFolder.mkdir();
    downloadSetup();
    extractDownloadedSetup();
    FileCreator.createBatFile();
    FileCreator.createBashFile();
    FileCreator.createTextFfile();
    FileCreator.createDevon4jAppWithCommandLine();
    runSetup();

    File file = new File(FILE_SEPARATOR + "SWTBOT-repo" + FILE_SEPARATOR + "projects" + FILE_SEPARATOR + "my-project"
        + FILE_SEPARATOR + "text.txt");
    FileReader fr = new FileReader(file); // Creation of File Reader object
    BufferedReader br;
    String lineReader = "";
    String searchLine = "";
    System.out.println("Setup of devonfw-ide installing ...");
    while (true) {
      TimeUnit.MINUTES.sleep(1);
      br = new BufferedReader(fr);// Creation of BufferedReader object
      while ((lineReader = br.readLine()) != null) {
        if ("Setup of devonfw-ide completed".equals(lineReader)) {
          searchLine = lineReader;
          System.out.println("Setup of devonfw-ide completed");
          break;
        }
      }
      if (searchLine.equals("Setup of devonfw-ide completed")) {
        break;
      }
    }
  }

}
