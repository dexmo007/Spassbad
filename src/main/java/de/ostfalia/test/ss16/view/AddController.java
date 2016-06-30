package de.ostfalia.test.ss16.view;

import de.ostfalia.test.ss16.logic.Preiskonzept;
import de.ostfalia.test.ss16.logic.Spassbad;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static de.ostfalia.test.ss16.view.Controller.shop;

/**
 * Controller
 *
 * @author Henrik Drefs
 */
public class AddController {

    @FXML
    TextField nameField;
    @FXML
    TextField einzelField;
    @FXML
    TextField ermField;
    @FXML
    TextField fbField;
    @FXML
    TextField saunaField;
    @FXML
    TextField zehnField;

    /**
     * add handler
     */
    @FXML
    public void addAndClose() {
        if (!nameField.getText().equals("")) {
            Spassbad spassbad = new Spassbad(nameField.getText());

            Preiskonzept pk = new Preiskonzept();
            pk.setPreisEinzelkarte(Double.parseDouble(einzelField.getText()));
            pk.setPreisEinzelkarteErmaessigt(Double.parseDouble(ermField.getText()));
            pk.setFruehbadetarif(Double.parseDouble(fbField.getText()));
            pk.setSaunaAufpreis(Double.parseDouble(saunaField.getText()));
            pk.setMassenRabatt(Double.parseDouble(zehnField.getText()));
            spassbad.setPreiskonzept(pk);

            shop.addBad(spassbad);
        }
        ((Stage) nameField.getScene().getWindow()).close();
    }

}
