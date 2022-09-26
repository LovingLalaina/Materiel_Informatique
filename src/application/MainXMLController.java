
package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import modele.Achat;
import modele.Calendrier;
import modele.ChiffreAffaire;
import modele.Materiel;
import modele.Client;
import modele.Facture;
import modele.HighTechMarket;
import modele.Message;
import modele.Vente;

public class MainXMLController implements Initializable
{
    @FXML
    private TextField DesignMateriel;
    @FXML
    private TextField PrixUnitaire;
    @FXML
    private TextField StockMateriel;
    @FXML
    private TableView<Materiel> TableMateriel;
    @FXML
    private TableColumn<Materiel, String> ColonneMaterielNumero;
    @FXML
    private TableColumn<Materiel, String> ColonneMaterielDesign;
    @FXML
    private TableColumn<Materiel, String> ColonneMaterielPrixUnitaire;
    @FXML
    private TableColumn<Materiel, String> ColonneMaterielStock;
    @FXML
    private TableColumn<Materiel, String> ColonneMaterielTotalVendu;
    @FXML
    private TextField NomClient;
    @FXML
    private TextField PrenomClient;
    @FXML
    private TextField AdresseMail;
    @FXML
    private TextField RechercheClient;
    @FXML
    private TableView<Client> TableClient;
    @FXML
    private TableColumn<Client, String> ColonneClientNumero;
    @FXML
    private TableColumn<Client, String> ColonneClientNom;
    @FXML
    private TableColumn<Client, String> ColonneClientAdresseMail;
    @FXML
    private TextField NumeroClient;
    @FXML
    private TextField NumeroMateriel;
    @FXML
    private TextField Quantite;
    @FXML
    private DatePicker DateAchat;
    @FXML
    private TableView<Achat> TableAchat;
    @FXML
    private TableColumn<Achat, String> ColonneAchatNumeroClient;
    @FXML
    private TableColumn<Achat, String> ColonneAchatNumeroMateriel;
    @FXML
    private TableColumn<Achat, String> ColonneAchatQuantite;
    @FXML
    private TableColumn<Achat, String> ColonneAchatDateAchat;
    @FXML
    private TableColumn<Achat, String> ColonneAchatId;
    @FXML
    private TextField NumeroClientListe;
    @FXML
    private RadioButton RadioDeuxDates;
    @FXML
    private DatePicker DateDebut;
    @FXML
    private DatePicker DateFin;
    @FXML
    private RadioButton RadioMois;
    @FXML
    private ComboBox<String> MoisListe;
    @FXML
    private RadioButton RadioAnnee;
    @FXML
    private TextField AnneeListe;
    @FXML
    private TableView<Vente> TableListe;
    @FXML
    private TableColumn<Vente, String> ColonneListeDesign;
    @FXML
    private TableColumn<Vente, String> ColonneListePrixUnitaire;
    @FXML
    private TableColumn<Vente, String> ColonneListeQuantite;
    @FXML
    private TableColumn<Vente, String> ColonneListeMontant;
    @FXML
    private TextField NumeroClientFacture;
    @FXML
    private TextField NomClientFacture;
    @FXML
    private TextField AnneeFacture;
    @FXML
    private TableView<Facture> TableFacture;
    @FXML
    private TableColumn<Facture, String> ColonneFactureDesign;
    @FXML
    private TableColumn<Facture, String> ColonneFacturePrixUnitaire;
    @FXML
    private TableColumn<Facture, String> ColonneFactureQuantite;
    @FXML
    private TableColumn<Facture, String> ColonneFactureMontant;
    @FXML
    private Label TotalMontant;
    @FXML
    private Label MontantLettre;
    @FXML
    private Label MontantLettreSuite;
    @FXML
    private TextField AnneeChiffreAffaire;
    @FXML
    private TableView<ChiffreAffaire> TableChiffreAffaire;
    @FXML
    private TableColumn<ChiffreAffaire, String> ColonneChiffreAffaireNumeroClient;
    @FXML
    private TableColumn<ChiffreAffaire, String> ColonneChiffreAffaireNomClient;
    @FXML
    private TableColumn<ChiffreAffaire, String> ColonneChiffreAffaireChiffreAffaire;
    @FXML
    private BarChart<?, ?> Histogramme;
    @FXML
    private NumberAxis AxeChiffreAffaire;
    @FXML
    private CategoryAxis AxeClient;
    
    
    @Override
    public void initialize( URL url , ResourceBundle rb )
    {
        initialiserInput();
        configurerRadio();
        creerMoisListe();
        initialiserTableau();
        afficherMateriels();
        afficherClients( "SELECT * FROM client" );
        afficherAchats();
    }
    
    private void initialiserInput()
    {
        DateAchat.setValue( LocalDate.now() );
        DateDebut.setValue( LocalDate.now() );
        DateFin.setValue( LocalDate.now() );
        AnneeChiffreAffaire.setText( String.valueOf( new Calendrier().getAnnee() ) );
        AnneeFacture.setText( String.valueOf( new Calendrier().getAnnee() ) );
        AnneeListe.setText( String.valueOf( new Calendrier().getAnnee() ) );
    }
    
    private void configurerRadio()
    {
        final ToggleGroup Choix = new ToggleGroup();
        RadioDeuxDates.setToggleGroup( Choix );
        RadioMois.setToggleGroup( Choix );
        RadioAnnee.setToggleGroup( Choix );
    }
    
    private void creerMoisListe()
    {
        List<String> listeMois = new ArrayList<>();
        listeMois.addAll( Arrays.asList( Calendrier.MOIS_FRANCAIS ) );
        MoisListe.setItems( FXCollections.observableArrayList( listeMois ) );
        MoisListe.setValue( Calendrier.moisFrancaisActuel() );
    }
    
    private void initialiserTableau()
    {
        ColonneMaterielNumero.setCellValueFactory( cell->cell.getValue().colonneNumero() );
        ColonneMaterielDesign.setCellValueFactory( cell->cell.getValue().colonneDesign() );
        ColonneMaterielDesign.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneMaterielPrixUnitaire.setCellValueFactory( cell->cell.getValue().colonnePrixUnitaire() );
        ColonneMaterielPrixUnitaire.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneMaterielStock.setCellValueFactory( cell->cell.getValue().colonneStock() );
        ColonneMaterielStock.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneMaterielTotalVendu.setCellValueFactory( cell->cell.getValue().colonneTotalVendu() );
        ColonneClientNumero.setCellValueFactory( cell->cell.getValue().colonneNumero() );
        ColonneClientNom.setCellValueFactory( cell->cell.getValue().colonneNom() );
        ColonneClientNom.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneClientAdresseMail.setCellValueFactory( cell->cell.getValue().colonneAdresseMail() );
        ColonneClientAdresseMail.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneAchatNumeroClient.setCellValueFactory( cell->cell.getValue().colonneNumeroClient() );
        ColonneAchatNumeroMateriel.setCellValueFactory( cell->cell.getValue().colonneNumeroMateriel() );
        ColonneAchatQuantite.setCellValueFactory( cell->cell.getValue().colonneQuantite() );
        ColonneAchatQuantite.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneAchatDateAchat.setCellValueFactory( cell->cell.getValue().colonneDateAchat() );
        ColonneAchatDateAchat.setCellFactory( TextFieldTableCell.forTableColumn() );
        ColonneAchatId.setCellValueFactory( cell->cell.getValue().colonneId() );
        ColonneFactureDesign.setCellValueFactory( cell->cell.getValue().colonneDesign() );
        ColonneFacturePrixUnitaire.setCellValueFactory( cell->cell.getValue().colonnePrixUnitaire() );
        ColonneFactureQuantite.setCellValueFactory( cell->cell.getValue().colonneQuantite() );
        ColonneFactureMontant.setCellValueFactory( cell->cell.getValue().colonneMontant() );
        ColonneChiffreAffaireNumeroClient.setCellValueFactory( cell->cell.getValue().colonneNumero() );
        ColonneChiffreAffaireNomClient.setCellValueFactory( cell->cell.getValue().colonneNom() );
        ColonneChiffreAffaireChiffreAffaire.setCellValueFactory( cell->cell.getValue().colonneChiffreAffaire() );
        ColonneListeDesign.setCellValueFactory( cell->cell.getValue().colonneDesign() );
        ColonneListePrixUnitaire.setCellValueFactory( cell->cell.getValue().colonnePrixUnitaire() );
        ColonneListeQuantite.setCellValueFactory( cell->cell.getValue().colonneQuantite() );
        ColonneListeMontant.setCellValueFactory( cell->cell.getValue().colonneTotalVendu() );
    }
    
    private void afficherMateriels()
    {
        TableMateriel.setItems( HighTechMarket.getMateriels() );
    }
    
    private void afficherClients( final String requeteSQL )
    {
        TableClient.setItems( HighTechMarket.getClients( requeteSQL ) );
    }
    
    private void afficherAchats()
    {
        TableAchat.setItems( HighTechMarket.getAchats() );
    }
    
    @FXML
    private void rechercherClient( ActionEvent event )
    {
        final String recherche = RechercheClient.getText();
        if( !recherche.equals( "" ) )
            TableClient.setItems( HighTechMarket.getClients( "SELECT * FROM client WHERE ( num_client LIKE '%" + recherche + "' OR num_client LIKE '" + recherche + "%' OR num_client LIKE '%" + recherche + "%' OR num_client='" + recherche + "' OR nom LIKE '%" + recherche + "' OR nom LIKE '" + recherche + "%' OR nom LIKE '%" + recherche + "%' OR nom='" + recherche + "' ) GROUP BY num_client LIMIT 30" ) );
        else
            afficherClients( "SELECT * FROM client");
    }
    
    @FXML
    private void ajouterMateriel( ActionEvent event )
    {
        Materiel monMateriel = new Materiel();
        final String design = DesignMateriel.getText();
        final String prixUnitaire = PrixUnitaire.getText();
        final String stock = StockMateriel.getText();
        monMateriel.ajouter( design , prixUnitaire , stock );
        DesignMateriel.setText( "" );
        PrixUnitaire.setText( "" );
        StockMateriel.setText( "" );
        afficherMateriels();
    }

    @FXML
    private void ajouterClient( ActionEvent event )
    {
        Client monClient = new Client();
        final String nomComplet = NomClient.getText().toUpperCase() + " " + PrenomClient.getText();
        final String adresseMail = AdresseMail.getText();
        monClient.ajouter( nomComplet , adresseMail );
        NomClient.setText( "" );
        PrenomClient.setText( "" );
        AdresseMail.setText( "" );
        afficherClients( "SELECT * FROM client" );
    }

    @FXML
    private void ajouterAchat( ActionEvent event )
    {
        Achat monAchat = new Achat();
        final String numeroClient = NumeroClient.getText();
        final String numeroMateriel = NumeroMateriel.getText();
        final String quantite = Quantite.getText();
        final String dateAchat = DateAchat.getValue().toString();
        monAchat.ajouter( numeroClient , numeroMateriel , quantite , dateAchat );
        NumeroClient.setText( "" );
        NumeroMateriel.setText( "" );
        Quantite.setText( "" );
        DateAchat.setValue( LocalDate.now() );
        afficherAchats();
    }
    
    @FXML
    private void afficherListe( ActionEvent event )
    {
        final String numeroClient = NumeroClientListe.getText();
        final String dateDebut = DateDebut.getValue().toString();
        final String dateFin = DateFin.getValue().toString();
        final String mois = MoisListe.getValue();
        final String annee = AnneeListe.getText();
        RadioButton RadioSelectionne = ( RadioButton ) RadioDeuxDates.getToggleGroup().getSelectedToggle();
        final String choix = RadioSelectionne.getId();
        if( numeroClient.equals( "" ) ||  ( choix.equals( "RadioDeuxDates" ) && ( dateDebut.equals( "" ) || dateFin.equals( "" ) ) ) || ( choix.equals( "RadioMois" ) && mois.equals( "" ) ) || ( choix.equals( "RadioAnnee" ) && annee.equals( "" ) ) )
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement les champs du formulaire !!" );
        else
            TableListe.setItems( HighTechMarket.getListe( numeroClient , choix , dateDebut , dateFin , mois , annee ) );
    }

    @FXML
    private void facturer( ActionEvent event )
    {
        Facture maFacture = new Facture();
        final String numeroClient = NumeroClientFacture.getText();
        final String annee = AnneeFacture.getText();
        if( numeroClient.equals( "" ) || annee.equals( "" ) )
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement les champs du formulaire !!" );
        else
            TableFacture.setItems( HighTechMarket.getFacture( numeroClient , annee , NomClientFacture , TotalMontant , MontantLettre , MontantLettreSuite ) );
    }
    
    @FXML
    private void afficherChiffreAffaire( ActionEvent event )
    {
        String annee = AnneeChiffreAffaire.getText();
        if( annee.equals( "" ) )
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement le champs !!" );
        else
        {
            TableChiffreAffaire.setVisible( true );
            Histogramme.setVisible( false );
            TableChiffreAffaire.setItems( HighTechMarket.getChiffreAffaire( annee ) );
        }
    }
    @FXML
    private void afficherHistogramme( ActionEvent event )
    {
        final String annee = AnneeChiffreAffaire.getText();
        if( annee.equals( "" ) )
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement le champs !!" );
        else
        {
            Histogramme.setVisible( true );
            TableChiffreAffaire.setVisible( false );
            ChiffreAffaire.afficherHistogramme( annee , Histogramme , AxeClient , AxeChiffreAffaire );
        }
    }
    
    @FXML
    private void modifierClientNom( CellEditEvent<Client, String> event )
    {
        final String nom = event.getNewValue();
        if( !nom.equals( "" ) )
            Client.modifierNom( event.getRowValue().getNumero() , nom );
        afficherClients( "SELECT * FROM client" );
    }
    
    @FXML
    private void modifierClientAdresseMail( CellEditEvent<Client, String> event )
    {
        final String adresseMail = event.getNewValue();
        if( !adresseMail.equals( "" ) )
            Client.modifierAdresseMail( event.getRowValue().getNumero() , adresseMail );
        afficherClients( "SELECT * FROM client" );
    }

    @FXML
    private void modifierMaterielDesign( CellEditEvent<Materiel, String> event )
    {
        final String design = event.getNewValue();
        if( !design.equals( "" ) )
            Materiel.modifierDesign( event.getRowValue().getNumero() , design );
        afficherMateriels();
    }

    @FXML
    private void modifierMaterielPrixUnitaire( CellEditEvent<Materiel, String> event )
    {
        final String prixUnitaire = event.getNewValue();
        if( !prixUnitaire.equals( "" ) )
            Materiel.modifierPrixUnitaire( event.getRowValue().getNumero() , prixUnitaire );
        afficherMateriels();
    }

    @FXML
    private void modifierMaterielStock( CellEditEvent<Materiel, String> event )
    {
        final String stock = event.getNewValue();
        if( !stock.equals( "" ) )
            Materiel.modifierStock( event.getRowValue().getNumero() , stock );
        afficherMateriels();
    }

    @FXML
    private void modifierAchatQuantite( CellEditEvent<Achat, String> event )
    {
        final String quantite = event.getNewValue();
        if( !quantite.equals( "" ) )
            Achat.modifierQuantite( event.getRowValue().getId() , quantite );
        afficherAchats();
        afficherMateriels();
    }

    @FXML
    private void modifierAchatDateAchat( CellEditEvent<Achat, String> event )
    {
        final String dateAchat = event.getNewValue();
        if( !dateAchat.equals( "" ) )
            Achat.modifierDateAchat( event.getRowValue().getId() , dateAchat );
        afficherAchats();
    }
    
    @FXML
    private void supprimerMateriel( ActionEvent event )
    {
        Materiel monMateriel = TableMateriel.getSelectionModel().getSelectedItem();
        if( monMateriel == null )
            Message.afficher( AlertType.INFORMATION , "REMARQUE" , "Veuillez selectionner un enregistrement dans la table pour le supprimer" );
        else if( Message.afficher( AlertType.CONFIRMATION , "SUPPRESSION" , "Êtes-vous vraiment sûr de vouloir supprimer ce matériel?" ) )
            Materiel.supprimer( monMateriel.getNumero() );
        afficherMateriels();
        afficherAchats();
    }
    
    @FXML
    private void supprimerClient( ActionEvent event )
    {
        Client monClient = TableClient.getSelectionModel().getSelectedItem();
        if( monClient == null )
            Message.afficher( AlertType.INFORMATION , "REMARQUE" , "Veuillez selectionner un enregistrement dans la table pour le supprimer" );
        else if( Message.afficher( AlertType.CONFIRMATION , "SUPPRESSION" , "Êtes-vous vraiment sûr de vouloir supprimer ce client?" ) )
            Client.supprimer( monClient.getNumero() );
        afficherClients( "SELECT * FROM client" );
        afficherAchats();
    }

    @FXML
    private void supprimerAchat( ActionEvent event )
    {
        Achat monAchat = TableAchat.getSelectionModel().getSelectedItem();
        if( monAchat == null )
            Message.afficher( AlertType.INFORMATION , "REMARQUE" , "Veuillez selectionner un enregistrement dans la table pour le supprimer" );
        else if( Message.afficher( AlertType.CONFIRMATION , "SUPPRESSION" , "Êtes-vous vraiment sûr de vouloir supprimer cet achat?" ) )
            Achat.supprimer( monAchat.getId() );
        afficherMateriels();
        afficherAchats();
    }
}
