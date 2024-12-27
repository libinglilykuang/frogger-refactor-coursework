package uk.ac.nott.cs.comp2013.froggergame.controller;

import uk.ac.nott.cs.comp2013.froggergame.controller.initializer.GameInitializer;
import uk.ac.nott.cs.comp2013.froggergame.model.factory.GameObjectFactory;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.score.ScoreManager;
import uk.ac.nott.cs.comp2013.froggergame.model.logic.state.GameStateChecker;
import uk.ac.nott.cs.comp2013.froggergame.view.components.Score;
import uk.ac.nott.cs.comp2013.froggergame.view.scenes.MyStage;

public record GameControllerConfig(MyStage background, ScoreManager scoreManager, GameObjectFactory gameObjectFactory,
                                   GameStateChecker gameStateChecker, Score scoreView,
                                   GameInitializer gameInitializer) {
}

