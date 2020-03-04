package com.devonIde.setup;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.devonIde.constants.Constants;

/**
 * @author rohit
 *
 */
public class test {

  /**
   * @param args
   */
  public static void main(String[] args) {

    File dir = new File(new File(Constants.USER_HOME + File.separator + Constants.BASE_FOLDER + File.separator + "projects"
        + File.separator + "my-project").getAbsolutePath());

    try {

      // Runtime.getRuntime().exec("xterm -c . setup-helper.sh", null, dir);

      ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", ". setup-helper");
      pb.directory(dir);
      Process p = pb.start();

      BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));

      String line = null;

      while ((line = bf.readLine()) != null) {
        System.out.println(line);

      }

      System.out.println("setup-helper run Enviroment is Linux...............");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
