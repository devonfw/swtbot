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

  private static void downloadSetup() throws IOException {

    File currDir = new File("\\SWTBOT-repo\\download");
    currDir.mkdir();
    String absPath = currDir.getAbsolutePath();
    URL url = new URL("http://de-mucevolve02/files/devonfw-ide/releases/devonfw-ide-scripts-3.2.2.tar.gz");
    URLConnection urlConnection = url.openConnection();
    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
    FileOutputStream out = new FileOutputStream(absPath + "\\devonfw-ide-scripts-3.2.2.tar.gz");
    int i = 0;
    byte[] bytesIn = new byte[3000000];
    while ((i = in.read(bytesIn)) >= 0) {
      out.write(bytesIn, 0, i);
    }
    out.close();
    in.close();
  }

  /**
   *
   */
  private static void extractDownloadedSetup() {

    File sourceFile = new File("\\SWTBOT-repo\\download\\devonfw-ide-scripts-3.2.2.tar.gz").getAbsoluteFile();
    File destDir = new File("\\SWTBOT-repo\\projects\\my-project").getAbsoluteFile();
    destDir.mkdirs();
    TarGZipUnArchiver unArchiver = new TarGZipUnArchiver();
    // Need to set/enable logging for the unArchiver to avoid null pointer
    ConsoleLoggerManager manager = new ConsoleLoggerManager();
    manager.initialize();
    unArchiver.enableLogging(manager.getLoggerForComponent("Extract Setup"));
    unArchiver.setSourceFile(sourceFile);
    unArchiver.setDestDirectory(destDir);
    unArchiver.extract();

  }

  private static void runSetup() throws IOException, InterruptedException {

    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start", "setup-helper.bat");
    File dir = new File(new File("\\SWTBOT-repo\\projects\\my-project").getAbsolutePath());
    pb.directory(dir);
    pb.start();

  }

  public static void main(String[] args) throws IOException, InterruptedException {

    File baseFolder = new File("\\SWTBOT-repo");
    baseFolder.mkdir();
    downloadSetup();
    extractDownloadedSetup();
    FileCreator.createBatFile();
    FileCreator.createBashFile();
    FileCreator.createTextFfile();
    FileCreator.createDevon4jAppWithCommandLine();
    runSetup();

    File file = new File("\\SWTBOT-repo\\projects\\my-project\\text.txt");
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
