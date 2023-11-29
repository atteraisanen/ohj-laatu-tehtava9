import mockesimerkki.Asiakas;
import mockesimerkki.IHinnoittelija;
import mockesimerkki.Tuote;
public class FakeHinnoittelija implements IHinnoittelija {
 private float alennus;
 public FakeHinnoittelija(float alennus) {
 this.alennus = alennus;
 }
 public float getAlennusProsentti(Asiakas asiakas, Tuote tuote) {
 return alennus;
 }
@Override
public void setAlennusProsentti(Asiakas asiakas, float prosentti) {
}
@Override
public void aloita() {
}
@Override
public void lopeta() {
}
}
