package de.ostfalia.test.ss16.view;

import de.ostfalia.test.ss16.logic.Spassbad;
import de.ostfalia.test.ss16.logic.Tickets;
import de.ostfalia.test.ss16.logic.Ticketshop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller für Main GUI
 *
 * @author Henrik Drefs
 * @author David N. Winterland
 */
public class Controller implements Initializable {

    @FXML
    TextArea consoleArea;

    @FXML
    TextArea cartArea;

    @FXML
    TextArea priceArea;

    @FXML
    ComboBox dropdown;

    static Ticketshop shop = new Ticketshop();

    /**
     * action handler für "+ Einzelkarte"-Button
     */
    @FXML
    public void addEinzelkarte() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.addTicket(bad, Tickets.EINZELKARTE, 1);

        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Ermäßigt"-Button
     */
    @FXML
    public void addErmaessigt() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.addTicket(bad, Tickets.ERMAESSIGT, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Frühbade"-Button
     */
    @FXML
    public void addFruehbad() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.addTicket(bad, Tickets.FRUEHBAD, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Frühbade Ermäßigt"-Button
     */
    @FXML
    public void addFruehbadErmaessigt() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.addTicket(bad, Tickets.FRUEHBAD_ERMAESSIGT, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Sauna"-Button
     */
    @FXML
    public void addSauna() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.addTicket(bad, Tickets.SAUNA, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Einzelkarte"-Button
     */
    @FXML
    public void removeEinzelkarte() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.removeTicket(bad, Tickets.EINZELKARTE, 1);

        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Ermäßigt"-Button
     */
    @FXML
    public void removeErmaessigt() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.removeTicket(bad, Tickets.ERMAESSIGT, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Frühbade"-Button
     */
    @FXML
    public void removeFruehbad() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.removeTicket(bad, Tickets.FRUEHBAD, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Frühbade Ermäßigt"-Button
     */
    @FXML
    public void removeFruehbadErmaessigt() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.removeTicket(bad, Tickets.FRUEHBAD_ERMAESSIGT, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * action handler für "+ Sauna"-Button
     */
    @FXML
    public void removeSauna() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        shop.warenkorb.removeTicket(bad, Tickets.SAUNA, 1);
        printCart();
        printTotalPrice();
    }

    /**
     * KAUFEN
     */
    @FXML
    public void confirmBuy() {
        shop.buy();
        printCart();
        printTotalPrice();
    }

    /**
     * on click listener
     */
    @FXML
    public void dropdownClick() {
        updateDropdown();
    }

    /**
     * Spassbad hinzufügen
     */
    @FXML
    public void addSpassbad() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(
                    getClass().getClassLoader().getResource("add_sb_view.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Spassbad hinzufügen");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * spassbad löschen
     */
    @FXML
    public void removeSpassbad() {
        if (dropdown.getValue() != null) {
            shop.removeBad(shop.getBad((String) dropdown.getValue()));
        }
        printCart();
        printTotalPrice();
    }

    /**
     * Initialisierungsmethode
     *
     * @param location  loc
     * @param resources res
     */
    public void initialize(URL location, ResourceBundle resources) {
        shop.addBad(new Spassbad("Dave's Wet Funland"));
        updateDropdown();
        printPrices();
        dropdown.valueProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                printCart();
                printPrices();
            }
        });
    }

    /**
     * druckt den Warenkorb-Preis in TextArea
     */
    private void printTotalPrice() {
        consoleArea.setText("Preis: " + shop.warenkorb.calculatePreis() + " Euro");
    }

    /**
     * druckt warenkorbinhalt in TextArea
     */
    private void printCart() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        String s = "Einzelkarten: " + shop.warenkorb.getAnzahlFor(bad, Tickets.EINZELKARTE) + '\n'
                + "Ermäßigt: " + shop.warenkorb.getAnzahlFor(bad, Tickets.ERMAESSIGT) + '\n'
                + "Frühbade: " + shop.warenkorb.getAnzahlFor(bad, Tickets.FRUEHBAD) + '\n'
                + "Frühebade Ermäßigt: "
                + shop.warenkorb.getAnzahlFor(bad, Tickets.FRUEHBAD_ERMAESSIGT) + '\n'
                + "Sauna: " + shop.warenkorb.getAnzahlFor(bad, Tickets.SAUNA) + '\n';

        cartArea.setText(s);
    }

    /**
     * druckt preise
     */
    private void printPrices() {
        Spassbad bad = shop.getBad((String) dropdown.getValue());
        if (bad != null) {
            priceArea.setText(bad.getPreiskonzept().toString());
        }
    }

    /**
     * initialisiert Dropdown menu
     */
    private void updateDropdown() {
        dropdown.setItems(FXCollections.observableArrayList(new String[0]));
        for (Spassbad sb : shop.getSpassbaeder()) {
            dropdown.getItems().add(sb.getName());
        }
        dropdown.setValue(dropdown.getItems().get(0));
    }
}
