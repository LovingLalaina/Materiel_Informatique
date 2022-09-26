
package modele;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;

public class Client
{
    protected int numero = 0;
    protected String nom = "";
    private String adresseMail = "";
    
    public static int numero( String chaineNumero )
    {
        return Integer.parseInt( chaineNumero.substring(2) );
    }
    
    public static void supprimer( final int numero )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "DELETE FROM client WHERE num_client='" + numero + "'";
        try
        {
            connexion.executeUpdate( "DELETE FROM achat WHERE num_client='" + numero + "'" );
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.WARNING, "REMARQUE" , "Suppression effectuée" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public static void modifierNom( final int numero , final String nom )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE client SET nom='" + nom + "' WHERE num_client='" + numero + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public static void modifierAdresseMail( final int numero , final String adresseMail )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE client SET adresse_mail='" + adresseMail + "' WHERE num_client='" + numero + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public void ajouter( final String nom , final String adresseMail )
    {
        Message resultat = new Message();
        Statement connexion = HighTechMarket.connecter();
        
        if( nom.equals( "" ) || adresseMail.equals( "" ) )
        {
            Message.afficher( AlertType.ERROR , "REMARQUE" , "Veuillez remplir correctement les champs du formulaire !!" );
            return;
        }
        
        String requeteSQL = "SELECT * FROM client WHERE nom='" + nom + "'";
        try
        {
            ResultSet resultatSELECT = connexion.executeQuery( requeteSQL );
            if( resultatSELECT.next() )
            {
               Message.afficher( AlertType.ERROR , "ATTENTION" , "Désolé ce nom de client est déjà utilisé. Veuillez réessayer" );
               return;
            }
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
            return;
        }
        
        requeteSQL = "INSERT INTO client ( nom , adresse_mail ) VALUES ( '" + nom + "' , '" + adresseMail + "' )";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION , "REMARQUE" , "Le client " + nom + " a bien été ajouté" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public int getNumero()
    {
        return numero;
    }
    
    public StringProperty colonneNumero()
    {
        String resultat = ( numero < 10 ) ? "0" + String.valueOf( numero ) : String.valueOf( numero );
        resultat = "CL" + resultat;
        return new SimpleStringProperty( resultat );
    }
     
    public String getNom()
    {
        return nom;
    }
    
    public SimpleStringProperty colonneNom()
    {
        return new SimpleStringProperty( nom );
    }
    
    public String getAdresseMail()
    {
        return adresseMail;
    }
    
    public SimpleStringProperty colonneAdresseMail()
    {
        return new SimpleStringProperty( adresseMail );
    }
    
    public void setNumero( final int numero )
    {
        this.numero = numero;
    }
    
    public void setNom( final String nom )
    {
        this.nom = nom;
    }
    
    public void setAdresseMail( final String adresseMail )
    {
        this.adresseMail = adresseMail;
    }
}
