package uk.ac.nott.cs.comp2013.froggergame.controller.processor;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public interface ActorProcessor {
    void process(long now, Actor actor);
}
