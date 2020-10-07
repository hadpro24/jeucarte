package com.learn.jeucarte.cardgame;

import com.learn.jeucarte.controller.GameController;
import com.learn.jeucarte.games.GameEvaluator;
import com.learn.jeucarte.model.Deck;
import com.learn.jeucarte.view.View;

public class CardGame {
	public static void main(String[] args) {
		GameController gc = new GameController(new View(), new Deck(), new GameEvaluator());
		gc.run();
	}
}
