package modele;

import java.util.Calendar;

public class Calendrier
{
    final private int jourDuMois;
    final private int mois;
    final private int annee;
    final public static String MOIS_FRANCAIS[] = { "Janvier" , "Février" , "Mars" , "Avril" , "Mai" , "Juin" , "Juillet" , "Août" , "Septembre" , "Octobre" , "Novembre" , "Décembre" };

    public Calendrier()
    {
            Calendar aujourdhui = Calendar.getInstance();
            jourDuMois = aujourdhui.get( Calendar.DAY_OF_MONTH );
            mois = aujourdhui.get( Calendar.MONTH ) + 1;
            annee = aujourdhui.get( Calendar.YEAR );
    }

    public String afficherSurInput()
    {
            final String Annee = Integer.toString( annee );
            final String Mois = ( mois < 10 ) ? "0" + Integer.toString( mois ) : Integer.toString( mois );
            final String JourDuMois = ( jourDuMois < 10 ) ? "0" + Integer.toString( jourDuMois ) : Integer.toString( jourDuMois );
            return Annee + "-" + Mois + "-" + JourDuMois;
    }

    public int getAnnee()
    {
            return this.annee;
    }

    public int getMois()
    {
            return this.mois;
    }
    
    public int getJourDuMois()
    {
            return this.jourDuMois;
    }
    
    public static String moisFrancaisActuel()
    {
        return MOIS_FRANCAIS[new Calendrier().mois];
    }
}
