import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import mockesimerkki.Asiakas;
import mockesimerkki.IHinnoittelija;
import mockesimerkki.Tilaus;
import mockesimerkki.TilaustenKäsittely;
import mockesimerkki.Tuote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TilaustenKäsittelyMockitoTest {
	@Mock
	IHinnoittelija hinnoittelijaMock;
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		}
 @Test
 public void testaaKäsittelijäWithMockitoHinnoittelija() {
 // Arrange
 float alkuSaldo = 100.0f;
 float listaHinta = 30.0f;
 float alennus = 20.0f;
 float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
 Asiakas asiakas = new Asiakas(alkuSaldo);
 Tuote tuote = new Tuote("TDD in Action", listaHinta);
 // Record
 when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
.thenReturn(alennus);
 // Act
 TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
 käsittelijä.setHinnoittelija(hinnoittelijaMock);
 käsittelijä.käsittele(new Tilaus(asiakas, tuote));
 // Assert
 assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
 }
}