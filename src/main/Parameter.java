package main;

public class Parameter {

  public static final String sep = System.getProperty("file.separator");
  public static final String homedir = System.getProperty("user.home");
//project path
  public static final String projectdir = System.getProperty("user.dir");
  // resources in project path
  public static final String resourcesPath = projectdir + sep + "src" + sep + "ressources" + sep + "cards" + sep;
}
