package com.learn.jeucarte.controller;

import java.util.ArrayList;

import com.learn.jeucarte.games.GameEvaluator;
import com.learn.jeucarte.model.Deck;
import com.learn.jeucarte.model.Player;
import com.learn.jeucarte.model.PlayingCard;
import com.learn.jeucarte.view.View;

public class GameController {
	enum GameState{
		AddingPlayers,
		CardsDealt,
		WinnerRevealed
	}
	
	Deck deck;
	ArrayList<Player> players;
	Player winner;
	View view;
	GameState gameState;
	GameEvaluator evaluator;
	
	public GameController(View view, Deck deck, GameEvaluator evaluator) {
		this.view = view;
		this.deck = deck;
		players = new ArrayList<Player>();
		gameState = GameState.AddingPlayers;
		this.evaluator = evaluator;
		view.setController(this);
	}
	
	public void addPlayer(String playerName) {
		if(gameState == GameState.AddingPlayers) {
			players.add(new Player(playerName));
			view.showPlayerName(players.size(), playerName);
		}
	}
	
	public void run() {
		while(true) {
			switch(gameState) {
				case AddingPlayers:
					view.promptForPlayerName();
					break;
				case CardsDealt:
					view.promptForFlip();
					break;
				case WinnerRevealed:
					view.promptForNewGame();
					break;
				
			}
		}
	}
	
	public void startGame() {
		if(gameState != GameState.CardsDealt) {
			deck.shuffle();
			int playerIndex = 1;
			for (Player player: players) {
				player.addCardToHand(deck.removeTopCard());
				view.showFaceDownCardForPlayer(playerIndex++, player.getName());
			}
			gameState = GameState.CardsDealt;
		}
	}
	
	public void flipCards() {
		int playerIndex = 1;
		for (Player player : players) {
			PlayingCard pc = player.getCard(0);
			pc.flip();
			view.showCardForPlayer(playerIndex++, player.getName(), 
					pc.getRank().toString(), pc.getSuit().toString());
		}
		evaluateWinner();
		displayWinner();
		rebuildDeck();
		gameState = GameState.WinnerRevealed;
	}
	
	void evaluateWinner() {
		winner = this.evaluator.evaluateWinner(players);
	}
	
	void displayWinner() {
		view.showWinner(winner.getName());
	}
	
	void rebuildDeck() {
		for (Player player: players) {
			deck.returnCardToDeck(player.removeCard());
		}
	}
	
	
}
