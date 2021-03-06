package de.hawhh.informatik.sml.kino.fachwerte;
public final class GeldBetrag // final, weil es eine Wertklasse ist
{
    private final int _euro;
    private final int _cent;

    
   
    public static GeldBetrag get(int betrag)
    {
        return new GeldBetrag(betrag);
    }
    
    /**
     * private Konstruktor
     * @param eurocent
     */
    private GeldBetrag (int eurocent)
    {
        _euro = eurocent / 100;
        _cent = eurocent % 100;
        
    }
    /**
     * 
     * @return EuroCent
     */
    public int getEurocent()
    {
        return _euro * 100 + _cent;
    }    

  
   
    /**
     * Hier werden zwei Geldbtreage addiert.
     * @require this.gibEurocent()> 0
     */ 
    public GeldBetrag addiere(GeldBetrag betrag)
    {
        assert this.getEurocent() > 0 : "Vorbedingung verletzt: betrag > 0";
        return new GeldBetrag (getEurocent() + betrag.getEurocent()); 
    }

    
    /**
     * Hier werden zwei Betraege von einander subtrahiert und ausgegeben.
     * @require this.gibEurocent() == betrag.gibEurocent()
     */
    public GeldBetrag subtrahiere(GeldBetrag betrag)
    {  
        assert this.getEurocent() >= betrag.getEurocent() : "Vorbedingung verletzt: this.gibEurocent() >= betrag.gibEurocent()";
        return new GeldBetrag (getEurocent() - betrag.getEurocent());
    }

    
    /**
     * Hier wird ein Betrag mit einer Zahl multipliziert und das Ergebnis wird ausgegeben..
     * @param _zahl
     * @require _zahl> 0
     */
    public GeldBetrag mal(int zahl)
    {
        assert zahl > 0 : "Vorbedingung verletzt: zahl > 0";
        return new GeldBetrag (getEurocent() * zahl);
    }
    





    /**
     * Gibt diesen Geldbetrag in der Form "10,21" zurück.
     */
    @Override
    public String toString()
    {
        return getFormatiertenString();
    }

    public int konvertiere()
    {
        return Integer.parseInt(getFormatiertenString());       
    }
    /**
     *  Hier wird der eurocent Betrag formatiert und im Format EE,CC ausgegeben.
     *  @return formatierte Ausgabe des eurocents
     *  @ensure result != null
     */
     public String getFormatiertenString()
     {
         String result = "" + _euro + ",";
         
         if(_cent <10)
         {
             result += "0" + _cent;
         }
         else
         {
             result += _cent;
         }
         
         return result;
     }


     @Override
     public int hashCode()
     {
         final int prime = 31;
         int result = 1;
         result = prime + _cent;
         result = prime * result + _euro;
         return result;
     }
     @Override
     public boolean equals(Object o)
     {
         boolean result = false;
         
         if(o instanceof GeldBetrag)
         {
             GeldBetrag og= (GeldBetrag) o;
             result = _euro == og._euro && _cent == og._cent;
         }
         return result;
     }

}