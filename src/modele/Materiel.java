
package modele;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert.AlertType;

public class Materiel
{
    protected int numero = 0;
    protected String design = "";
    protected int prixUnitaire = 0;
    private int stock = 0;
    protected int totalVendu = 0;
    
    public static void supprimer( final int numero )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "DELETE FROM materiel WHERE num_materiel='" + numero + "'";
        try
        {
            connexion.executeUpdate( "DELETE FROM achat WHERE num_materiel='" + numero + "'" );
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.WARNING, "REMARQUE" , "Suppression effectuée" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public static void modifierDesign( final int numero , final String design )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE materiel SET design='" + design + "' WHERE num_materiel='" + numero + "'";
        try
        {
            connexion.executeUpdate( requeteSQL );
            Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie :)" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
    }
    
    public static void modifierPrixUnitaire( final int numero , final String prixUnitaire )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE materiel SET pu='" + prixUnitaire + "' WHERE num_materiel='" + numero + "'";
        try
        {
            if( Integer.parseInt( prixUnitaire ) > 0 )
            {
                connexion.executeUpdate( requeteSQL );
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie :)" );
            }
            else
                Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        catch( Exception e )
        {
            Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
    }
    
    public static void modifierStock( final int numero , final String stock )
    {
        Statement connexion = HighTechMarket.connecter();
        final String requeteSQL = "UPDATE materiel SET stock='" + stock + "' WHERE num_materiel='" + numero + "'";
        try
        {
            if( Integer.parseInt( stock ) > 0 )
            {
                connexion.executeUpdate( requeteSQL );
                Message.afficher( AlertType.INFORMATION, "REMARQUE" , "Modification reussie :)" );
            }
            else
                Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
        catch( SQLException e )
        {
            Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
            System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
        }
        catch( Exception e )
        {
            Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez entrer un nombre valide" );
        }
    }
    
    public void ajouter( final String design , final String prixUnitaire , final String stock )
    {
        Statement connexion = HighTechMarket.connecter();
        try
        {
            Integer.parseInt( prixUnitaire );
            Integer.parseInt( stock );
        }
        catch( Exception e )
        {
            Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez remplir correctement les champs du formulaire" );
            return;
        }
        
        if( design.equals( "" ) || prixUnitaire.equals( "" ) || stock.equals( "" ) || Integer.parseInt( prixUnitaire ) < 0 || Integer.parseInt( stock ) < 0 )
        {
            Message.afficher( AlertType.ERROR, "REMARQUE" , "Veuillez remplir correctement les champs du formulaire" );
            return;
        }
        
       String requeteSQL = "SELECT * FROM materiel WHERE design='" + design + "'";
       ResultSet resultatSELECT;
       try
       {
           resultatSELECT = connexion.executeQuery( requeteSQL );
           if( resultatSELECT.next() )
           {
               Message.afficher( AlertType.WARNING, "REMARQUE" , "Désolé ce nom de produit est déjà utilisé. Veuillez réessayer" );
               return;
           }
       }
       catch( SQLException e )
       {
           Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
           System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
           return;
       }
       
       requeteSQL = "INSERT INTO materiel ( design , pu , stock ) VALUES ( '" + design + "' , '" + prixUnitaire + "' , '" + stock + "' )";
       try
       {
           connexion.executeUpdate( requeteSQL );
           Message.afficher( AlertType.INFORMATION , "REMARQUE" , "Le matériel " + design + " a bien été ajouté" );
       }
       catch( SQLException e )
       {
           Message.afficher( AlertType.WARNING , "ATTENTION !!!" , "Une erreur SQL s'est produite :(\n" );
           System.out.println( "Une erreur SQL s'est produite :\n" + e + " Voici la requete :\n" + requeteSQL );
           return;
       }
    }
    
    public int getNumero()
    {
        return numero;
    }
    
    public StringProperty colonneNumero()
    {
        String resultat = ( numero < 10 ) ? "0" + String.valueOf( numero ) : String.valueOf( numero );
        resultat = "M" + resultat;
        return new SimpleStringProperty( resultat );
    }
     
    public String getDesign()
    {
        return design;
    }
    
    public SimpleStringProperty colonneDesign()
    {
        return new SimpleStringProperty( design.toUpperCase() );
    }
    
    public int getPrixUnitaire()
    {
        return prixUnitaire;
    }
    
    public StringProperty colonnePrixUnitaire()
    {
        return new SimpleStringProperty( String.valueOf( prixUnitaire ) + " FMG" );
    }
    
    public int getStock()
    {
        return stock;
    }
    
    public StringProperty colonneStock()
    {
        return new SimpleStringProperty( String.valueOf( stock ) );
    }
    
    public int getTotalVendu()
    {
        return totalVendu;
    }
    
    public StringProperty colonneTotalVendu()
    {
        return new SimpleStringProperty( String.valueOf( totalVendu ) + " FMG" );
    }
    
    public void setNumero( final int numero )
    {
        this.numero = numero;
    }
    
    public void setDesign( final String design )
    {
        this.design = design;
    }
    
    public void setPrixUnitaire( final int prixUnitaire )
    {
        this.prixUnitaire = prixUnitaire;
    }
    
    public void setStock( final int stock )
    {
        this.stock = stock;
    }
    
    public void setTotalVendu( final int totalVendu )
    {
        this.totalVendu = totalVendu;
    }
}
