package com.letsShout;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.letsShout.business.Shouter;
import com.letsShout.model.MessageRegistry;

import java.util.Optional;

/**
 * Shouter service actor registry
 */
public class ActorRegistry extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    Shouter shout = new Shouter();

    static Props props() {
        return Props.create(ActorRegistry.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MessageRegistry.SearchTweets.class, x -> {
                    log.info("received message type: ", MessageRegistry.SearchTweets.class.getName());
                    x.SearchTweets();
                    getSender().tell(Optional.of(shout.shout(x.getResults())), getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
