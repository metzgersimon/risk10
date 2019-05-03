package main;

public class Parameter {

  public static final String sep = System.getProperty("file.separator");
  public static final String homedir = System.getProperty("user.home");
  // project path
  public static final String projectdir = System.getProperty("user.dir");
  // resources in project path
  public static final String resourcesPathCards =
      projectdir + sep + "src" + sep + "resources" + sep + "cards" + sep;
  public static final String resourcesPathAvatars =
      projectdir + sep + "src" + sep + "resources" + sep + "avatar" + sep;

  // for network game
  public static final int PORT = 8888;
}
