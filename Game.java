package nl.delphinity.pokemon.model.general;

import java.util.Scanner;

import nl.delphinity.pokemon.model.area.Area;
import nl.delphinity.pokemon.model.area.Pokecenter;
import nl.delphinity.pokemon.model.battle.Battle;
import nl.delphinity.pokemon.model.item.ItemType;
import nl.delphinity.pokemon.model.trainer.Badge;
import nl.delphinity.pokemon.model.trainer.GymLeader;
import nl.delphinity.pokemon.model.trainer.Trainer;

import java.util.*;

public class Game {

	private static final ArrayList<Area> areas = new ArrayList<>();
	private static final Scanner sc = new Scanner(System.in);
	private static Trainer trainer = null;


	static {

		// PEWTER City
		Pokecenter pewterCenter = new Pokecenter("Pewter City's Pokecenter");
		Area pewterCity = new Area("Pewter city", null, true, null, pewterCenter);
		pewterCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));

		// VIRIDIAN City
		Pokecenter viridianCenter = new Pokecenter("Viridian City's Pokecenter");
		Area viridianCity = new Area("Viridian city", null, true, pewterCity, viridianCenter);
		viridianCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));

		// PALLET Town
		Pokecenter palletCenter = new Pokecenter("Pallet Town's Pokecenter");
		Area palletTown = new Area("Pallet town", null, true, viridianCity, palletCenter);
		palletTown.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));
		
		// VERMILION City
		Pokecenter vermilionCenter = new Pokecenter("Vermilion City's Pokecenter");
		Area vermilionCity = new Area("Vermilion City",  null, true, palletTown ,vermilionCenter );
		vermilionCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));
		
		// CERULEAN City
		Pokecenter ceruleanCenter = new Pokecenter("Cerulean City's Pokecenter");
		Area ceruleanCity = new Area("Cerulean City",  null, true, vermilionCity ,ceruleanCenter );
		ceruleanCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));
		
		
		areas.add(palletTown);
		areas.add(viridianCity);
		areas.add(pewterCity);
		areas.add(vermilionCity);
		areas.add(ceruleanCity);

		// SETUP gym leaders
		GymLeader pewterLeader = new GymLeader("Bram", new Badge("Boulder Badge"), pewterCity);
		Pokemon p = new Pokemon(PokemonData.ONIX);
		p.setLevel(5);
		p.setOwner(pewterLeader);
		pewterLeader.setActivePokemon(p);
		pewterLeader.getPokemonCollection().add(p);
		pewterCity.setGymLeader(pewterLeader);
		
		GymLeader vermilionLeader = new GymLeader("Rens", new Badge("Thunder Badge"), vermilionCity);
		Pokemon p1 = new Pokemon(PokemonData.PIKACHU);
		p1.setLevel(10);
		p1.setOwner(vermilionLeader);
		vermilionLeader.setActivePokemon(p1);
		vermilionLeader.getPokemonCollection().add(p1);
		vermilionCity.setGymLeader(vermilionLeader);
		
		GymLeader ceruleanLeader = new GymLeader("Gino", new Badge("Cascade Badge"), ceruleanCity);
		Pokemon p2 = new Pokemon(PokemonData.BLASTOISE);
		p2.setLevel(15);
		p2.setOwner(ceruleanLeader);
		ceruleanLeader.setActivePokemon(p2);
		ceruleanLeader.getPokemonCollection().add(p2);
		ceruleanCity.setGymLeader(ceruleanLeader);
	
	}

	public static void main(String[] args) {
		System.out.println("Welcome new trainer, what is your name?");
		String name = sc.nextLine();
		trainer = new Trainer(name, areas.get(0));
		System.out.println("Hello " + trainer.getName());

		Pokemon firstPokemon = chooseFirstPokemon();
		firstPokemon.setOwner(trainer);
		trainer.getPokemonCollection().add(firstPokemon);
		System.out.println("You now have " + trainer.getPokemonCollection().size() + " pokemon in your collection!");

		// game loop
		while (true) {
			showGameOptions();
		}
	}

	private static void showGameOptions() {
		System.out.println("What do you want to do?");
		System.out.println("1 ) Find Pokemon");
		System.out.println("2 ) My Pokemon");
		System.out.println("3 ) Inventory");
		System.out.println("4 ) Badges");
		System.out.println("5 ) Challenge " + trainer.getCurrentArea().getName() + "'s Gym Leader");
		System.out.println("6 ) Travel");
		System.out.println("7 ) Visit Pokecenter");
		System.out.println("8 ) Exit game");
		int action = sc.nextInt();
		switch (action) {
		case 1:
			findAndBattlePokemon();
			break;
		case 2:
			trainer.showPokemonColletion();
			break;
		case 3:
			ItemType item = showInventory();
			trainer.useItem(item, null);
			break;
		case 4:
			trainer.showBadges();
			break;
		case 5:
			if (trainer.getCurrentArea().getGymLeader() != null) {
				startGymBattle();
			} else {
				System.out.println("No Gym Leader in this town!");
			}
			break;
		case 6:
			Area area = showTravel();
			if (area != null) {
				trainer.travel(area);
			}
			break;
		case 7:
			trainer.visitPokeCenter(trainer.getCurrentArea().getPokecenter());
			break;
		case 8:
			quit();
			break;
		default:
			System.out.println("Sorry, that's not a valid option");
			break;
		}
	}


	// TODO: US-PKM-O-6
	private static void findAndBattlePokemon() {
	        Pokemon randomPokemon = trainer.findPokemon();
	        
	        Battle battle = trainer.battle(trainer.getActivePokemon(), randomPokemon);
	        battle.start();
	    }

	

	private static Area showTravel() {
		Area travelTo = null;
		int index = 1;
		List<Area> travelToAreas = new ArrayList<>();

		for (Area area : areas) {
			if (!area.equals(trainer.getCurrentArea()) && area.isUnlocked()
					&& ((area.getNextArea() != null && area.getNextArea().equals(trainer.getCurrentArea()))
							|| trainer.getCurrentArea().getNextArea() != null
									&& trainer.getCurrentArea().getNextArea().equals(area))) {
				travelToAreas.add(area);
			}
		}
		for (Area a : travelToAreas) {
			System.out.println(index + ") " + a.getName());
			index++;
		}
		System.out.println(index + ") Back");
		int choice = sc.nextInt();
		if (choice != index) {
			travelTo = travelToAreas.get(choice - 1);
		}
		return travelTo;
	}

	private static ItemType showInventory() {
		HashMap<ItemType, Integer> items = trainer.getInventory().getItems();
		Set<Map.Entry<ItemType, Integer>> entries = items.entrySet();
		int index = 1;
		for (Map.Entry<ItemType, Integer> entry : entries) {
			System.out.println(index + ") " + entry.getKey() + " " + entry.getValue());
			index++;
		}
		System.out.println(index + ") Back");
		int choice = sc.nextInt();
		if (choice != index) {
			return ItemType.values()[choice - 1];
		}
		return null;
	}

	

	// TODO: US-PKM-O-1
	private static Pokemon chooseFirstPokemon() {
		System.out.println("Please choose a pokemon:");
		System.out.println("1 ) Charmander");
		System.out.println("2 ) Bulbasaur");
		System.out.println("3 ) Squirtle");

		PokemonData firstPokemon = null;
		int input1 = sc.nextInt();
		if (input1 == 1) {
			firstPokemon = PokemonData.CHARMANDER;

			System.out.println("You chose the FIRE Pokemon Charmander!");
		} else if (input1 == 2) {
			firstPokemon = PokemonData.BULBASAUR;

			System.out.println("You chose the GRASS Pokemon Bulbasaur!");
		} else if (input1 == 3) {
			firstPokemon = PokemonData.SQUIRTLE;

			System.out.println("You chose the WATER Pokemon Squirtle!");
		} else {
			System.out.println("Please try again");
			return chooseFirstPokemon();
		}
		
		Pokemon chosenPokemon = new Pokemon(firstPokemon);
		trainer.setActivePokemon(chosenPokemon);
		return chosenPokemon;
		
		
	}

	// TODO: US-PKM-O-8
	private static void startGymBattle() {
        Battle trainerBattle = trainer.challengeTrainer(Game.trainer.getCurrentArea().getGymLeader());
        
        if (trainerBattle != null && trainerBattle.getWinner().getOwner().equals(trainer)) {
        	
        if (trainerBattle.getEnemy().getOwner().getClass().equals(GymLeader.class)) {
                Pokemon enemyPokemon = trainerBattle.getEnemy();
                Trainer gymleader = enemyPokemon.getOwner();
                Trainer owner = new Trainer("Test", null);
                GymLeader gymLeader = (GymLeader) gymleader;
                gymLeader.setDefeated(true);
                awardBadge(gymLeader.getBadge().getName());
                Area gymLeaderArea = gymLeader.getCurrentArea();
                Area nextArea = gymLeaderArea.getNextArea();
                if (nextArea != null) {
                    nextArea.setUnlocked(true);
                }
                
            }
        }
        }

	// TODO: US-PKM-O-9
	public static void awardBadge(String badgeName) {
		Badge newBadge = new Badge(badgeName);
		trainer.addBadge(newBadge);

	}

	public static void gameOver(String message) {
		System.out.println(message);
		System.out.println("Game over");
		quit();
	}

	// TODO: US-PKM-O-2
	private static void quit() {
		System.out.println("Goodbye");
		System.exit(0);
	}
}
