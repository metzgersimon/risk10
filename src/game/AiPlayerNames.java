package game;

import java.util.HashSet;
import java.util.Random;
import main.Main;

/**
 * 
 * @author smetzger
 *
 */
public enum AiPlayerNames {
  ST_EDWARD_THE_CONFESSOR, NAPOLEON_BONAPARTE, RAGNAR_LODBROK, ETHELRED_THE_UNREADY, RUDOLPHUS_THE_MAGGOT, TOMAS_MCBEAN, ALBUS_DUMBLEDORE, PRUDENCE_ELEGANCE, SAMANTHA_HILLS, MARVIN_THE_STUPID, SOFIA_JUDGEMENT, ALDWYN_THE_COFFEE_POT, CNUT_THE_GREAT, WILLIAM_THE_CONQUEROR, LORD_VOLDEMORT, EMPRESS_MATILDA, KUNG_FU_PANDA, EDWY_THE_FAIR, EDGAR_THE_PEACEABLE, ST_EDWARD_THE_MARTYR, OSCAR_THE_GREAT, KNOX_LEON_OF_DURNINGHAM, EDMUND_IRONSIDE, RICCIARDA_THE_WOMAN, ROBOBOBOBOBOT, RODOLPHO_AGOSTO, ERIC_THE_MEMORABLE, PRESTORIUS_OF_EGGENBURGH, BJORN_IRONSIDE, SPONGEBOB, EGIL_SKALLAGRIMSSON, EINSTEIN_THE_BRAIN, MEISTER_PROPER, URSULA_THE_SNAKE, CINDERELLA, YOSHI, ANGELINA_THE_MOM, SIGFRID_THE_SAPIENT, BOB_THE_BUILDER, LEIF_ERIKSON, VELLA_SHIELD, EMMELINE_PANKHURST, ELEANOR_OF_AQUITAINE, AGNES_DE_PERCY, GRAPELLA_DALLE_CARCERI, ELISABETH_OF_ORLAMUNDE, ISABEL_THE_DESTROYER, LILIANDU_OF_AMTSCHAKA, GUDRID_THORBJARNARDOTTIR, SIGRID_THE_PROUD, SUPREMOR_THE_ODD, AGATHE_THE_QUEEN, ALFREDO_THE_DISHWASHER, BOMBADO, MISS_MARPLE, ADVOCADO, BEAT_THE_BEAT, MAYA_THE_BEE, ERWIN_THE_KID, GUNDULA_THE_BIG, BOWSER, PEACH, SUPER_MARIO, SUN_KING, THE_METZELER, VALDEMAR_THE_YOUNG, FREYDIS_EIRIKSDOTTIR, IVAR_THE_BONELESS, KONFUZIUS;

  /**
   * 
   * @param given enum constant
   * @return a string representation which can be used to display the name in the game
   */
  public static String getFormattedName(AiPlayerNames named) {
    String name = named.toString().toLowerCase();
    String regex = "_";
    if (name.contains("_")) {
      String[] parts = name.split(regex);

      name = "";

      for (int i = 0; i < parts.length; i++) {
        char replace = Character.toUpperCase(parts[i].charAt(0));
        String partOfName = parts[i];
        partOfName = partOfName.replaceFirst("" + partOfName.charAt(0) + "", "" + replace + "");
        parts[i] = partOfName;
        name += parts[i] + " ";
      }
    } else {
      char replace = Character.toUpperCase(name.charAt(0));
      name = name.replaceFirst("" + name.charAt(0) + "", "" + replace + "");
    }
    return name;
  }

  /**
   * 
   * @returns a String that represents a name of an ai player which is chosen randomly
   */
  public static String getRandomName() {
    String name;
    do {
      Random rand = new Random();
      name = (getFormattedName(values()[rand.nextInt(values().length)]));

    } while (Main.g.getAiNames().contains(name));

    return name;
  }
}
