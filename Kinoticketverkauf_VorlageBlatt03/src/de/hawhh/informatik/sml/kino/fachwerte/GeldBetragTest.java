package de.hawhh.informatik.sml.kino.fachwerte;
import static org.junit.Assert.*;

import org.junit.Test;

public class GeldBetragTest
{
    @Test
    public void testSelektor()
    {
        GeldBetrag betrag1 = GeldBetrag.get(540);
        assertEquals("5,40", betrag1.getFormatiertenString());       
    }
    
    @Test
    public void testAddieren()
    {
        GeldBetrag betrag1 = GeldBetrag.get(500);
        GeldBetrag betrag2 = GeldBetrag.get(400);
        GeldBetrag betrag3 = betrag1.addiere(betrag2);
        GeldBetrag betrag4 = GeldBetrag.get(900);
        assertEquals(betrag4,betrag3);
    }


    @Test
    public void testSubtrahiere()
    {
        GeldBetrag betrag1 = GeldBetrag.get(500);
        GeldBetrag betrag2 = GeldBetrag.get(400);
        GeldBetrag betrag3 = betrag1.subtrahiere(betrag2);
        GeldBetrag betrag4 = GeldBetrag.get(100);
        assertEquals(betrag4,betrag3);  
    }
 

    @Test
    public void testMultipliziere()
    {
        GeldBetrag betrag1 = GeldBetrag.get(500);
        int zahl = 4;
        GeldBetrag betrag2 = betrag1.mal(zahl);
        GeldBetrag betrag3 = GeldBetrag.get(2000);
        assertEquals(betrag3,betrag2);
    }
         
    
    @Test
    public void testEquals()
    {
        GeldBetrag betrag1 = GeldBetrag.get(500);
        GeldBetrag betrag2 = GeldBetrag.get(500);
        GeldBetrag betrag3 = GeldBetrag.get(1500);
        assertEquals(betrag1,betrag2);
        assertFalse(betrag1.equals(betrag3));
        assertEquals(betrag1.hashCode(),betrag2.hashCode());
    }     
 }