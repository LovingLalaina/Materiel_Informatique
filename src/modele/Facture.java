package modele;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import com.github.royken.converter.FrenchNumberToWords;

public class Facture extends Achat
{
    private String nomClient = "";
    private String design = "";
    private int prixUnitaire = 0;
    private int montant = 0;
    
    public static String montantLettre( final int totalMontant )
    {
        return "aze";//FrenchNumberToWords.convert( totalMontant ) + " FMG";
    }
    
    public String getNomClient()
    {
        return nomClient;
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
    
    public int getMontant()
    {
        return montant;
    }
    
    public StringProperty colonneMontant()
    {
        return new SimpleStringProperty( String.valueOf( montant ) + " FMG" );
    }
    
    public StringProperty colonneQuantite()
    {
        return new SimpleStringProperty( String.valueOf( quantite ) );
    }
    
    public void setNomClient( final String nomClient )
    {
        this.nomClient = nomClient;
    }
    
    public void setDesign( final String design )
    {
        this.design = design;
    }
    
    public void setPrixUnitaire( final int prixUnitaire )
    {
        this.prixUnitaire = prixUnitaire;
    }
    
    public void setMontant( final int montant )
    {
        this.montant =  montant;
    }
}