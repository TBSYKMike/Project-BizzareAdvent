/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.bizzareadvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import project.bizzareadvent.SaveLoad.AllLocalData;
import project.bizzareadvent.SaveLoad.DBTable2LoginHasCharacters;
import project.bizzareadvent.SaveLoad.DatabaseServer;
import project.bizzareadvent.SaveLoad.UserData;

public class NewGameController implements Initializable {

    @FXML
    private Button buttonDone;
    @FXML
    private Button buttonWarrior;
    @FXML
    private Button buttonMage;
    @FXML
    private Button buttonAssassin;
    @FXML
    private Button buttonBack;
    @FXML
    private ImageView imageWarrior;
    @FXML
    private ImageView imageMage;
    @FXML
    private ImageView imageRogue;
    @FXML
    private ImageView backGroundImage;
    @FXML
    public TextField characterName;
    @FXML
    private Label Error1;

    public boolean chosenCharacter = false;
    // 3 booleans for marking chosen hero
    public boolean warriorChosen = false;
    public boolean mageChosen = false;
    public boolean assassinChosen = false;
    private static NewGameController instance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCharacterImage();
    }

    // method when done should check if hero is chosen and name is valid before saving to database and sending to worldmap
    public static NewGameController getInstance() {

        if (instance == null) {
            instance = new NewGameController();

        }

        return instance;

    }

    @FXML
    public void handleButtonActionDone(ActionEvent event) {
       
        createNewCharacter(event, "FXMLWorldMap.fxml");

    }

    @FXML
    public void handleButtonActionBack(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stageLogin = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLScene2.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stageLogin.setScene(scene);
            stageLogin.show();

        } catch (IOException ex) {
            System.out.println("Scene change error1");
        }
    }

    // methods below for marking hero as chosen
    @FXML
    public void warriorChosen(ActionEvent event) {
        reset();
        warriorChosen = true;

        chosenCharacter = true;
        buttonDone.setDisable(false);
        loadCharacterImage();
    }

    @FXML
    public void mageChosen(ActionEvent event) {
        reset();
        mageChosen = true;

        chosenCharacter = true;
        buttonDone.setDisable(false);
        loadCharacterImage();
    }

    @FXML
    public void assassinChosen(ActionEvent event) {
        reset();
        assassinChosen = true;

        chosenCharacter = true;
        buttonDone.setDisable(false);
        loadCharacterImage();
    }

    private void reset() {
        assassinChosen = false;
        warriorChosen = false;
        mageChosen = false;
    }

    private void loadSetBaseData(int i) {
        int charType = 0;
        if (warriorChosen) {
            charType = 0;
        }
        else if (mageChosen) {
            charType = 1;
        }
        else if (assassinChosen) {
            charType = 2;
        }

        if (true) {
            AllLocalData.getInstance().getInfo2LoginHasCharacters().get(i).setCurrentAttack(AllLocalData.getInstance().getInfo3Characters().get(charType).getBaseAttack());
            AllLocalData.getInstance().getInfo2LoginHasCharacters().get(i).setCurrentDef(AllLocalData.getInstance().getInfo3Characters().get(charType).getBaseDef());
            AllLocalData.getInstance().getInfo2LoginHasCharacters().get(i).setCurrentDmg(AllLocalData.getInstance().getInfo3Characters().get(charType).getBaseDmg());
            AllLocalData.getInstance().getInfo2LoginHasCharacters().get(i).setCurrentHp(AllLocalData.getInstance().getInfo3Characters().get(charType).getBaseHp());

        }

    }

    private void setLoadSlot() {
        int slots = 0;

        for (DBTable2LoginHasCharacters test : AllLocalData.getInstance().getInfo2LoginHasCharacters()) {
            if (test.getCharacters_idNr() > 0) {
                slots++;
                System.out.println(slots);
            }
        }

        UserData.getInstance().setArraylistNumber(slots - 1);
    }

    //private Image characterImage = new Image("chosecharacter.bmp", true);
    private void loadCharacterImage() {

        Image characterImage1 = new Image("ms-warrior0.png", true);
        Image characterImage2 = new Image("ms-mage0.png", true);
        Image characterImage3 = new Image("ms-rogue0.png", true);

        imageWarrior.setImage(characterImage1);

        imageMage.setImage(characterImage2);

        imageRogue.setImage(characterImage3);

        imageWarrior.setOpacity(0.3);
        imageMage.setOpacity(0.3);
        imageRogue.setOpacity(0.3);

        if (warriorChosen == true) {
            imageWarrior.setOpacity(1);
        } else if (mageChosen == true) {
            imageMage.setOpacity(1);
        } else if (assassinChosen == true) {
            imageRogue.setOpacity(1);
        }

    }

    private void createNewCharacter(ActionEvent event, String path) {

        int arraySlot = 0;
        String charName = characterName.getText();

        for (DBTable2LoginHasCharacters test : AllLocalData.getInstance().getInfo2LoginHasCharacters()) {
            if (test.getCharacters_idNr() > 0) {
                arraySlot++;
            }
        }
        System.out.println(arraySlot);
        if (chosenCharacter) {
            if (charName.length() > 0) {
                if (arraySlot < 3) {
                    if (warriorChosen) {
                        AllLocalData.getInstance().getInfo2LoginHasCharacters().get(arraySlot).setCharacters_idNr(1);
                    } else if (mageChosen) {
                        AllLocalData.getInstance().getInfo2LoginHasCharacters().get(arraySlot).setCharacters_idNr(2);
                    } else if (assassinChosen) {
                        AllLocalData.getInstance().getInfo2LoginHasCharacters().get(arraySlot).setCharacters_idNr(3);
                    }
                    if (true) {
                        AllLocalData.getInstance().getInfo2LoginHasCharacters().get(arraySlot).setCharacterName(charName);
                        loadSetBaseData(arraySlot);
                        
                        
                        DatabaseServer.getInstance().saveToDB();
                        UserData.getInstance().test001LoadCharDataFromALLLOCALDATA();
                    
                        
                        UserData.getInstance().setArraylistNumber(arraySlot);
                        
                        loadScene(event, path);
                    }

                } else {
                    System.out.println("All Slot Are Full");
                }
            } else {
                System.out.println("no charactername");
            }
        } else {
            System.out.println("No character Chosen");
        }
    }
    
    
    
    
    
    
    private void loadScene(ActionEvent event, String path) {
        try {

            Node node = (Node) event.getSource();
            Stage stage1 = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage1.setScene(scene);
            stage1.show();
            //stage1.setFullScreen(true);
            stage1.setResizable(false);

        } catch (Exception ex) {
            System.out.println("Loading " + path + " error!");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    

}
