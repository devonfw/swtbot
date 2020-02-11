package com.devonIde.setup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    File currDir = new File(File.separator + "SWTBOT-repo" + File.separator + "download");
    currDir.mkdirs();

    String absPath = currDir.getAbsolutePath();
    System.out.println("Path -----" + absPath);
    URL url = new URL("http://de-mucevolve02/files/devonfw-ide/releases/devonfw-ide-scripts-3.2.2.tar.gz");
    URLConnection urlConnection = url.openConnection();
    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
    System.out.println("File out put stream  start");
    // FileOutputStream out = new FileOutputStream(absPath + File.separator + "devonfw-ide-scripts-3.2.2.tar.gz");
    // OutputStream out = Files.newOutputStream(Paths.get(absPath + File.separator +
    // "devonfw-ide-scripts-3.2.2.tar.gz"));
    FileWriter out = new FileWriter(absPath + File.separator + "devonfw-ide-scripts-3.2.2.tar.gz");
    System.out.println("File out put stream  end");
    int i = 0;
    byte[] bytesIn = new byte[3000000];
    while ((i = in.read(bytesIn)) >= 0) {
      out.write(i);// write(bytesIn, 0, i);
    }
    System.out.println("downloadSetup done");
    out.close();
    in.close();
  }

  /**
   *
   */
  private static void extractDownloadedSetup() {

    System.out.println("Extraction Started......");
    File sourceFile = new File(File.separator + "SWTBOT-repo" + File.separator + "download" + File.separator
        + "devonfw-ide-scripts-3.2.2.tar.gz");
    File destDir = new File(
        File.separator + "SWTBOT-repo" + File.separator + "projects" + File.separator + "my-project");
    destDir.mkdirs();
    TarGZipUnArchiver unArchiver = new TarGZipUnArchiver();
    // Need to set/enable logging for the unArchiver to avoid null pointer
    ConsoleLoggerManager manager = new ConsoleLoggerManager();
    manager.initialize();
    unArchiver.enableLogging(manager.getLoggerForComponent("Extract Setup"));
    unArchiver.setSourceFile(sourceFile);
    unArchiver.setDestDirectory(destDir);
    System.out.println("Extraction progress........");
    unArchiver.extract();
    System.out.println("Extraction done........");

  }

  private static void runSetup() throws IOException, InterruptedException {

    ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "Start", "setup-helper.bat");
    File dir = new File(
        new File(File.separator + "SWTBOT-repo" + File.separator + "projects" + File.separator + "my-project")
            .getAbsolutePath());
    pb.directory(dir);
    pb.start();

  }

  public static void main(String[] args) throws IOException, InterruptedException {

    File baseFolder = new File(File.separator + "SWTBOT-repo");
    baseFolder.mkdir();
    downloadSetup();
    extractDownloadedSetup();
    FileCreator.createBatFile();
    FileCreator.createBashFile();
    FileCreator.createTextFfile();
    FileCreator.createDevon4jAppWithCommandLine();
    runSetup();

    File file = new File(File.separator + "SWTBOT-repo" + File.separator + "projects" + File.separator + "my-project"
        + File.separator + "text.txt");
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
