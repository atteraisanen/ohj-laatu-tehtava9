import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import mockesimerkki.Asiakas;
import mockesimerkki.IHinnoittelija;
import mockesimerkki.Tilaus;
import mockesimerkki.TilaustenKäsittely;
import mockesimerkki.Tuote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TilaustenKäsittelyMockTest {

    @Mock
    IHinnoittelija hinnoittelijaMock;

    @InjectMocks
    TilaustenKäsittely käsittelijä;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaaKäsittelijä1() {
        float alkuSaldo = 100.0f;
        float listaHinta = 80.0f;
        float alennus = 15.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alennus);
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).aloita();
        verify(hinnoittelijaMock).setAlennusProsentti(asiakas, alennus);
        verify(hinnoittelijaMock).lopeta();
    }

    @Test
    public void testaaKäsittelijä2() {
        float alkuSaldo = 100.0f;
        float listaHinta = 120.0f;
        float alkuperäinenAlennus = 10.0f;
        float lisäAlennus = 5.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alkuperäinenAlennus + lisäAlennus) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);

        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote)).thenReturn(alkuperäinenAlennus);
        doNothing().when(hinnoittelijaMock).setAlennusProsentti(asiakas, alkuperäinenAlennus + lisäAlennus);

        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));

        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock).aloita();
        verify(hinnoittelijaMock).setAlennusProsentti(asiakas, alkuperäinenAlennus + lisäAlennus);
        verify(hinnoittelijaMock).lopeta();
    }
}