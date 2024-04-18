package ir.ninjacoder.psptools.rewinter.model;

import java.io.File;

public class ListGameModel {

  protected String name;
  protected int idGame;
  protected String size;
  protected String nameDevGame;
  protected File gamePath;

  public ListGameModel(String name, int idGame, String size, String nameDevGame, File gamePath) {
    this.name = name;
    this.idGame = idGame;
    this.size = size;
    this.nameDevGame = nameDevGame;
    this.gamePath = gamePath;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getIdGame() {
    return this.idGame;
  }

  public void setIdGame(int idGame) {
    this.idGame = idGame;
  }

  public String getSize() {
    return this.size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getNameDevGame() {
    return this.nameDevGame;
  }

  public void setNameDevGame(String nameDevGame) {
    this.nameDevGame = nameDevGame;
  }

  public File getGamePath() {
    return this.gamePath;
  }

  public void setGamePath(File gamePath) {
    this.gamePath = gamePath;
  }
}
