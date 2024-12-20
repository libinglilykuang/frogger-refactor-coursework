package uk.ac.nott.cs.comp2013.froggergame.controller.processor;

import uk.ac.nott.cs.comp2013.froggergame.model.base.Actor;

public class DefaultActorProcessor implements ActorProcessor {
    @Override
    public void process(long now, Actor actor) {
        actor.act(now);
    }
}