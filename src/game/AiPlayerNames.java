package game;

import java.util.HashSet;
import java.util.Random;

/**
 * 
 * @author smetzger
 *
 */
public enum AiPlayerNames {
  OSCAR_THE_GREAT, KNOX_LEON_OF_DURNINGHAM, LORD_VOLDEMORT, RUDOLPHUS_THE_MAGGOT, TOMAS_MCBEAN, ALBUS_DUMBLEDORE, PRUDENCE_ELEGANCE, SAMANTHA_HILLS, MARVIN_THE_STUPID, SOFIA_JUDGEMENT, ALDWYN_THE_COFFEE_POT, CNUT_THE_GREAT, ST_EDWARD_THE_CONFESSOR, WILLIAM_THE_CONQUEROR, EMPRESS_MATILDA, EDWY_THE_FAIR, EDGAR_THE_PEACEABLE, ST_EDWARD_THE_MARTYR, ETHELRED_THE_UNREADY, EDMUND_IRONSIDE, RICCIARDA_THE_WOMAN, RODOLPHO_AGOSTO, RAGNAR_LODBROK, ERIC_THE_MEMORABLE, VALDEMAR_THE_YOUNG, BJORN_IRONSIDE, FREYDIS_EIRIKSDOTTIR, IVAR_THE_BONELESS, EGIL_SKALLAGRIMSSON, LEIF_ERIKSON, VELLA_SHIELD, EMMELINE_PANKHURST, ELEANOR_OF_AQUITAINE, AGNES_DE_PERCY, GRAPELLA_DALLE_CARCERI, ELISABETH_OF_ORLAMUNDE, ISABEL_THE_DESTROYER, LILIANDU_OF_AMTSCHAKA, GUDRID_THORBJARNARDOTTIR, SIGRID_THE_PROUD, NAPOLEON_BONAPARTE, SUPREMOR_THE_ODD, AGATHE_THE_QUEEN, ERWIN_THE_KID, GUNDULA_THE_BIG, BOWSER, PEACH, SUPER_MARIO, SUN_KING, THE_METZELER, PRESTORIUS_OF_EGGENBURGH;

  /**
   * 
   * @param given enum constant
   * @return a string representation which can be used to display the name in the game
   */
  public static String getFormattedName(AiPlayerNames named) {
    String name = named.toString().toLowerCase();
    String regex = "_";
    if(name.contains("_")) {
      String[] parts = name.split(regex);
      
      name = "";

      for (int i = 0; i < parts.length; i++) {
        char replace = Character.toUpperCase(parts[i].charAt(0));
        String partOfName = parts[i];
        partOfName = partOfName.replaceFirst("" + partOfName.charAt(0) + "", "" + replace + "");
        parts[i] = partOfName;
        name += parts[i] + " ";
      }
    }
    else {
      char replace = Character.toUpperCase(name.charAt(0));
      name = name.replaceFirst(""+name.charAt(0)+"", ""+replace+"");
    }
    return name;
  }

  /**
   * 
   * @returns a String that represents a name of an ai player which is chosen randomly
   */
  public static HashSet getRandomNames(int numberOfAi) {
    HashSet<String> randomNames = new HashSet<String>();
    Random rand = new Random();
    for(int i = 0; i< numberOfAi; i++) {
      while(randomNames.size() < numberOfAi-i) {
        randomNames.add(getFormattedName(values()[rand.nextInt(values().length)]));
      }
    }
    return randomNames;
  }
}
