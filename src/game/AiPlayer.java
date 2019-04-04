package game;

public interface AiPlayer {

  public void initialTerritoryDistribution();

  public void initialArmyDistribution();

  public void armyDistribution();

  public void attack();

  public void fortify();
}
