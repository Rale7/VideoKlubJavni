package com.mycompany.mavenproject1;

import brisanja.BrisanjeOcene;
import brisanja.BrisanjeSnimka;
import forme.FormaDodavanjeKategorije;
import forme.FormaGledanje;
import forme.FormaKategorija;
import forme.FormaKorisnik;
import tabele.PrikazOcena;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import forme.FormaMesto;
import forme.FormaOcena;
import forme.FormaPaket;
import forme.FormaPretplata;
import forme.FormaSnimak;
import kontrole.Kontrola;
import promene.PromenaMaila;
import promene.PromenaMesecneCene;
import promene.PromenaMesta;
import promene.PromenaNazivaSnimka;
import tabele.PrikazGledanje;
import tabele.PrikazKategorija;
import tabele.PrikazKategorijaSnimak;
import tabele.PrikazKorisnika;
import tabele.PrikazMesta;
import tabele.PrikazPaket;
import tabele.PrikazPretplata;
import tabele.PrikazSnimka;


/**
 * JavaFX App
 */
public class App extends Application {

    BorderPane root = new BorderPane();
    TabPane tabpane = new TabPane();
    
    private ObservableList<TreeItem<Kontrola>> prikazi = FXCollections.observableArrayList(
            new TreeItem<>(new PrikazMesta()), new TreeItem<>(new PrikazKorisnika()),
            new TreeItem<>(new PrikazKategorija()), new TreeItem<>(new PrikazSnimka()),
            new TreeItem<>(new PrikazPaket()), new TreeItem<>(new PrikazPretplata()),
            new TreeItem<>(new PrikazKategorijaSnimak()), new TreeItem<>(new PrikazGledanje()),
            new TreeItem<>(new PrikazOcena())
    );
    
    private ObservableList<TreeItem<Kontrola>> forme = FXCollections.observableArrayList(
            new TreeItem<>(new FormaMesto()), new TreeItem<>(new FormaKorisnik()),
            new TreeItem<>(new FormaKategorija()), new TreeItem<>(new FormaSnimak()),
            new TreeItem<>(new FormaPaket()), new TreeItem<>(new FormaPretplata()),
            new TreeItem<>(new FormaGledanje()), new TreeItem<>(new FormaOcena()),
            new TreeItem<>(new FormaDodavanjeKategorije())
    );
    
    private ObservableList<TreeItem<Kontrola>> brisanja = FXCollections.observableArrayList(
            new TreeItem<>(new BrisanjeSnimka()),new TreeItem<>(new BrisanjeOcene())
    );
    
    private ObservableList<TreeItem<Kontrola>> promene = FXCollections.observableArrayList(
            new TreeItem<>(new PromenaMaila()), new TreeItem<>(new PromenaMesta()),
            new TreeItem<>(new PromenaNazivaSnimka()), new TreeItem<>(new PromenaMesecneCene())
    );

    
    private TreeItem<Kontrola> kontroleTabela() {
        for (TreeItem<Kontrola> ti : prikazi) {
            ti.getValue().setOnMouseClicked(event -> {
                ti.getValue().dodaj(tabpane);
            });
        }
        
        TreeItem<Kontrola> prikaz = new TreeItem<>(new Kontrola("Prikaz"));
        prikaz.getChildren().addAll(prikazi);                                               
        
        return prikaz;
    }
    
    private Pane napraviKontrolu() {
        Pane pane = new StackPane();                
        
        forme.forEach(ti -> {
            ti.getValue().setOnMouseClicked(event -> {
                ti.getValue().dodaj(tabpane);
            });
        });
        
        brisanja.forEach(ti -> {
            ti.getValue().setOnMouseClicked(event -> {
                ti.getValue().dodaj(tabpane);
            });
        });
        
        promene.forEach(ti -> {
            ti.getValue().setOnMouseClicked(event -> {
                ti.getValue().dodaj(tabpane);
            });
        });
        
        TreeItem<Kontrola> koren = new TreeItem<>(new Kontrola("Video klub"));
        koren.setExpanded(true);
        
        TreeItem<Kontrola> forma = new TreeItem<>(new Kontrola("Forme"));
        forma.getChildren().addAll(forme);
        
        TreeItem<Kontrola> brisanje = new TreeItem<>(new Kontrola("Brisanje"));
        brisanje.getChildren().addAll(brisanja);
        
        TreeItem<Kontrola> promena = new TreeItem<>(new Kontrola("Promene"));
        promena.getChildren().addAll(promene);
                
        koren.getChildren().addAll(kontroleTabela(), forma, brisanje, promena);
        
        TreeView<Kontrola> glavni = new TreeView<>(koren);        
        pane.getChildren().add(glavni);
        return pane;
    }
        
    
    @Override
    public void start(Stage primaryStage) {       
        root.setLeft(napraviKontrolu());
        root.setCenter(tabpane);                
        
        Scene scene = new Scene(root, 1200, 450);
        scene.getStylesheets().add(getClass().getResource("mojiStilovi.css").toExternalForm());
        primaryStage.setTitle("Video klub");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);                
    }

}