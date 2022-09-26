
package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HighTechMarket
{
    public static Statement connecter()
    {
        try
        {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            Connection connexion = DriverManager.getConnection( "jdbc:mysql://localhost:3306/gestion_materiel_info" , "root" , "" );
            return connexion.createStatement();
        }
        catch( ClassNotFoundException | SQLException e )
        {
            Message.afficher( AlertType.WARNING , "OUPS" , "Accès à la base de données refusée par le serveur :(\nVeuillez revenir plus tard" );
            System.exit( 0 );
            return null;
        }
    }
    
    public static ObservableList<Materiel> getMateriels()
    {
        ObservableList<Materiel> ListeMateriels = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
            
        final String requeteSQL = "SELECT *, sum(pu*qte) AS total_vendu FROM materiel LEFT OUTER JOIN achat ON materiel.num_materiel=achat.num_materiel GROUP BY materiel.num_materiel";
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            while( resultatSELECT.next() )
            {
                Materiel monMateriel = new Materiel();
                monMateriel.setNumero( resultatSELECT.getInt( "num_materiel" ) );
                monMateriel.setDesign( resultatSELECT.getString( "design" ) );
                monMateriel.setPrixUnitaire( resultatSELECT.getInt( "pu" ) );
                monMateriel.setStock( resultatSELECT.getInt( "stock" ) );
                monMateriel.setTotalVendu( resultatSELECT.getInt( "total_vendu" ) );
                ListeMateriels.add( monMateriel );
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        return ListeMateriels;
    }
    
    public static ObservableList<Client> getClients( final String requeteSQL )
    {
        ObservableList<Client> ListeClients = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            boolean aucunResultat = true;
            while( resultatSELECT.next() )
            {
                Client monClient = new Client();
                monClient.setNumero( resultatSELECT.getInt( "num_client" ) );
                monClient.setNom( resultatSELECT.getString( "nom" ) );
                monClient.setAdresseMail( resultatSELECT.getString( "adresse_mail" ) );
                ListeClients.add( monClient );
                aucunResultat = false;
            }
            if( !requeteSQL.equals( "SELECT * FROM client" ) && aucunResultat )
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Aucun résultat ne correspond à la recherche :/" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        
        return ListeClients;
    }
    public static ObservableList<Achat> getAchats()
    {
        ObservableList<Achat> ListeAchats = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "SELECT * FROM achat";
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            while( resultatSELECT.next() )
            {
                Achat monAchat = new Achat();
                monAchat.setId( resultatSELECT.getInt( "id" ) );
                monAchat.setNumeroClient( resultatSELECT.getInt( "num_client" ) );
                monAchat.setNumeroMateriel( resultatSELECT.getInt( "num_materiel" ) );
                monAchat.setQuantite( resultatSELECT.getInt( "qte" ) );
                monAchat.setDateAchat( resultatSELECT.getString( "date_achat" ) );
                ListeAchats.add( monAchat );
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        return ListeAchats;
    }
    
    public static ObservableList<Vente> getListe( final String numeroClient , final String choix , final String dateDebut , final String dateFin , final String mois , final String annee )
    {
        ObservableList<Vente> ListeMateriel = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
        String requeteSQL;
        if( choix.equals( "RadioDeuxDates" ) )
            requeteSQL = "SELECT * , sum(qte*pu) AS montant , sum(qte) AS quantite FROM achat JOIN materiel ON achat.num_materiel=materiel.num_materiel JOIN client ON achat.num_client=client.num_client WHERE ( date_achat BETWEEN '" + dateDebut + "' AND '" + dateFin + "' ) AND ( client.num_client='" + numeroClient + "') GROUP BY materiel.num_materiel";
        else if( choix.equals( "RadioAnnee" ) )
            requeteSQL = "SELECT * , sum(qte*pu) AS montant , sum(qte) AS quantite FROM achat JOIN materiel ON achat.num_materiel=materiel.num_materiel JOIN client ON achat.num_client=client.num_client WHERE ( year(date_achat)='" + annee + "' ) AND ( client.num_client='" + numeroClient + "') GROUP BY materiel.num_materiel";
        else
            requeteSQL = "SELECT * , sum(qte*pu) AS montant , sum(qte) AS quantite FROM achat JOIN materiel ON achat.num_materiel=materiel.num_materiel JOIN client ON achat.num_client=client.num_client WHERE ( month(date_achat)='" + mois + "' ) AND ( year(date_achat)='" + new Calendrier().getAnnee() + "' ) AND ( client.num_client='" + numeroClient + "') GROUP BY materiel.num_materiel";
        
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            boolean aucunResultat = true;
            while( resultatSELECT.next() )
            {
                Vente maVente = new Vente();
                maVente.setDesign( resultatSELECT.getString( "design" ) );
                maVente.setPrixUnitaire( resultatSELECT.getInt( "pu" ) );
                maVente.setQuantite( resultatSELECT.getInt( "quantite" ) );
                maVente.setTotalVendu( resultatSELECT.getInt( "montant" ) );
                ListeMateriel.add( maVente );
                aucunResultat = false;
            }
            if( aucunResultat )
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Aucun résultat ne correspond à la recherche :/" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        return ListeMateriel;
    }
    
    public static ObservableList<Facture> getFacture( final String numeroClient , final String annee , TextField NomClientFacture , Label TotalMontant , Label MontantLettre , Label MontantLettreSuite )
    {
        ObservableList<Facture> ListeFacture = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "SELECT * , ( materiel.pu * achat.qte ) AS montant FROM achat JOIN client ON achat.num_client=client.num_client JOIN materiel ON achat.num_materiel=materiel.num_materiel WHERE (achat.num_client='" + numeroClient + "' AND year(date_achat)='" + annee + "')";
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            int totalMontant = 0;
            boolean aucunResultat = true;
            while( resultatSELECT.next() )
            {
                Facture maFacture = new Facture();
                NomClientFacture.setText( resultatSELECT.getString( "nom" ) );
                maFacture.setDesign( resultatSELECT.getString( "design" ) );
                maFacture.setPrixUnitaire( resultatSELECT.getInt( "pu" ) );
                maFacture.setQuantite( resultatSELECT.getInt( "qte" ) );
                maFacture.setMontant( resultatSELECT.getInt( "montant" ) );
                totalMontant += maFacture.getMontant();
                ListeFacture.add( maFacture );
                aucunResultat = false;
            }
            if( aucunResultat )
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Aucun résultat ne correspond à la recherche :/" );
            else
            {
                TotalMontant.setText( String.valueOf( totalMontant ) + " FMG" );
                final String chaine = Facture.montantLettre( totalMontant );
                
                if( chaine.length() >= 47 )
                {
                   MontantLettre.setText( chaine.substring(0, 46) + "-" );
                   MontantLettreSuite.setText( chaine.substring( 46 ) ); 
                }
                else
                    MontantLettre.setText( chaine );
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        return ListeFacture;
    }
    
    public static ObservableList<ChiffreAffaire> getChiffreAffaire( final String annee )
    {
        ObservableList<ChiffreAffaire> ListeChiffreAffaire = FXCollections.observableArrayList();
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "SELECT * , sum(pu*qte) AS chiffre_affaire FROM client LEFT OUTER JOIN achat ON client.num_client=achat.num_client LEFT OUTER JOIN materiel ON achat.num_materiel=materiel.num_materiel WHERE year(date_achat)='" + annee + "' GROUP BY client.num_client";
        ResultSet resultatSELECT;
        try
        {
            resultatSELECT = connexion.executeQuery( requeteSQL );
            
            boolean aucunResultat = true;
            while( resultatSELECT.next() )
            {
                ChiffreAffaire monClient = new ChiffreAffaire();
                monClient.setNumero( resultatSELECT.getInt( "num_client" ) );
                monClient.setNom( resultatSELECT.getString( "nom" ) );
                monClient.setChiffreAffaire( resultatSELECT.getInt( "chiffre_affaire" ) );
                ListeChiffreAffaire.add( monClient );
                aucunResultat = false;
            }
            if( aucunResultat )
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Aucun résultat ne correspond à la recherche :/" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        return ListeChiffreAffaire;
    }
}
