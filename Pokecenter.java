package nl.delphinity.pokemon.model.area;

import nl.delphinity.pokemon.model.general.Pokemon;

import java.util.List;

public class Pokecenter {

    private final String name;

    public Pokecenter(String name) {
        this.name = name;
    }

 // TODO: US-PKM-O-12
    public void healPokemon(List<Pokemon> pokemonToHeal) {
            for (Pokemon p : pokemonToHeal) {
                System.out.println("Healing your Pokemon...");
                System.out.println("zzzzzzz");
                
                p.setCurrentHp(p.getMaxHp());
                System.out.println("Your Pokemon is healed!");
            }
        }
}
